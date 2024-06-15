package repository;

import service.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PopulateTables {
    Connection con = DatabaseConfiguration.getConnection();
    public void insert() throws SQLException {

        Statement stmt = con.createStatement();

        // Insere ligas
        stmt.executeUpdate("INSERT INTO Leagues (name, img, country) VALUES ('Premier League', " +
                "'res/Leagues/pre.png', 'Inglaterra')");

        stmt.executeUpdate("INSERT INTO Leagues (name, img, country) VALUES ('Brasileirao', " +
                "'res/Leagues/bra.png', 'Brasil')");

        System.out.println("Dados inseridos na tabela Ligas com sucesso!");

        ResultSet rs = stmt.executeQuery("SELECT id, name FROM Leagues");

        Map<String, Integer> leagueIdMap = new HashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("name");
            leagueIdMap.put(nome, id);
        }

        // Insere times
        stmt.executeUpdate("INSERT INTO Teams(name, img, leagueFK) VALUES ('Manchester United', " +
                "'res/Teams/man.png', " + leagueIdMap.get("Premier League") + ")");

        stmt.executeUpdate("INSERT INTO Teams(name, img, leagueFK) VALUES ('Corinthians', " +
                "'res/Teams/cor.png', " + leagueIdMap.get("Brasileirao") + ")");

        System.out.println("Dados inseridos na tabela Times com sucesso!");


        rs = stmt.executeQuery("SELECT id, name FROM Teams");

        Map<String, Integer> teamIdMap = new HashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("name");
            teamIdMap.put(nome, id);
        }

        // Insere jogadores
        stmt.executeUpdate("INSERT INTO Players (name, img, dateBirth, height, position, country, teamFK)" +
                " VALUES ('Marcus Rashford', 'rashford.png', '1997-10-31', '1.80', 'Atacante', 'Reino Unido', " +
                teamIdMap.get("Manchester United") + ")");

        stmt.executeUpdate("INSERT INTO Players (name, img, dateBirth, height, position, country, teamFK)" +
                " VALUES ('Yuri Alberto', 'res/Players/yuri_alberto.png', '2001-03-18', '1.82', 'Centroavante', 'Brasil', " +
                teamIdMap.get("Corinthians") + ")");


        System.out.println("Dados inseridos na tabela Players com sucesso!");

        stmt.close();
    }
}
