package com.pratica4.application.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private final String password = "Tatazzo@2024#";
    private final String database = "cadastro_clientes";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3367/" + database, "usuario", password);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
