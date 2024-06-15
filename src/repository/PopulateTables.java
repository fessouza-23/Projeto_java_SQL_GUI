package repository;

import service.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe PopulateTables é responsável por popular as tabelas Leagues, Teams e Players com dados iniciais.
 * Utiliza a configuração de banco de dados definida na classe DatabaseConfiguration.
 */
public class PopulateTables {
    Connection con = DatabaseConfiguration.getConnection();

    /**
     * Insere dados nas tabelas Leagues, Teams e Players.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void insert() throws SQLException {
        Statement stmt = con.createStatement();

        // Insere ligas
        stmt.executeUpdate("INSERT INTO Leagues (name, img, country) VALUES ('Premier League', " +
                "'res/Leagues/pre.png', 'Inglaterra')");
        stmt.executeUpdate("INSERT INTO Leagues (name, img, country) VALUES ('Brasileirao', " +
                "'res/Leagues/bra.png', 'Brasil')");
        stmt.executeUpdate("INSERT INTO Leagues (name, img, country) VALUES ('Custom', " +
                "'res/Leagues/custom.jpg', 'Mundial')");

        System.out.println("Dados inseridos na tabela Ligas com sucesso!");

        // Busca IDs das ligas inseridas
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
        stmt.executeUpdate("INSERT INTO Teams(name, img, leagueFK) VALUES ('Nike', " +
                "'res/Teams/nike.jpg', " + leagueIdMap.get("Custom") + ")");
        stmt.executeUpdate("INSERT INTO Teams(name, img, leagueFK) VALUES ('Adidas', " +
                "'res/Teams/adidas.jpg', " + leagueIdMap.get("Custom") + ")");
        stmt.executeUpdate("INSERT INTO Teams(name, img, leagueFK) VALUES ('Milan', " +
                "'res/Teams/mil.png', " + leagueIdMap.get("Custom") + ")");

        System.out.println("Dados inseridos na tabela Times com sucesso!");

        // Busca IDs dos times inseridos
        rs = stmt.executeQuery("SELECT id, name FROM Teams");

        Map<String, Integer> teamIdMap = new HashMap<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String nome = rs.getString("name");
            teamIdMap.put(nome, id);
        }

        // Insere jogadores para Manchester United
        insertPlayer(stmt, "David de Gea", "degea.png", "1990-11-07", "1.92", "Goleiro", "Espanha", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Harry Maguire", "maguire.png", "1993-03-05", "1.94", "Zagueiro", "Inglaterra", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Bruno Fernandes", "bruno.png", "1994-09-08", "1.79", "Meio-campista", "Portugal", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Marcus Rashford", "rashford.png", "1997-10-31", "1.80", "Atacante", "Reino Unido", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Paul Pogba", "pogba.png", "1993-03-15", "1.91", "Meio-campista", "França", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Edinson Cavani", "cavani.png", "1987-02-14", "1.84", "Atacante", "Uruguai", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Mason Greenwood", "greenwood.png", "2001-10-01", "1.81", "Atacante", "Inglaterra", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Luke Shaw", "shaw.png", "1995-07-12", "1.85", "Lateral-esquerdo", "Inglaterra", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Fred", "fred.png", "1993-03-05", "1.69", "Meio-campista", "Brasil", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Scott McTominay", "mctominay.png", "1996-12-08", "1.93", "Meio-campista", "Escócia", teamIdMap.get("Manchester United"));
        insertPlayer(stmt, "Jadon Sancho", "sancho.png", "2000-03-25", "1.80", "Atacante", "Inglaterra", teamIdMap.get("Manchester United"));

        // Insere jogadores para Corinthians
        insertPlayer(stmt, "Cássio", "cassio.png", "1987-06-06", "1.95", "Goleiro", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Fagner", "fagner.png", "1989-06-11", "1.68", "Lateral-direito", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Gil", "gil.png", "1987-06-12", "1.92", "Zagueiro", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Fábio Santos", "fabio.png", "1985-09-16", "1.76", "Lateral-esquerdo", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Gabriel", "gabriel.png", "1992-09-18", "1.75", "Volante", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Renato Augusto", "renato.png", "1988-02-08", "1.86", "Meio-campista", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Jô", "jo.png", "1987-03-20", "1.92", "Atacante", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Luan", "luan.png", "1993-03-27", "1.82", "Meio-campista", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Roni", "roni.png", "1999-08-17", "1.77", "Meio-campista", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Adson", "adson.png", "2001-10-06", "1.73", "Atacante", "Brasil", teamIdMap.get("Corinthians"));
        insertPlayer(stmt, "Gustavo Mosquito", "mosquito.png", "1997-06-23", "1.76", "Atacante", "Brasil", teamIdMap.get("Corinthians"));

        // Insere jogadores para Nike
        insertPlayer(stmt, "Cristiano Ronaldo", "cristiano_ronaldo.png", "1985-02-05", "1.87", "Atacante", "Portugal", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Kylian Mbappe", "kylian_mbappe.png", "1998-12-20", "1.78", "Atacante", "França", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Neymar Jr.", "neymar.png", "1992-02-05", "1.75", "Atacante", "Brasil", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Kevin De Bruyne", "de_bruyne.png", "1991-06-28", "1.81", "Meio-campista", "Bélgica", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Robert Lewandowski", "lewandowski.png", "1988-08-21", "1.85", "Atacante", "Polônia", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Eden Hazard", "hazard.png", "1991-01-07", "1.75", "Atacante", "Bélgica", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Virgil van Dijk", "van_dijk.png", "1991-07-08", "1.93", "Zagueiro", "Holanda", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Sergio Ramos", "ramos.png", "1986-03-30", "1.84", "Zagueiro", "Espanha", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Luka Modric", "modric.png", "1985-09-09", "1.72", "Meio-campista", "Croácia", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Romelu Lukaku", "lukaku.png", "1993-05-13", "1.90", "Atacante", "Bélgica", teamIdMap.get("Nike"));
        insertPlayer(stmt, "Philippe Coutinho", "coutinho.png", "1992-06-12", "1.72", "Meio-campista", "Brasil", teamIdMap.get("Nike"));

        // Insere jogadores para Adidas
        insertPlayer(stmt, "Lionel Messi", "lionel_messi.png", "1987-06-24", "1.70", "Atacante", "Argentina", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Paul Pogba", "paul_pogba.png", "1993-03-15", "1.91", "Meio-campista", "França", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Paulo Dybala", "dybala.png", "1993-11-15", "1.77", "Atacante", "Argentina", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Gareth Bale", "bale.png", "1989-07-16", "1.85", "Atacante", "País de Gales", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "James Rodriguez", "james.png", "1991-07-12", "1.80", "Meio-campista", "Colômbia", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Karim Benzema", "benzema.png", "1987-12-19", "1.85", "Atacante", "França", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Toni Kroos", "kroos.png", "1990-01-04", "1.83", "Meio-campista", "Alemanha", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Manuel Neuer", "neuer.png", "1986-03-27", "1.93", "Goleiro", "Alemanha", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Luis Suarez", "suarez.png", "1987-01-24", "1.82", "Atacante", "Uruguai", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Mesut Ozil", "ozil.png", "1988-10-15", "1.80", "Meio-campista", "Alemanha", teamIdMap.get("Adidas"));
        insertPlayer(stmt, "Ivan Rakitic", "rakitic.png", "1988-03-10", "1.84", "Meio-campista", "Croácia", teamIdMap.get("Adidas"));

        // Insere jogadores para Milan
        insertPlayer(stmt, "Gianluigi Donnarumma", "donnarumma.png", "1999-02-25", "1.96", "Goleiro", "Itália", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Zlatan Ibrahimovic", "ibrahimovic.png", "1981-10-03", "1.95", "Atacante", "Suécia", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Franck Kessie", "kessie.png", "1996-12-19", "1.83", "Meio-campista", "Costa do Marfim", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Theo Hernandez", "theo_hernandez.png", "1997-10-06", "1.84", "Lateral-esquerdo", "França", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Simon Kjaer", "kjaer.png", "1989-03-26", "1.90", "Zagueiro", "Dinamarca", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Hakan Calhanoglu", "calhanoglu.png", "1994-02-08", "1.78", "Meio-campista", "Turquia", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Ismael Bennacer", "bennacer.png", "1997-12-01", "1.75", "Meio-campista", "Argélia", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Alessio Romagnoli", "romagnoli.png", "1995-01-12", "1.85", "Zagueiro", "Itália", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Rafael Leao", "leao.png", "1999-06-10", "1.88", "Atacante", "Portugal", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Ante Rebic", "rebic.png", "1993-09-21", "1.85", "Atacante", "Croácia", teamIdMap.get("Milan"));
        insertPlayer(stmt, "Davide Calabria", "calabria.png", "1996-12-06", "1.77", "Lateral-direito", "Itália", teamIdMap.get("Milan"));

        System.out.println("Dados inseridos na tabela Players com sucesso!");

        stmt.close();
    }

    /**
     * Insere um jogador no banco de dados.
     *
     * @param stmt o objeto Statement para executar a inserção
     * @param name o nome do jogador
     * @param img o caminho da imagem do jogador
     * @param dateBirth a data de nascimento do jogador
     * @param height a altura do jogador
     * @param position a posição do jogador
     * @param country o país do jogador
     * @param teamFK o ID do time ao qual o jogador pertence
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    private void insertPlayer(Statement stmt, String name, String img, String dateBirth, String height, String position, String country, int teamFK) throws SQLException {
        String sql = String.format("INSERT INTO Players (name, img, dateBirth, height, position, country, teamFK) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d)",
                name, img, dateBirth, height, position, country, teamFK);
        stmt.executeUpdate(sql);
    }
}