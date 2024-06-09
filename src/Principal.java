import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Menus.BoasVindas;  // Certifique-se de importar a classe Menus.BoasVindas do pacote Menus

public class Principal {
    public static void main(String[] args) {
        Connection con = null;
        try {
            // Carrega o driver do HSQLDB
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            // Conecta ao banco de dados
            con = DriverManager.getConnection("jdbc:HypersonicSQL:localhost", "sa", "");

            // Cria as tabelas
            Cria.criarTabelas(con);

            // Insere os dados
            Insere.inserirDados(con);

            // Exibe o menu inicial
            BoasVindas.exibirMenuInicial();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
