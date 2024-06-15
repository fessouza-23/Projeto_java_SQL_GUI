import repository.PopulateTables;
import service.DatabaseService;
import view.Selection;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            DatabaseService databaseService = new DatabaseService();
            databaseService.initialize();

            PopulateTables populateTables = new PopulateTables();
            populateTables.insert();

            SwingUtilities.invokeLater(() -> {
                new Selection();
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
