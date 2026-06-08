package com.pratica4.application.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String password = "Tatazzo@2024#";
    private String database = "cadastro_clientes";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/" + database, "root", password);
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
