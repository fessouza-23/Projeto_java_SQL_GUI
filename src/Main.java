import repository.PopulateTables;
import service.DatabaseService;
import view.Selection;

import javax.swing.*;
import java.sql.SQLException;

/**
 * A classe Main é o ponto de entrada da aplicação. Ela inicializa o banco de dados,
 * popula as tabelas com dados iniciais e abre a interface gráfica de seleção.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            // Inicializa o banco de dados e cria as tabelas
            DatabaseService databaseService = new DatabaseService();
            databaseService.initialize();

            // Popula as tabelas com dados iniciais
            PopulateTables populateTables = new PopulateTables();
            populateTables.insert();

            // Inicia a interface gráfica de seleção
            SwingUtilities.invokeLater(() -> {
                new Selection();
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}