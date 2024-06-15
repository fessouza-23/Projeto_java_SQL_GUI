package service;

import repository.ConnectDB;
import repository.CreateTables;
import repository.PopulateTables;

import java.sql.SQLException;

public class DatabaseService {
    private ConnectDB connectDB;
    private CreateTables createTables;

    public DatabaseService() {
        this.connectDB = new ConnectDB();
        this.createTables = new CreateTables();
    }

    public void initialize() throws SQLException {
        try {
            connectDB.connect();
            createTables.create();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
