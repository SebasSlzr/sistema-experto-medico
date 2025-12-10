package com.sebas.sistemaexperto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    
    private static MySQLConnection instance;
    private Connection connection;
    
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "sistema_experto_medico";
    private static final String USER = "root";
    private static final String PASSWORD = "Sebasleak28";  
    
    private MySQLConnection() {
        try {
            String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
            this.connection = DriverManager.getConnection(url, USER, PASSWORD);
            System.out.println("Conexion exitosa a MySQL");
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
    
    public static MySQLConnection getInstance() {
        if (instance == null) {
            instance = new MySQLConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
}