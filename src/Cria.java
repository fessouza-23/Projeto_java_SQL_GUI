import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Cria {
    public static void criarTabelas(Connection con) throws SQLException {
        Statement stmt = con.createStatement();

        // Cria a tabela Liga
        String createLigaTable = "CREATE TABLE Liga (" +
                "Id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                "Nome VARCHAR(100) NOT NULL," +
                "Logo VARCHAR(255)," +
                "Pais VARCHAR(100)," +
                "TimeFK INTEGER)";
        stmt.executeUpdate(createLigaTable);

        // Cria a tabela Time
        String createTimeTable = "CREATE TABLE Time (" +
                "Id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                "Nome VARCHAR(100) NOT NULL," +
                "Escudo VARCHAR(255)," +
                "AnoFundacao INTEGER," +
                "Titulos INTEGER," +
                "LigaFK INTEGER," +
                "JogadoresFK INTEGER," +
                "FOREIGN KEY (LigaFK) REFERENCES Liga(Id))";
        stmt.executeUpdate(createTimeTable);

        // Cria a tabela Menus.Jogador
        String createJogadorTable = "CREATE TABLE Menus.Jogador (" +
                "Id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
                "Nome VARCHAR(100) NOT NULL," +
                "Foto VARCHAR(255)," +
                "Overall INTEGER," +
                "Idade INTEGER," +
                "Altura FLOAT," +
                "Posicao VARCHAR(50)," +
                "Nacionalidade VARCHAR(100)," +
                "Playstyle VARCHAR(100)," +
                "TimeFK INTEGER," +
                "FOREIGN KEY (TimeFK) REFERENCES Time(Id))";
        stmt.executeUpdate(createJogadorTable);

        System.out.println("Tabelas criadas com sucesso!");

        stmt.close();
    }
}
