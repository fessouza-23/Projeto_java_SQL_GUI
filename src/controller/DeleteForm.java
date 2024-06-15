package controller;

import service.DatabaseConfiguration;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe DeleteForm é uma interface gráfica para permitir a exclusão de registros
 * de uma tabela de banco de dados. Esta classe é genérica e pode ser usada para qualquer
 * tipo de entidade representada por uma classe.
 *
 * @param <T> o tipo da entidade que será manipulada por esta interface
 */
public class DeleteForm<T> extends JFrame {
    private Class<T> clazz;
    private JComboBox<ComboBoxItem> comboBox;
    private JButton deleteButton;
    private List<ComboBoxItem> items;

    /**
     * Construtor da classe DeleteForm.
     *
     * @param clazz a classe da entidade que será manipulada
     */
    public DeleteForm(Class<T> clazz) {
        this.clazz = clazz;
        initialize();
        setResizable(false);
    }

    /**
     * Método para inicializar os componentes da interface gráfica.
     */
    private void initialize() {
        setTitle("Delete " + clazz.getSimpleName());
        setLayout(new GridLayout(2, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel nameLabel = new JLabel("Select " + clazz.getSimpleName() + ":");
        comboBox = new JComboBox<>();
        deleteButton = new JButton("Delete");

        add(nameLabel);
        add(comboBox);
        add(new JLabel()); // Espaço vazio para alinhamento
        add(deleteButton);

        // Adiciona o ouvinte de ação para o botão de deletar
        deleteButton.addActionListener(e -> handleDelete());

        // Popula o comboBox com os itens do banco de dados
        populateComboBox();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método para popular o comboBox com os itens da tabela correspondente no banco de dados.
     */
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

    /**
     * Método para lidar com a exclusão do item selecionado no comboBox.
     */
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

    /**
     * Classe interna para representar os itens do comboBox.
     */
    private static class ComboBoxItem {
        private int id;
        private String name;

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
         * Método para obter o ID do item.
         *
         * @return o ID do item
         */
        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}