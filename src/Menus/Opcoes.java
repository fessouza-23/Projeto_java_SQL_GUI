package Menus;

import javax.swing.*;
import java.awt.*;

public class Opcoes extends JFrame {
    public Opcoes() {
        // Configura o frame
        setTitle("Opções");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(945, 730);
        setLayout(new BorderLayout());

        // Painel para a mensagem de seleção
        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
        messagePanel.setBackground(Color.WHITE);

        JLabel messageLabel = new JLabel("Selecione o que gostaria de ver...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Montserrat", Font.PLAIN, 30));
        messageLabel.setForeground(Color.BLACK);
        messagePanel.add(messageLabel);

        add(messagePanel, BorderLayout.NORTH);

        // Painel para os botões de opções
        JPanel optionsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        optionsPanel.setBackground(Color.WHITE);

        // Carrega a imagem do ícone
        ImageIcon icon = new ImageIcon("src/Imagens/opcoes_trofeu.png"); // Certifique-se de que o arquivo trophy.png está no diretório do projeto
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        // Carrega a segunda imagem do ícone
        ImageIcon icon2 = new ImageIcon("src/Imagens/opcoes_time.jpg"); // Certifique-se de que o arquivo opcoes_time.jpg está no diretório do projeto
        Image img2 = icon2.getImage();
        Image scaledImg2 = img2.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon2 = new ImageIcon(scaledImg2);

        // Botão Ligas
        JButton ligasButton = new JButton(scaledIcon);
        ligasButton.setPreferredSize(new Dimension(200, 300));
        ligasButton.setBackground(Color.WHITE);
        ligasButton.setFocusPainted(false);
        ligasButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        optionsPanel.add(ligasButton);

        // Botão Times
        JButton timesButton = new JButton(scaledIcon2);
        timesButton.setPreferredSize(new Dimension(200, 300));
        timesButton.setBackground(Color.WHITE);
        timesButton.setFocusPainted(false);
        timesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        optionsPanel.add(timesButton);

        // Botão Jogadores
        JButton jogadoresButton = new JButton(scaledIcon);
        jogadoresButton.setPreferredSize(new Dimension(200, 300));
        jogadoresButton.setBackground(Color.WHITE);
        jogadoresButton.setFocusPainted(false);
        jogadoresButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        optionsPanel.add(jogadoresButton);

        add(optionsPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }

    public static void main(String[] args) {
        new Opcoes();
    }
}
