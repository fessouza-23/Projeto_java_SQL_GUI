package controller;

import service.DatabaseConfiguration;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteForm<T> extends JFrame {
    private Class<T> clazz;
    private JComboBox<ComboBoxItem> comboBox;
    private JButton deleteButton;
    private List<ComboBoxItem> items;

    public DeleteForm(Class<T> clazz) {
        this.clazz = clazz;
        initialize();
        setResizable(false);
    }

    private void initialize() {
        setTitle("Delete " + clazz.getSimpleName());
        setLayout(new GridLayout(2, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel nameLabel = new JLabel("Select " + clazz.getSimpleName() + ":");
        comboBox = new JComboBox<>();
        deleteButton = new JButton("Delete");

        add(nameLabel);
        add(comboBox);
        add(new JLabel());
        add(deleteButton);

        deleteButton.addActionListener(e -> handleDelete());

        populateComboBox();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateComboBox() {
        items = new ArrayList<>();
        Connection con = DatabaseConfiguration.getConnection();
        String tableName = clazz.getSimpleName();
        String sql = "SELECT id, name FROM " + tableName;

        try (PreparedStatement statement = con.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                items.add(new ComboBoxItem(id, name));
                comboBox.addItem(new ComboBoxItem(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete() {
        ComboBoxItem selectedItem = (ComboBoxItem) comboBox.getSelectedItem();
        if (selectedItem != null) {
            int selectedId = selectedItem.getId();

            Connection con = DatabaseConfiguration.getConnection();
            String tableName = clazz.getSimpleName();
            String sql = "DELETE FROM " + tableName + " WHERE id = ?";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, selectedId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, clazz.getSimpleName() + " deleted successfully.");
                    comboBox.removeItem(selectedItem);
                } else {
                    JOptionPane.showMessageDialog(this, "Error: " + clazz.getSimpleName() + " not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting " + clazz.getSimpleName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a " + clazz.getSimpleName() + " to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private static class ComboBoxItem {
        private int id;
        private String name;

        public ComboBoxItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
