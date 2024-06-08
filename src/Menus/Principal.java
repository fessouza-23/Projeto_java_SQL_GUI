package Menus;

import javax.swing.*;
import java.awt.*;

public class Principal {
    public static void main(String[] args) {
        // Cria o frame principal
        JFrame frame = new JFrame("CRUDBALL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(945, 730);
        frame.setLayout(new BorderLayout());

        // Painel para o título com margens
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10)); // Ajusta margens ao redor do título
        titlePanel.setBackground(new Color(255,255,255,255)); // Cor de fundo do painel de título

        // Carrega a imagem do ícone
        ImageIcon icon = new ImageIcon("bola.jpg"); // Certifique-se de que o arquivo bola.png está no diretório do projeto
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        // Título com ícone
        JLabel titleLabel = new JLabel("CRUDBALL", scaledIcon, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat Black", Font.BOLD, 88));
        titleLabel.setForeground(new Color(35, 31, 32, 255));
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza o texto horizontalmente
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM); // Posiciona o texto abaixo do ícone
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Adiciona o rótulo ao painel de título
        frame.add(titlePanel, BorderLayout.NORTH); // Adiciona o painel de título ao frame

        // Painel principal para organizar os componentes
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(35,31,32,255)); // Cor de fundo do painel principal
        GridBagConstraints gbc = new GridBagConstraints();
        frame.add(mainPanel, BorderLayout.CENTER);

        // Seção Inserir
        JLabel inserirLabel = new JLabel("Incluir:");
        inserirLabel.setFont(new Font("Montserrat", Font.PLAIN, 25));
        inserirLabel.setForeground(Color.WHITE); // Cor do texto da seção Inserir
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(inserirLabel, gbc);

        // ComboBox de Inserir
        String[] criarOptions = {"Jogador", "Time", "Liga"};
        JComboBox<String> criarComboBox = new JComboBox<>(criarOptions);
        criarComboBox.setFont(new Font("Montserrat", Font.PLAIN, 18));
        criarComboBox.setPreferredSize(new Dimension(200, 30));
        criarComboBox.setBackground(new Color(220, 220, 220));
        criarComboBox.setForeground(new Color(50, 50, 50));
        criarComboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(criarComboBox, gbc);

        // Seção Editar
        JLabel editarLabel = new JLabel("Editar ou remover:");
        editarLabel.setFont(new Font("Montserrat", Font.PLAIN, 25));
        editarLabel.setForeground(Color.WHITE); // Cor do texto da seção Editar
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(editarLabel, gbc);

        // ComboBox de Editar
        String[] editarOptions = {"Jogador", "Time", "Liga"};
        JComboBox<String> editarComboBox = new JComboBox<>(editarOptions);
        editarComboBox.setFont(new Font("Montserrat", Font.PLAIN, 18));
        editarComboBox.setPreferredSize(new Dimension(200, 30));
        editarComboBox.setBackground(new Color(220, 220, 220));
        editarComboBox.setForeground(new Color(50, 50, 50));
        editarComboBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(editarComboBox, gbc);

        // Adiciona ações às comboboxes (exemplo de ActionListener)
        criarComboBox.addActionListener(e -> {
            String selectedOption = (String) criarComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(frame, selectedOption + " selecionado!");
        });

        editarComboBox.addActionListener(e -> {
            String selectedOption = (String) editarComboBox.getSelectedItem();
            JOptionPane.showMessageDialog(frame, selectedOption + " selecionado!");
        });

        // Torna o frame visível
        frame.setVisible(true);
    }
}