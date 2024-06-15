package service;

import repository.ConnectDB;
import repository.CreateTables;
import repository.PopulateTables;

import java.sql.SQLException;

/**
 * A classe DatabaseService é responsável por inicializar o banco de dados,
 * conectando-se a ele e criando as tabelas necessárias.
 */
public class DatabaseService {
    private ConnectDB connectDB;
    private CreateTables createTables;

    /**
     * Construtor da classe DatabaseService.
     * Inicializa os objetos ConnectDB e CreateTables.
     */
    public DatabaseService() {
        this.connectDB = new ConnectDB();
        this.createTables = new CreateTables();
    }

    /**
     * Inicializa o banco de dados, conectando-se a ele e criando as tabelas.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void initialize() throws SQLException {
        try {
            connectDB.connect(); // Conecta ao banco de dados
            createTables.create(); // Cria as tabelas no banco de dados

            // Popula as tabelas com dados iniciais
            PopulateTables populateTables = new PopulateTables();
            populateTables.insert();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
