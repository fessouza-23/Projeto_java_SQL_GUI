package domain;

import service.DatabaseConfiguration;

import java.sql.*;

public class Leagues {
    private int id;
    private String name;
    private String img;
    private String country;

    Connection con = DatabaseConfiguration.getConnection();

    public Leagues() {}

    public Leagues(String name, String img, String country) {
        this.name = name;
        this.img = img;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setNome() {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setLogo() {
        this.img = img;
    }

    public String getCountry() {
        return country;
    }

    public void setPais() {
        this.country = country;
    }

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

    public static void printAll() throws SQLException {
        Connection con = DatabaseConfiguration.getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM Leagues";
        ResultSet rs = stmt.executeQuery("SELECT * FROM Leagues");

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
