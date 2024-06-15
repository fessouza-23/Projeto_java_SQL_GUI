package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A classe DatabaseConfiguration é responsável por gerenciar a conexão com o banco de dados.
 * Utiliza o padrão Singleton para garantir que apenas uma conexão ativa exista.
 */
public class DatabaseConfiguration {
    private static Connection connection;

    // Construtor privado para evitar instanciação
    private DatabaseConfiguration() { }

    /**
     * Obtém a conexão com o banco de dados. Se a conexão ainda não foi estabelecida,
     * ela será criada.
     *
     * @return uma conexão ativa com o banco de dados
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                try {
                    // Carrega o driver JDBC do HSQLDB
                    Class.forName("org.hsqldb.jdbc.JDBCDriver");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Conecta ao banco de dados
                connection = DriverManager.getConnection("jdbc:hsqldb:mem:mydatabase", "sa", "");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}