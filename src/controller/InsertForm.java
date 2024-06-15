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

public class InsertForm<T> extends JFrame {
    private Class<T> clazz;
    private Map<String, JTextField> textFieldMap;
    private Map<String, JComboBox<Object>> comboBoxMap;
    private Map<String, JButton> imageButtonMap; // Map para armazenar os botoes e os imageFields
    private static final String BASE_DIR = System.getProperty("user.dir");

    public InsertForm(Class<T> clazz) {
        this.clazz = clazz;
        this.textFieldMap = new HashMap<>();
        this.comboBoxMap = new HashMap<>();
        this.imageButtonMap = new HashMap<>();
        initialize();
    }

    private void initialize() {
        setTitle("Insert " + clazz.getSimpleName());
        setLayout(new GridLayout(0, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();

            // Skip the "id" and "con" fields
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

        JButton submitButton = new JButton("Inserir");
        submitButton.addActionListener(e -> handleSubmit());

        add(new JLabel());
        add(submitButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean isForeignKeyField(Field field) {
        return field.getName().endsWith("FK");
    }

    private boolean isImageField(Field field) {
        return field.getName().equals("img");
    }

    private void addTextField(Field field, JLabel label) {
        JTextField textField = new JTextField(20);
        textFieldMap.put(field.getName(), textField);
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
        return fieldName.substring(0, fieldName.length() - 2) + "s"; // Assumindo a forma plural
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

    private void handleSubmit() {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            System.out.println("Instance created: " + instance);

            for (Field field : clazz.getDeclaredFields()) {
                String fieldName = field.getName();


                if (fieldName.equals("id") || fieldName.equals("con")) {
                    continue;
                }

                if (isForeignKeyField(field)) {
                    int selectedId = getSelectedIdFromComboBox(fieldName);
                    field.setAccessible(true);
                    field.set(instance, selectedId);
                    continue;
                }

                if (isImageField(field)) {
                    String imagePath = imageButtonMap.get(fieldName).getActionCommand();
                    field.setAccessible(true);
                    field.set(instance, imagePath);
                    continue;
                }

                JTextField textField = textFieldMap.get(fieldName);
                if (textField == null) {
                    System.err.println("No JTextField found for field: " + fieldName);
                    continue;
                }
                String value = textField.getText();
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    field.set(instance, value);
                } else if (field.getType() == int.class) {
                    field.set(instance, Integer.parseInt(value));
                } else if (field.getType() == java.sql.Date.class) {
                    field.set(instance, java.sql.Date.valueOf(value));
                }
            }

            Method insertMethod = clazz.getMethod("insert");
            insertMethod.invoke(instance);

            Method printAllMethod = clazz.getMethod("printAll");
            printAllMethod.invoke(null);

            JOptionPane.showMessageDialog(this, clazz.getSimpleName() + " inserted successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting form: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getSelectedIdFromComboBox(String fieldName) {
        JComboBox<Object> comboBox = comboBoxMap.get(fieldName);
        if (comboBox == null) {
            System.err.println("No JComboBox found for field: " + fieldName);
            return -1;
        }
        Object selectedItem = comboBox.getSelectedItem();
        if (selectedItem instanceof ComboBoxItem) {
            return ((ComboBoxItem) selectedItem).getId();
        }
        return -1;
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

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
