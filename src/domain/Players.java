package domain;

import service.DatabaseConfiguration;

import java.sql.*;

public class Players {
    private int id;
    private String name;
    private String img;
    private Date dateBirth;
    private String height;
    private String position;
    private String country;
    private int teamFK;

    Connection con = DatabaseConfiguration.getConnection();

    public Players() {}

    public Players(String name, String img, Date dateBirth, String height, String position, String country) {
        this.name = name;
        this.img = img;
        this.dateBirth = dateBirth;
        this.height = height;
        this.position = position;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTeamFK() {
        return teamFK;
    }

    public void setTeamFK(int teamFK) {
        this.teamFK = teamFK;
    }

    public void insert() throws SQLException {
        String sql = "INSERT INTO Players (name, img, dateBirth, height, position, country, teamFK) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, this.name);
            statement.setString(2, this.img);
            statement.setDate(3, this.dateBirth);
            statement.setString(4, this.height);
            statement.setString(5, this.position);
            statement.setString(6, this.country);
            statement.setInt(7, this.teamFK);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        this.id = generatedKeys.getInt(1);
                        System.out.println("New Player ID: " + this.id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void update() throws SQLException {
        String sql = "UPDATE Players SET name = ?, img = ?, dateBirth = ?, height = ?, position = ?, country = ?, teamFK = ? WHERE id = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, img);
            statement.setDate(3, dateBirth);
            statement.setString(4, height);
            statement.setString(5, position);
            statement.setString(6, country);
            statement.setInt(7, teamFK);
            statement.setInt(8, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Player updated successfully.");
            } else {
                System.out.println("No Player found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM Players WHERE id = ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, name);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("PLayer deleted successfully.");
            } else {
                System.out.println("No Player found with the name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void printAll() throws SQLException {
        Connection con = DatabaseConfiguration.getConnection();
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM Players";
        ResultSet rs = stmt.executeQuery("SELECT * FROM Players");

        System.out.println("ID\tName\tImg\tDateBirth\tHeight\tPosition\tCountry\tTeamFK");
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String img = rs.getString("img");
            Date dateBirth = rs.getDate("dateBirth");
            String height = rs.getString("height");
            String position = rs.getString("position");
            String country = rs.getString("country");
            int teamFK = rs.getInt("teamFK");

            System.out.printf("%d\t%s\t%s\t%s\t%s\t%s\t%s\t%d%n", id, name, img, dateBirth, height, position, country, teamFK);
        }
        rs.close();
        stmt.close();
    }
}
