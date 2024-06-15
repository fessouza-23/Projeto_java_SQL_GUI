package view;

import controller.DeleteForm;
import controller.InsertForm;
import controller.UpdateForm;
import domain.Players;
import service.DatabaseConfiguration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlayerSelection extends JFrame {
    private JComboBox<ComboBoxItem> playerSelectionBox;
    private JButton backButton, insertButton, updateButton, deleteButton, selectButton;
    private JPanel teamLogoPanel, playerInfoPanel;
    private JLabel teamLogoLabel, playerNameLabel, playerHeightLabel, playerAgeLabel, playerPositionLabel, playerCountryLabel, playerImageLabel;
    private List<Integer> playerIds;
    private int selectedTeamId;
    private static final String BASE_DIR = System.getProperty("user.dir");
    private static final String PLAYER_IMAGE_DIR = BASE_DIR + File.separator + "res" + File.separator + "Players" + File.separator;
    private static final int IMAGE_SIZE = 100;

    public PlayerSelection(int selectedTeamId) {
        this.selectedTeamId = selectedTeamId;

        setTitle("Players Selection");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initButtons();
        initTeamLogoPanel();
        initPlayerInfoPanel();
        initPlayerSelectionBox();

        loadTeamLogo();
        populatePlayerSelectionBox();

        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        backButton = new JButton("Back");
        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        selectButton = new JButton("Select");

        backButton.addActionListener(e -> handleBack());
        insertButton.addActionListener(e -> handleInsert());
        updateButton.addActionListener(e -> handleUpdate());
        deleteButton.addActionListener(e -> handleDelete());
        selectButton.addActionListener(e -> handleSelect());

        buttonPanel.add(backButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(selectButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void initTeamLogoPanel() {
        teamLogoPanel = new JPanel(new BorderLayout());
        teamLogoLabel = new JLabel();
        teamLogoPanel.add(teamLogoLabel, BorderLayout.CENTER);

        add(teamLogoPanel, BorderLayout.WEST);
    }

    private void initPlayerInfoPanel() {
        playerInfoPanel = new JPanel(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(6, 1));
        playerNameLabel = new JLabel("Name:");
        playerHeightLabel = new JLabel("Height:");
        playerAgeLabel = new JLabel("Birthdate:");
        playerPositionLabel = new JLabel("Position:");
        playerCountryLabel = new JLabel("Country:");
        playerImageLabel = new JLabel();

        infoPanel.add(playerNameLabel);
        infoPanel.add(playerAgeLabel);
        infoPanel.add(playerHeightLabel);
        infoPanel.add(playerPositionLabel);
        infoPanel.add(playerCountryLabel);
        infoPanel.add(playerImageLabel);

        playerInfoPanel.add(infoPanel, BorderLayout.NORTH);

        add(playerInfoPanel, BorderLayout.CENTER);
    }

    private void initPlayerSelectionBox() {
        JPanel playerSelectionPanel = new JPanel(new BorderLayout());
        playerSelectionBox = new JComboBox<>();
        playerSelectionBox.setMaximumRowCount(5);
        JScrollPane scrollPane = new JScrollPane(playerSelectionBox);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        playerSelectionPanel.add(scrollPane, BorderLayout.CENTER);

        add(playerSelectionPanel, BorderLayout.EAST);
    }

    private void loadTeamLogo() {
        try {
            Connection con = DatabaseConfiguration.getConnection();
            String sql = "SELECT img FROM Teams WHERE id = ?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, selectedTeamId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        String imgPath = rs.getString("img");
                        String fullImgPath = BASE_DIR + File.separator + imgPath;
                        ImageIcon teamLogoIcon = new ImageIcon(fullImgPath);
                        Image scaledImage = teamLogoIcon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
                        teamLogoIcon.setImage(scaledImage);
                        teamLogoLabel.setIcon(teamLogoIcon);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populatePlayerSelectionBox() {
        playerSelectionBox.removeAllItems();
        playerIds = new ArrayList<>();
        try {
            Connection con = DatabaseConfiguration.getConnection();
            String sql = "SELECT id, name FROM Players WHERE teamFK = ?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, selectedTeamId);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        int playerId = rs.getInt("id");
                        String playerName = rs.getString("name");

                        ComboBoxItem item = new ComboBoxItem(playerId, playerName);
                        playerSelectionBox.addItem(item);

                        playerIds.add(playerId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayerInfo(int playerId) {
        try {
            Connection con = DatabaseConfiguration.getConnection();
            String sql = "SELECT name, img, dateBirth, height, position, country FROM Players WHERE id = ?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, playerId);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        String playerName = rs.getString("name");
                        String imgPath = rs.getString("img");
                        String playerAge = rs.getString("dateBirth");
                        String playerHeight = rs.getString("height");
                        String playerPosition = rs.getString("position");
                        String playerCountry = rs.getString("country");

                        playerNameLabel.setText("Name: " + playerName);
                        playerAgeLabel.setText("Birthdate: " + playerAge);
                        playerHeightLabel.setText("Height: " + playerHeight);
                        playerPositionLabel.setText("Position: " + playerPosition);
                        playerCountryLabel.setText("Country: " + playerCountry);

                        if (imgPath != null) {
                            String fullImgPath = PLAYER_IMAGE_DIR + imgPath;
                            ImageIcon playerImageIcon = new ImageIcon(fullImgPath);
                            Image scaledImage = playerImageIcon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
                            playerImageIcon.setImage(scaledImage);
                            playerImageLabel.setIcon(playerImageIcon);
                        } else {
                            playerImageLabel.setIcon(null);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleBack() {
        dispose();
    }

    private void handleInsert() {
        new InsertForm<>(Players.class);
        populatePlayerSelectionBox();
    }

    private void handleUpdate() {
        new UpdateForm<>(Players.class);
        populatePlayerSelectionBox();
    }

    private void handleDelete() {
        new DeleteForm<>(Players.class);
        populatePlayerSelectionBox();
    }

    private void handleSelect() {
        ComboBoxItem selectedItem = (ComboBoxItem) playerSelectionBox.getSelectedItem();
        if (selectedItem != null) {
            int playerId = selectedItem.getId();
            loadPlayerInfo(playerId);
        }
    }

    private static class ComboBoxItem {
        private final int id;
        private final String name;

        public ComboBoxItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}