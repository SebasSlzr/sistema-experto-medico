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
    
    public String buscarHistorialPorPaciente(String nombrePaciente) throws SQLException {
        String sql = "SELECT p.nombre, p.edad, e.nombre AS enfermedad, d.sintomas_presentados, d.fecha_diagnostico " +
                     "FROM diagnosticos d " +
                     "JOIN pacientes p ON d.id_paciente = p.id_paciente " +
                     "JOIN enfermedades e ON d.id_enfermedad = e.id_enfermedad " +
                     "WHERE p.nombre LIKE ? " +
                     "ORDER BY d.fecha_diagnostico DESC";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + nombrePaciente + "%");
        ResultSet rs = stmt.executeQuery();
        
        StringBuilder resultado = new StringBuilder();
        resultado.append(" HISTORIAL DE: ").append(nombrePaciente).append(" ===\n\n");
        
        boolean encontrado = false;
        while (rs.next()) {
            encontrado = true;
            resultado.append("Paciente: ").append(rs.getString("nombre"));
            resultado.append(" (").append(rs.getInt("edad")).append(" años)\n");
            resultado.append("Enfermedad: ").append(rs.getString("enfermedad")).append("\n");
            resultado.append("Sintomas: ").append(rs.getString("sintomas_presentados")).append("\n");
            resultado.append("Fecha: ").append(rs.getString("fecha_diagnostico")).append("\n");
            resultado.append("-------------------\n");
        }
        
        if (!encontrado) {
            resultado.append("No se encontraron diagnosticos para este paciente.");
        }
        
        rs.close();
        stmt.close();
        
        return resultado.toString();
    }
    
}