package com.sebas.sistemaexperto;

import java.sql.SQLException;
import java.util.List;
import org.jpl7.Query;

public class FactsBuilder {
    
    public static void cargarEnfermedadesAProlog() {
        EnfermedadDAO dao = new EnfermedadDAO();
        
        try {
            List<Enfermedad> lista = dao.obtenerTodas();
            
            for (Enfermedad e : lista) {
                // Construir la lista de sintomas en formato Prolog: [s1, s2, s3]
                StringBuilder sintomasProlog = new StringBuilder("[");
                List<String> sintomas = e.getSintomas();
                
                for (int i = 0; i < sintomas.size(); i++) {
                    sintomasProlog.append(sintomas.get(i));
                    if (i < sintomas.size() - 1) {
                        sintomasProlog.append(", ");
                    }
                }
                sintomasProlog.append("]");
                
                // Construir el hecho: enfermedad(nombre, [sintomas], categoria, recomendacion)
                StringBuilder hecho = new StringBuilder();
                hecho.append("assertz(enfermedad(")
                     .append(e.getNombre()).append(", ")
                     .append(sintomasProlog.toString()).append(", ")
                     .append(e.getCategoria()).append(", '")
                     .append(e.getRecomendacion()).append("'))");
                
                // Ejecutar en Prolog
                Query q = new Query(hecho.toString());
                if (q.hasSolution()) {
                    System.out.println("Hecho cargado: " + e.getNombre());
                }
                q.close();
            }
            
            System.out.println("Total enfermedades cargadas: " + lista.size());
            
        } catch (SQLException ex) {
            System.out.println("Error cargando enfermedades: " + ex.getMessage());
        }
    }
}