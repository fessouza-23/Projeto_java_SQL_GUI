package domain;

import service.DatabaseConfiguration;

import java.sql.*;

/**
 * A classe Teams representa um time com atributos como id, nome, imagem e liga (chave estrangeira).
 * Esta classe fornece métodos para inserir, atualizar, deletar e imprimir todos os times
 * armazenados no banco de dados.
 */
public class Teams {
    private int id;
    private String name;
    private String img;
    private int leagueFK;

    Connection con = DatabaseConfiguration.getConnection();

    /**
     * Construtor padrão da classe Teams.
     */
    public Teams() {}

    /**
     * Construtor da classe Teams com parâmetros.
     *
     * @param name o nome do time
     * @param img o caminho da imagem do time
     */
    public Teams(String name, String img) {
        this.name = name;
        this.img = img;
    }

    // Métodos getter e setter para os atributos do time

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getLeagueFK() {
        return leagueFK;
    }

    public void setLeagueFK(int leagueFK) {
        this.leagueFK = leagueFK;
    }

    /**
     * Insere um novo time no banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void insert() throws SQLException {
        String sql = "INSERT INTO Teams (name, img, leagueFK) VALUES (?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, this.name);
            statement.setString(2, this.img);
            statement.setInt(3, this.leagueFK);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                        System.out.println("New Team ID: " + this.id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Atualiza um time existente no banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void update() throws SQLException {
        String sql = "UPDATE Teams SET name = ?, img = ?, leagueFK = ? WHERE id = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, img);
            statement.setInt(3, leagueFK);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Team updated successfully.");
            } else {
                System.out.println("No Team found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Deleta um time do banco de dados pelo nome.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void delete() throws SQLException {
        String sql = "DELETE FROM Teams WHERE id = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, this.id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Team deleted successfully.");
            } else {
                System.out.println("No Team found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Imprime todos os times armazenados no banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public static void printAll() throws SQLException {
        Connection con = DatabaseConfiguration.getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM Teams";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("ID\tName\tImg\tLeagueFK");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String img = rs.getString("img");
            int leagueFK = rs.getInt("leagueFK");

            System.out.printf("%d\t%s\t%s\t%d%n", id, name, img, leagueFK);
        }
        rs.close();
        stmt.close();
    }
}