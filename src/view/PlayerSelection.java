package view;

import controller.DeleteForm;
import controller.InsertForm;
import controller.UpdateForm;
import domain.Players;
import service.DatabaseConfiguration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe PlayerSelection é uma interface gráfica que permite a seleção, visualização,
 * inserção, atualização e deleção de jogadores de um time específico.
 */
public class PlayerSelection extends JFrame {
    private JComboBox<ComboBoxItem> playerSelectionBox;
    private JButton backButton, insertButton, updateButton, deleteButton, selectButton;
    private JPanel teamLogoPanel, playerInfoPanel;
    private JLabel teamLogoLabel, playerNameLabel, playerHeightLabel, playerAgeLabel, playerPositionLabel, playerCountryLabel, playerImageLabel;
    private List<Integer> playerIds;
    private int selectedTeamId;
    private static final String BASE_DIR = System.getProperty("user.dir");
    private static final String ROOT_DIR = "./";
    private static final String PLAYER_IMAGE_DIR = BASE_DIR + File.separator + "res" + File.separator + "Players" + File.separator;
    private static final int IMAGE_SIZE = 100;

    /**
     * Construtor da classe PlayerSelection.
     *
     * @param selectedTeamId o ID do time selecionado
     */
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

    /**
     * Inicializa os botões e define seus eventos de clique.
     */
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

    /**
     * Inicializa o painel do logotipo do time.
     */
    private void initTeamLogoPanel() {
        teamLogoPanel = new JPanel(new BorderLayout());
        teamLogoLabel = new JLabel();
        teamLogoPanel.add(teamLogoLabel, BorderLayout.CENTER);

        add(teamLogoPanel, BorderLayout.WEST);
    }

    /**
     * Inicializa o painel de informações do jogador.
     */
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

    /**
     * Inicializa a caixa de seleção de jogadores.
     */
    private void initPlayerSelectionBox() {
        JPanel playerSelectionPanel = new JPanel(new BorderLayout());
        playerSelectionBox = new JComboBox<>();
        playerSelectionBox.setMaximumRowCount(5);
        JScrollPane scrollPane = new JScrollPane(playerSelectionBox);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        playerSelectionPanel.add(scrollPane, BorderLayout.CENTER);

        add(playerSelectionPanel, BorderLayout.EAST);
    }

    /**
     * Carrega o logotipo do time.
     */
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

    /**
     * Popula a caixa de seleção de jogadores com os jogadores do time selecionado.
     */
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

    /**
     * Carrega as informações do jogador selecionado.
     *
     * @param playerId o ID do jogador selecionado
     */
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
                            String fullImgPath = ROOT_DIR + imgPath;
                            System.out.println("Full Image Path: " + fullImgPath); // Debugging line
                            try {
                                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fullImgPath);
                                if (inputStream != null) {
                                    BufferedImage bufferedImage = ImageIO.read(inputStream);
                                    ImageIcon playerImageIcon = new ImageIcon(bufferedImage);
                                    Image scaledImage = playerImageIcon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
                                    playerImageIcon.setImage(scaledImage);
                                    playerImageLabel.setIcon(playerImageIcon);
                                } else {
                                    System.out.println("Image not found: " + fullImgPath);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
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

    /**
     * Manipula o evento de clique do botão "Back".
     */
    private void handleBack() {
        dispose();
    }

    /**
     * Manipula o evento de clique do botão "Insert".
     */
    private void handleInsert() {
        new InsertForm<>(Players.class);
        populatePlayerSelectionBox();
    }

    /**
     * Manipula o evento de clique do botão "Update".
     */
    private void handleUpdate() {
        new UpdateForm<>(Players.class);
        populatePlayerSelectionBox();
    }

    /**
     * Manipula o evento de clique do botão "Delete".
     */
    private void handleDelete() {
        new DeleteForm<>(Players.class);
        populatePlayerSelectionBox();
    }

    /**
     * Manipula o evento de clique do botão "Select".
     */
    private void handleSelect() {
        ComboBoxItem selectedItem = (ComboBoxItem) playerSelectionBox.getSelectedItem();
        if (selectedItem != null) {
            int playerId = selectedItem.getId();
            loadPlayerInfo(playerId);
        }
    }

    /**
     * Classe interna para representar os itens do comboBox.
     */
    private static class ComboBoxItem {
        private final int id;
        private final String name;

        /**
         * Construtor da classe ComboBoxItem.
         *
         * @param id o ID do item
         * @param name o nome do item
         */
        public ComboBoxItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Obtém o ID do item.
         *
         * @return o ID do item
         */
        public int getId() {
            return id;
        }

        /**
         * Obtém o nome do item.
         *
         * @return o nome do item
         */
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}