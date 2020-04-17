package com.houarizegai.spocom.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String HOST = "localhost";
    private static final String PORT = "localhost";
    private static final String DB_NAME = "spocom_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection con;

    public static Connection getConnection() {
        if(con == null) {
            try {
                con = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DB_NAME), USERNAME, PASSWORD);
            } catch(SQLException se) {
                se.printStackTrace();
            }
        }

        return con;
    }
}
