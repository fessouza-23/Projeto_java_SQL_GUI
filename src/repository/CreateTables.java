package repository;

import service.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A classe CreateTables é responsável por criar as tabelas no banco de dados.
 * Esta classe utiliza a configuração de banco de dados definida na classe DatabaseConfiguration.
 */
public class CreateTables {

    /**
     * Cria as tabelas Leagues, Teams e Players no banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void create() throws SQLException {
        Connection con = DatabaseConfiguration.getConnection();
        Statement stmt = con.createStatement();

        // Cria a tabela Leagues
        String createLeaguesTable = "CREATE TABLE Leagues (" +
                "id INTEGER IDENTITY PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "img VARCHAR(255)," +
                "country VARCHAR(100))";
        stmt.executeUpdate(createLeaguesTable);

        // Cria a tabela Teams
        String createTeamsTable = "CREATE TABLE Teams (" +
                "id INTEGER IDENTITY PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "img VARCHAR(255)," +
                "leagueFK INTEGER," +
                "FOREIGN KEY (leagueFK) REFERENCES Leagues(id) ON DELETE CASCADE)";
        stmt.executeUpdate(createTeamsTable);

        // Cria a tabela Players
        String createPlayersTable = "CREATE TABLE Players (" +
                "id INTEGER IDENTITY PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "img VARCHAR(255)," +
                "dateBirth DATE," +
                "height VARCHAR(255)," +
                "position VARCHAR(50)," +
                "country VARCHAR(50)," +
                "teamFK INTEGER," +
                "FOREIGN KEY (teamFK) REFERENCES Teams(id) ON DELETE CASCADE)";
        stmt.executeUpdate(createPlayersTable);

        System.out.println("Tabelas criadas com sucesso!");

        stmt.close();
    }
}
