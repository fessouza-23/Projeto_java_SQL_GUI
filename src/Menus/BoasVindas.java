package Menus;

import javax.swing.*;
import java.awt.*;

public class BoasVindas {
    public static void exibirMenuInicial() {
        JFrame frame = new JFrame("Menu Inicial");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Menu Inicial", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        JButton button1 = new JButton("Opção 1");
        JButton button2 = new JButton("Opção 2");
        JButton button3 = new JButton("Opção 3");

        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        frame.add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
