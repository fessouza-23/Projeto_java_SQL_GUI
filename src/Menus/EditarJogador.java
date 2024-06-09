package Menus;

import javax.swing.*;
import java.awt.*;

public class EditarJogador extends JFrame {

    public EditarJogador() {
        // Configuração do frame
        setTitle("Editar Jogador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criação do painel principal
        JPanel mainPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Adição dos campos de texto para edição das informações do jogador
        mainPanel.add(new JLabel("Nome:"));
        JTextField nameField = new JTextField("J. Grealish");
        mainPanel.add(nameField);

        mainPanel.add(new JLabel("Overall:"));
        JTextField overallField = new JTextField("84");
        mainPanel.add(overallField);

        mainPanel.add(new JLabel("Idade:"));
        JTextField ageField = new JTextField("20");
        mainPanel.add(ageField);

        mainPanel.add(new JLabel("Nacionalidade:"));
        JTextField nationalityField = new JTextField("Inglaterra");
        mainPanel.add(nationalityField);

        mainPanel.add(new JLabel("Time:"));
        JTextField teamField = new JTextField("Aston Villa");
        mainPanel.add(teamField);

        mainPanel.add(new JLabel("Posição:"));
        JTextField positionField = new JTextField("ATA");
        mainPanel.add(positionField);

        mainPanel.add(new JLabel("Altura:"));
        JTextField heightField = new JTextField("1.91 m");
        mainPanel.add(heightField);

        mainPanel.add(new JLabel("Estilo de Jogo:"));
        JTextField playstyleField = new JTextField("Criativo");
        mainPanel.add(playstyleField);

        // Criação do painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        mainPanel.add(buttonPanel);

        // Adição dos botões
        JButton saveButton = createStyledButton("Salvar", new Color(144, 238, 144), new Color(0, 0, 0));
        JButton cancelButton = createStyledButton("Cancelar", new Color(255, 204, 204), new Color(0, 0, 0));

        saveButton.addActionListener(e -> {
            // Lógica para salvar as informações do jogador
            // Pode-se obter os valores dos campos de texto e salvar no banco de dados ou arquivo
            JOptionPane.showMessageDialog(this, "Informações do jogador salvas!");
            dispose(); // Fecha a janela após salvar
        });

        cancelButton.addActionListener(e -> dispose()); // Fecha a janela ao cancelar

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Torna o frame visível
        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton("<html><span style='color: " + toHexString(textColor) + ";'>" + text + "</span></html>");
        button.setPreferredSize(new Dimension(120, 30));
        button.setFont(new Font("Montserrat Medium", Font.PLAIN, 16));
        button.setBackground(bgColor);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(bgColor, 1, true));
        button.setFocusPainted(false);
        return button;
    }

    private String toHexString(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditarJogador frame = new EditarJogador();
            frame.setVisible(true);
        });
    }
}
