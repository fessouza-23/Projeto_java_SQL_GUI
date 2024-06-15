package domain;

import service.DatabaseConfiguration;

import java.sql.*;

/**
 * A classe Leagues representa uma liga com atributos como id, nome, imagem e país.
 * Esta classe fornece métodos para inserir, atualizar, deletar e imprimir todas as ligas
 * armazenadas no banco de dados.
 */
public class Leagues {
    private int id;
    private String name;
    private String img;
    private String country;

    Connection con = DatabaseConfiguration.getConnection();

    /**
     * Construtor padrão da classe Leagues.
     */
    public Leagues() {}

    /**
     * Construtor da classe Leagues com parâmetros.
     *
     * @param name o nome da liga
     * @param img o caminho da imagem da liga
     * @param country o país da liga
     */
    public Leagues(String name, String img, String country) {
        this.name = name;
        this.img = img;
        this.country = country;
    }

    // Métodos getter e setter para os atributos da liga

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Insere uma nova liga no banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void insert() throws SQLException {
        String sql = "INSERT INTO Leagues (name, img, country) VALUES (?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, this.name);
            statement.setString(2, this.img);
            statement.setString(3, this.country);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                        System.out.println("New League ID: " + this.id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Atualiza uma liga existente no banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void update() throws SQLException {
        String sql = "UPDATE Leagues SET name = ?, img = ?, country = ? WHERE id = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, img);
            statement.setString(3, country);
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("League updated successfully.");
            } else {
                System.out.println("No League found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Deleta uma liga do banco de dados pelo nome.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public void delete() throws SQLException {
        String sql = "DELETE FROM Leagues WHERE name = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, name);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Leagues deleted successfully.");
            } else {
                System.out.println("No Leagues found with the name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Imprime todas as ligas armazenadas no banco de dados.
     *
     * @throws SQLException se ocorrer um erro ao acessar o banco de dados
     */
    public static void printAll() throws SQLException {
        Connection con = DatabaseConfiguration.getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM Leagues";
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("ID\tName\tImg\tCountry");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String img = rs.getString("img");
            String country = rs.getString("country");

            System.out.printf("%d\t%s\t%s\t%s%n", id, name, img, country);
        }
        rs.close();
        stmt.close();
    }
}