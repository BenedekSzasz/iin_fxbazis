package com.example.models;

import java.sql.Connection;
import java.sql.SQLException;

public class Mariadb implements Database{
    
    @Override 
    public Connection connect() {
        try {
            return tryConnect();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Connection tryConnect() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/fxbazis";
        String user = "fxbazis";
        String password = "titok";
        return java.sql.DriverManager.getConnection(url, user, password);
    }
}
