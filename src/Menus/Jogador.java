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
        infoPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 10, 10)); // Adiciona margens ao redor do título
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // Adição das informações do jogador
        infoPanel.add(new JLabel("<html><h1 style='font-family: Mont Serrat Medium; font-size: 40px'>Player Info</h1></html>"));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaçamento

        infoPanel.add(new JLabel("<html><h1 style='font-family: Mont Serrat Medium; font-size: 30px'>J. Grealish</h1></html>"));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 0))); // Espaçamento

        infoPanel.add(new JLabel("<html><h1 style='font-family: Mont Serrat Medium; font-size: 30px'>Over: 84</h1></html>"));
        infoPanel.add(Box.createRigidArea(new Dimension(0, 0))); // Espaçamento

        infoPanel.add(createInfoLabel("Idade: ", "20"));
        infoPanel.add(createInfoLabel("Nacionalidade: ", "Inglaterra"));
        infoPanel.add(createInfoLabel("Time: ", "Aston Villa"));
        infoPanel.add(createInfoLabel("Posição: ", "ata"));
        infoPanel.add(createInfoLabel("Altura: ", "1.91"));
        infoPanel.add(createInfoLabel("Playstyle: ", "criativo"));

        // Criação e adição do painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // Espaçamento superior
        infoPanel.add(buttonPanel);

        // Adição dos botões
        JButton editButton = new JButton("editar");
        JButton deleteButton = new JButton("Excluir");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
    }

    private JPanel createInfoLabel(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(new JLabel(value), BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Jogador frame = new Jogador();
            frame.setVisible(true);
        });
    }
}
