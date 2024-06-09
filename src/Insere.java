import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Insere {
    public static void inserirDados(Connection con) throws SQLException {
        Statement stmt = con.createStatement();

        // Insere uma liga
        stmt.executeUpdate("INSERT INTO Liga (Nome, Logo, Pais) VALUES ('Premier League', 'logo_premier_league.png', 'Inglaterra')");

        // Insere um time
        stmt.executeUpdate("INSERT INTO Time (Nome, Escudo, AnoFundacao, Titulos, LigaFK) VALUES ('Manchester United', 'escudo_manchester_united.png', 1878, 66, 1)");

        // Insere jogadores
        stmt.executeUpdate("INSERT INTO Menus.Jogador (Nome, Foto, Overall, Idade, Altura, Posicao, Nacionalidade, Playstyle, TimeFK) VALUES ('David de Gea', 'degea.png', 87, 30, 1.92, 'Goleiro', 'Espanha', 'Defensivo', 1)");
        stmt.executeUpdate("INSERT INTO Menus.Jogador (Nome, Foto, Overall, Idade, Altura, Posicao, Nacionalidade, Playstyle, TimeFK) VALUES ('Harry Maguire', 'maguire.png', 82, 28, 1.94, 'Zagueiro', 'Inglaterra', 'Defensivo', 1)");
        stmt.executeUpdate("INSERT INTO Menus.Jogador (Nome, Foto, Overall, Idade, Altura, Posicao, Nacionalidade, Playstyle, TimeFK) VALUES ('Bruno Fernandes', 'bruno.png', 89, 26, 1.79, 'Meio-campo', 'Portugal', 'Ofensivo', 1)");

        System.out.println("Dados inseridos com sucesso!");

        stmt.close();
    }
}
