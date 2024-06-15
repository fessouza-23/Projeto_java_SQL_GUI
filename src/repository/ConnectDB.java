package repository;

import service.DatabaseConfiguration;

import java.sql.Connection;

public class ConnectDB {
    public Connection connect() {
        return DatabaseConfiguration.getConnection();
    }

}
