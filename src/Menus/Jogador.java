import javax.swing.*;
import java.awt.*;

public class Jogador extends JFrame {

    public Jogador() {
        // Configuração do frame
        setTitle("Player Info");
        setSize(945, 730);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criação do painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        // Criação e adição da imagem do jogador
        ImageIcon playerIcon = new ImageIcon("jogador1.png"); // Substitua pelo caminho da sua imagem
        JLabel playerLabel = new JLabel(playerIcon);
        mainPanel.add(playerLabel, BorderLayout.WEST);

        // Criação e adição do painel de informações
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20)); // Adiciona margens ao redor do painel de informações
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Adição das informações do jogador
        JLabel titleLabel = new JLabel("<html><h1 style='font-family: Montserrat; font-size: 26px'>Player Info</h1></html>");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinhamento à esquerda
        infoPanel.add(titleLabel);

        JPanel nameAndOverPanel = new JPanel();
        nameAndOverPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alinhamento à esquerda
        nameAndOverPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel nameLabel = new JLabel("<html><h1 style='font-family: Montserrat Medium Extra Bold; font-size: 33px'>J. Grealish</h1></html>");
        JLabel overLabel = new JLabel("<html><h1 style='font-family: Montserrat Medium Extra Bold; font-size: 33px; margin-left: 10px'>84</h1></html>");
        nameAndOverPanel.add(nameLabel);
        nameAndOverPanel.add(overLabel);
        infoPanel.add(nameAndOverPanel);

        infoPanel.add(createInfoLabel("Idade: ", "20"));
        infoPanel.add(createInfoLabel("Nacionalidade: ", "Inglaterra"));
        infoPanel.add(createInfoLabel("Time: ", "Aston Villa"));
        infoPanel.add(createInfoLabel("Posição: ", "ATA"));
        infoPanel.add(createInfoLabel("Altura: ", "1.91 m"));
        infoPanel.add(createInfoLabel("Playstyle: ", "Criativo"));

        // Criação e adição do painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Espaçamento superior
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinhamento à esquerda
        infoPanel.add(buttonPanel);

        // Adição dos botões
        JButton editButton = createStyledButton("Editar", new Color(255, 223, 0), new Color(0, 0, 0));
        JButton deleteButton = createStyledButton("Excluir", new Color(255, 204, 204), new Color(0, 0, 0));

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
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

    private JPanel createInfoLabel(String label, String value) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Usa FlowLayout para alinhamento à esquerda
        JLabel labelComponent = new JLabel("<html><h2 style='font-family: Montserrat; font-size: 14px; margin: 0; padding: 2px 0;'>" + label + "</h2></html>");
        JLabel valueComponent = new JLabel("<html><h2 style='font-family: Montserrat Light; font-size: 14px; margin: 0; padding: 2px 0;'>" + value + "</h2></html>");
        panel.add(labelComponent);
        panel.add(valueComponent);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinhamento à esquerda
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogador frame = new Jogador();
            frame.setVisible(true);
        });
    }
}
