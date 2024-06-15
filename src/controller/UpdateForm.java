package controller;

import service.DatabaseConfiguration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe UpdateForm é uma interface gráfica para permitir a atualização de registros
 * em uma tabela de banco de dados. Esta classe é genérica e pode ser usada para qualquer
 * tipo de entidade representada por uma classe.
 *
 * @param <T> o tipo da entidade que será manipulada por esta interface
 */
public class UpdateForm<T> extends JFrame {
    private Class<T> clazz;
    private Map<String, JTextField> textFieldMap;
    private Map<String, JComboBox<Object>> comboBoxMap;
    private Map<String, JButton> imageButtonMap;
    private JComboBox<Object> selectComboBox;
    private T currentInstance;
    private static final String BASE_DIR = System.getProperty("user.dir");

    /**
     * Construtor da classe UpdateForm.
     *
     * @param clazz a classe da entidade que será manipulada
     */
    public UpdateForm(Class<T> clazz) {
        this.clazz = clazz;
        this.textFieldMap = new HashMap<>();
        this.comboBoxMap = new HashMap<>();
        this.imageButtonMap = new HashMap<>();
        initializeSelect();
        setResizable(false);
    }

    /**
     * Método para inicializar a seleção do item a ser atualizado.
     */
    private void initializeSelect() {
        setTitle("Select " + clazz.getSimpleName() + " to Update");
        setLayout(new GridLayout(0, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        selectComboBox = new JComboBox<>();
        populateSelectComboBox(selectComboBox);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> handleSelect());

        add(new JLabel("Select " + clazz.getSimpleName() + ":"));
        add(selectComboBox);
        add(new JLabel());
        add(selectButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Método para inicializar a interface de atualização com os dados do item selecionado.
     */
    private void initializeUpdate() {
        getContentPane().removeAll();
        setLayout(new GridLayout(0, 2, 10, 10));

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();

            if (fieldName.equals("id") || fieldName.equals("con")) {
                continue;
            }

            JLabel label = new JLabel(fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + ":");
            if (isForeignKeyField(field)) {
                addForeignKeyField(field, label);
            } else if (isImageField(field)) {
                addImageField(field, label);
            } else {
                addTextField(field, label);
            }
        }

        JButton submitButton = new JButton("Update");
        submitButton.addActionListener(e -> handleSubmit());

        add(new JLabel()); // Espaço vazio para alinhamento
        add(submitButton);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Verifica se um campo é uma chave estrangeira.
     *
     * @param field o campo a ser verificado
     * @return true se o campo for uma chave estrangeira, false caso contrário
     */
    private boolean isForeignKeyField(Field field) {
        return field.getName().endsWith("FK");
    }

    /**
     * Verifica se um campo é um campo de imagem.
     *
     * @param field o campo a ser verificado
     * @return true se o campo for um campo de imagem, false caso contrário
     */
    private boolean isImageField(Field field) {
        return field.getName().equals("img");
    }

    /**
     * Adiciona um campo de texto à interface.
     *
     * @param field o campo a ser adicionado
     * @param label o rótulo do campo
     */
    private void addTextField(Field field, JLabel label) {
        JTextField textField = new JTextField(20);
        textFieldMap.put(field.getName(), textField); // Usa o nome do campo como chave
        add(label);
        add(textField);
    }

    /**
     * Adiciona um campo de chave estrangeira à interface.
     *
     * @param field o campo a ser adicionado
     * @param label o rótulo do campo
     */
    private void addForeignKeyField(Field field, JLabel label) {
        JComboBox<Object> comboBox = new JComboBox<>();
        comboBoxMap.put(field.getName(), comboBox);
        add(label);
        add(comboBox);

        String relatedTableName = getRelatedTableName(field);
        populateComboBox(comboBox, relatedTableName);
    }

    /**
     * Adiciona um campo de imagem à interface.
     *
     * @param field o campo a ser adicionado
     * @param label o rótulo do campo
     */
    private void addImageField(Field field, JLabel label) {
        JButton imageButton = new JButton("Select Image");
        imageButtonMap.put(field.getName(), imageButton);
        add(label);
        add(imageButton);

        imageButton.addActionListener(e -> handleImageSelection(imageButton));
    }

    /**
     * Obtém o nome da tabela relacionada a uma chave estrangeira.
     *
     * @param field o campo de chave estrangeira
     * @return o nome da tabela relacionada
     */
    private String getRelatedTableName(Field field) {
        String fieldName = field.getName();
        return fieldName.substring(0, fieldName.length() - 2) + "s";
    }

    /**
     * Popula um comboBox com os dados de uma tabela relacionada.
     *
     * @param comboBox o comboBox a ser populado
     * @param tableName o nome da tabela relacionada
     */
    private void populateComboBox(JComboBox<Object> comboBox, String tableName) {
        try {
            Connection con = DatabaseConfiguration.getConnection();
            String sql = "SELECT id, name FROM " + tableName;
            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    comboBox.addItem(new ComboBoxItem(id, name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Popula o comboBox de seleção inicial com os dados da tabela principal.
     *
     * @param comboBox o comboBox a ser populado
     */
    private void populateSelectComboBox(JComboBox<Object> comboBox) {
        try {
            Connection con = DatabaseConfiguration.getConnection();
            String sql = "SELECT id, name FROM " + clazz.getSimpleName();
            try (PreparedStatement statement = con.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    comboBox.addItem(new ComboBoxItem(id, name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lida com a seleção de uma imagem.
     *
     * @param imageButton o botão de seleção de imagem
     */
    private void handleImageSelection(JButton imageButton) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "gif"));

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String relativePath = getRelativePath(selectedFile);
            imageButton.setText(relativePath);
            imageButton.setActionCommand(relativePath);
        }
    }

    /**
     * Obtém o caminho relativo de um arquivo.
     *
     * @param file o arquivo
     * @return o caminho relativo do arquivo
     */
    private String getRelativePath(File file) {
        File baseDir = new File(BASE_DIR);
        return baseDir.toURI().relativize(file.toURI()).getPath();
    }

    /**
     * Lida com a seleção de um item para atualização.
     */
    private void handleSelect() {
        ComboBoxItem selectedItem = (ComboBoxItem) selectComboBox.getSelectedItem();
        if (selectedItem != null) {
            try {
                // Instancia currentInstance se necessário
                currentInstance = clazz.getDeclaredConstructor().newInstance();

                // Recupera dados da database e preenche a currentInstance
                Connection con = DatabaseConfiguration.getConnection();
                String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE id = ?";
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setInt(1, selectedItem.getId());
                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            for (Field field : clazz.getDeclaredFields()) {
                                String fieldName = field.getName();

                                if (fieldName.equals("id") || fieldName.equals("con")) {
                                    continue;
                                }

                                field.setAccessible(true);
                                Object value = rs.getObject(fieldName);
                                if (value != null) {
                                    field.set(currentInstance, value); // Coloca o valor na currentInstance
                                }
                            }
                        }
                    }
                }

                // Inicializa a atualização do form com os dados de currentInstance
                initializeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error selecting " + clazz.getSimpleName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Lida com o envio do formulário de atualização.
     */
    private void handleSubmit() {
        try {
            // Coloca valores na currentInstance
            for (Field field : clazz.getDeclaredFields()) {
                String fieldName = field.getName();
                field.setAccessible(true);

                if (fieldName.equals("id") || fieldName.equals("con")) {
                    continue;
                }

                if (isForeignKeyField(field)) {
                    int selectedId = getSelectedIdFromComboBox(fieldName);
                    field.set(currentInstance, selectedId);
                } else if (isImageField(field)) {
                    String imagePath = imageButtonMap.get(fieldName).getActionCommand();
                    field.set(currentInstance, imagePath);
                } else {
                    JTextField textField = textFieldMap.get(fieldName);
                    if (textField != null) {
                        String value = textField.getText();
                        if (field.getType() == String.class) {
                            field.set(currentInstance, value);
                        } else if (field.getType() == int.class) {
                            field.set(currentInstance, Integer.parseInt(value));
                        } // Lida com outros tipos de campos se necessário
                    }
                }
            }

            // Chama o método update na currentInstance para atualizar a database
            Method updateMethod = clazz.getMethod("update");
            updateMethod.invoke(currentInstance);

            // Printa todos os valores da tabela
            Method printAllMethod = clazz.getMethod("printAll");
            printAllMethod.invoke(null);

            JOptionPane.showMessageDialog(this, clazz.getSimpleName() + " updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating " + clazz.getSimpleName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtém o ID selecionado de um comboBox.
     *
     * @param fieldName o nome do campo
     * @return o ID selecionado
     */
    private int getSelectedIdFromComboBox(String fieldName) {
        JComboBox<Object> comboBox = comboBoxMap.get(fieldName);
        ComboBoxItem selectedItem = (ComboBoxItem) comboBox.getSelectedItem();
        return selectedItem != null ? selectedItem.getId() : -1;
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

        @Override
        public String toString() {
            return name;
        }
    }
}