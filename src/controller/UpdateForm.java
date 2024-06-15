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

public class UpdateForm<T> extends JFrame {
    private Class<T> clazz;
    private Map<String, JTextField> textFieldMap;
    private Map<String, JComboBox<Object>> comboBoxMap;
    private Map<String, JButton> imageButtonMap;
    private JComboBox<Object> selectComboBox;
    private T currentInstance;
    private static final String BASE_DIR = System.getProperty("user.dir");


    public UpdateForm(Class<T> clazz) {
        this.clazz = clazz;
        this.textFieldMap = new HashMap<>();
        this.comboBoxMap = new HashMap<>();
        this.imageButtonMap = new HashMap<>();
        initializeSelect();
        setResizable(false);
    }

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

        add(new JLabel());
        add(submitButton);

        pack();
        setLocationRelativeTo(null);
    }

    private boolean isForeignKeyField(Field field) {
        return field.getName().endsWith("FK");
    }

    private boolean isImageField(Field field) {
        return field.getName().equals("img");
    }

    private void addTextField(Field field, JLabel label) {
        JTextField textField = new JTextField(20);
        textFieldMap.put(field.getName(), textField); // Use o campo do nome como chave
        add(label);
        add(textField);
    }

    private void addForeignKeyField(Field field, JLabel label) {
        JComboBox<Object> comboBox = new JComboBox<>();
        comboBoxMap.put(field.getName(), comboBox);
        add(label);
        add(comboBox);

        String relatedTableName = getRelatedTableName(field);
        populateComboBox(comboBox, relatedTableName);
    }

    private void addImageField(Field field, JLabel label) {
        JButton imageButton = new JButton("Select Image");
        imageButtonMap.put(field.getName(), imageButton);
        add(label);
        add(imageButton);

        imageButton.addActionListener(e -> handleImageSelection(imageButton));
    }

    private String getRelatedTableName(Field field) {
        String fieldName = field.getName();
        return fieldName.substring(0, fieldName.length() - 2) + "s";
    }

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

    private String getRelativePath(File file) {
        File baseDir = new File(BASE_DIR);
        return baseDir.toURI().relativize(file.toURI()).getPath();
    }

    private void handleSelect() {
        ComboBoxItem selectedItem = (ComboBoxItem) selectComboBox.getSelectedItem();
        if (selectedItem != null) {
            try {
                // Instancia currentInstance se necessario
                currentInstance = clazz.getDeclaredConstructor().newInstance();

                // Retrieve data from database and populate currentInstance
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
                                    field.set(currentInstance, value); // Coloca o valor do na currentInstance
                                }
                            }
                        }
                    }
                }

                // Initialize the update form with currentInstance data
                // Inicializa a atualização do form com os dados de currentInstance
                initializeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error selecting " + clazz.getSimpleName() + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

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
                        } // Lida com outros tipos de campos se necessario
                    }
                }
            }

            // Call the update method on currentInstance to update the database
            // Chama o metodo update na currentInstance para atualizar a database
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

    private int getSelectedIdFromComboBox(String fieldName) {
        JComboBox<Object> comboBox = comboBoxMap.get(fieldName);
        ComboBoxItem selectedItem = (ComboBoxItem) comboBox.getSelectedItem();
        return selectedItem != null ? selectedItem.getId() : -1;
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

        @Override
        public String toString() {
            return name;
        }
    }
}