package repository;

import service.DatabaseConfiguration;

import java.sql.Connection;

/**
 * A classe ConnectDB é responsável por estabelecer uma conexão com o banco de dados.
 * Ela utiliza a configuração de banco de dados definida na classe DatabaseConfiguration.
 */
public class ConnectDB {

    /**
     * Estabelece uma conexão com o banco de dados.
     *
     * @return uma conexão ativa com o banco de dados
     */
    public Connection connect() {
        return DatabaseConfiguration.getConnection();
    }

}
