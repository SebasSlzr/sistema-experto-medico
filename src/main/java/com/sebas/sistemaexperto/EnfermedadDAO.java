package com.sebas.sistemaexperto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnfermedadDAO {
    
    private Connection conn;
    
    public EnfermedadDAO() {
        this.conn = MySQLConnection.getInstance().getConnection();
    }
    
    public List<Enfermedad> obtenerTodas() throws SQLException {
        List<Enfermedad> lista = new ArrayList<>();
        
        String sql = "SELECT e.id_enfermedad, e.nombre, c.nombre AS categoria, e.recomendacion " +
                     "FROM enfermedades e " +
                     "JOIN categorias c ON e.id_categoria = c.id_categoria";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Enfermedad enf = new Enfermedad(
                rs.getInt("id_enfermedad"),
                rs.getString("nombre"),
                rs.getString("categoria"),
                rs.getString("recomendacion")
            );
            
            // Cargar los sintomas de esta enfermedad
            cargarSintomas(enf);
            lista.add(enf);
        }
        
        rs.close();
        stmt.close();
        return lista;
    }
    
    private void cargarSintomas(Enfermedad enf) throws SQLException {
        String sql = "SELECT s.nombre FROM sintomas s " +
                     "JOIN enfermedad_sintoma es ON s.id_sintoma = es.id_sintoma " +
                     "WHERE es.id_enfermedad = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, enf.getId());
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            enf.agregarSintoma(rs.getString("nombre"));
        }
        
        rs.close();
        stmt.close();
    }
}