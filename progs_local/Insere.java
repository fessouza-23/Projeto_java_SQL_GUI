import java.sql.*;

class Insere {
  public static void main(String[] args) {
    try {
      Class.forName("org.hsql.jdbcDriver");
      Connection con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_curso", "sa", "");
      Statement stmt = con.createStatement();
      stmt.executeUpdate("INSERT INTO AGENDA VALUES ('JOAO', 2345678)");
      stmt.executeUpdate("INSERT INTO AGENDA VALUES ('JOSE', 1234567)");
      stmt.executeUpdate("INSERT INTO AGENDA VALUES ('MARIA', 3456789)");
      stmt.close();
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
