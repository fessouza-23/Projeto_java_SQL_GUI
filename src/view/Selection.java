package view;
import controller.DeleteForm;
import controller.InsertForm;
import controller.UpdateForm;
import domain.Leagues;
import domain.Teams;
import service.DatabaseConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Selection extends JFrame {
    private JButton backButton, insertButton, updateButton, deleteButton;
    private JPanel imagePanel;
    private List<ImageIcon> imageIcons;
    private Class<?> currentClass;
    private String currentTable;
    private int selectedLeagueId;
    private static final String BASE_DIR = System.getProperty("user.dir");
    private static final int IMAGE_SIZE = 100;

    public Selection() {
        setTitle("Leagues and Teams Management");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        initButtons();
        initImagePanel();

        currentClass = Leagues.class;
        currentTable = "Leagues";

        populateSelectionBox(currentTable);
        setVisible(true);
    }

    private void initButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        backButton = new JButton("Back");
        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        backButton.addActionListener(e -> handleBack());
        insertButton.addActionListener(e -> handleInsert());
        updateButton.addActionListener(e -> handleUpdate());
        deleteButton.addActionListener(e -> handleDelete());
        refreshButton.addActionListener(e -> handleRefresh());

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(backButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void initImagePanel() {
        imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(imagePanel, BorderLayout.CENTER);
    }

    private void handleInsert() {
        new InsertForm<>(currentClass);
        populateSelectionBox(currentTable);
    }

    private void handleUpdate() {
        new UpdateForm<>(currentClass);
        populateSelectionBox(currentTable);
    }

    private void handleDelete() {
        new DeleteForm<>(currentClass);
        populateSelectionBox(currentTable);
    }

    private void handleBack() {
        currentClass = Leagues.class;
        currentTable = "Leagues";
        populateSelectionBox(currentTable);
    }

    private void handleRefresh() {
        populateSelectionBox(currentTable);
    }

    private void populateSelectionBox(String tableName) {
        imagePanel.removeAll();
        imageIcons = new ArrayList<>();
        try {
            Connection con = DatabaseConfiguration.getConnection();
            String sql = "SELECT id, name, img FROM " + tableName;
            if (tableName.equals("Teams")) {
                sql += " WHERE leagueFK = ?";
            }
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                if (tableName.equals("Teams")) {
                    statement.setInt(1, selectedLeagueId);
                }
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String imgPath = rs.getString("img");

                        ImageIcon imageIcon = new ImageIcon(BASE_DIR + File.separator + imgPath);
                        Image scaledImage = imageIcon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);

                        imageIcons.add(scaledIcon);

                        JLabel label = new JLabel(scaledIcon);
                        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        label.addMouseListener(new ImageClickListener(id, name));
                        imagePanel.add(label);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private class ImageClickListener extends MouseAdapter {
        private final int id;
        private final String name;

        public ImageClickListener(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (currentClass == Leagues.class) {
                selectedLeagueId = id;
                currentClass = Teams.class;
                currentTable = "Teams";
                populateSelectionBox(currentTable);
            } else if (currentClass == Teams.class) {
                SwingUtilities.invokeLater(() -> new PlayerSelection(id));
            }
        }
    }
}
