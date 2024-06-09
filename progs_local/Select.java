import java.sql.*;

class Select {
  public static void main(String[] args) {
    try {
      Class.forName("org.hsql.jdbcDriver");
      Connection con = DriverManager.getConnection("jdbc:HypersonicSQL:bd_curso", "sa", "");
      Statement stmt = con.createStatement();
      //~ ResultSet rs = stmt.executeQuery("SELECT * FROM AGENDA ORDER BY NOME");
      ResultSet rs = stmt.executeQuery("SELECT * FROM AGENDA WHERE NOME LIKE '%JO%' ORDER BY TELEFONE");
      while (rs.next()) {
        String s = rs.getString("NOME");
        int n = rs.getInt("TELEFONE");
        System.out.println(s + "   " + n);
      }
      stmt.close();
      con.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
