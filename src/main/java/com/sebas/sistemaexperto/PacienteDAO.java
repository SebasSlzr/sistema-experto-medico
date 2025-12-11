package com.sebas.sistemaexperto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PacienteDAO {
    
    private Connection conn;
    
    public PacienteDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }
    
    public int guardarPaciente(String nombre, int edad) throws SQLException {
        String sql = "INSERT INTO pacientes (nombre, edad) VALUES (?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, nombre);
        stmt.setInt(2, edad);
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1);
        }
        
        rs.close();
        stmt.close();
        return id;
    }
    
    public void guardarDiagnostico(int idPaciente, int idEnfermedad, String sintomas) throws SQLException {
        String sql = "INSERT INTO diagnosticos (id_paciente, id_enfermedad, sintomas_presentados) VALUES (?, ?, ?)";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idPaciente);
        stmt.setInt(2, idEnfermedad);
        stmt.setString(3, sintomas);
        stmt.executeUpdate();
        stmt.close();
    }
    
    public int obtenerIdEnfermedad(String nombre) throws SQLException {
        String sql = "SELECT id_enfermedad FROM enfermedades WHERE nombre = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nombre);
        ResultSet rs = stmt.executeQuery();
        
        int id = 0;
        if (rs.next()) {
            id = rs.getInt("id_enfermedad");
        }
        
        rs.close();
        stmt.close();
        return id;
    }
}