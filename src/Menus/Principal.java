package Menus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal {
    public static void main(String[] args) {
        // Cria o frame principal
        JFrame frame = new JFrame("CRUDBALL");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(945, 730);
        frame.setLayout(new BorderLayout());

        // Painel para o título com margens
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10)); // Ajusta margens ao redor do título
        titlePanel.setBackground(Color.WHITE); // Cor de fundo do painel de título

        // Carrega a imagem do ícone
        ImageIcon icon = new ImageIcon("src/Imagens/bola.jpg"); // Certifique-se de que o arquivo bola.png está no diretório do projeto
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        // Título com ícone
        JLabel titleLabel = new JLabel("CRUDBALL", scaledIcon, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat Black", Font.BOLD, 50));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalTextPosition(SwingConstants.CENTER); // Centraliza o texto horizontalmente
        titleLabel.setVerticalTextPosition(SwingConstants.BOTTOM); // Posiciona o texto abaixo do ícone
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Adiciona o rótulo ao painel de título
        frame.add(titlePanel, BorderLayout.CENTER); // Adiciona o painel de título ao frame

        // Botão "Começar"
        JButton startButton = new JButton("Começar");
        startButton.setFont(new Font("Montserrat", Font.PLAIN, 18));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Opcoes(); // Abre a nova janela Opcoes
                frame.dispose(); // Fecha a janela atual
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(startButton);

        frame.add(buttonPanel, BorderLayout.SOUTH); // Adiciona o painel do botão ao frame

        // Torna o frame visível
        frame.setVisible(true);
    }
}