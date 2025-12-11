package com.sebas.sistemaexperto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jpl7.Query;
import org.jpl7.Term;

public class PrologQueryExecutor {
    
    public static void cargarArchivoProlog(String ruta) {
        String consultaCargar = "consult('" + ruta.replace("\\", "/") + "')";
        Query q = new Query(consultaCargar);
        if (q.hasSolution()) {
            System.out.println("Archivo Prolog cargado: " + ruta);
        } else {
            System.out.println("Error al cargar archivo Prolog");
        }
        q.close();
    }
    
    public static List<String> diagnosticar(List<String> sintomasUsuario) {
        List<String> enfermedadesEncontradas = new ArrayList<>();
        
        // Construir lista de sintomas en formato Prolog
        StringBuilder listaSintomas = new StringBuilder("[");
        for (int i = 0; i < sintomasUsuario.size(); i++) {
            listaSintomas.append(sintomasUsuario.get(i));
            if (i < sintomasUsuario.size() - 1) {
                listaSintomas.append(", ");
            }
        }
        listaSintomas.append("]");
        
        // Consulta: diagnostico([sintomas], Enfermedad)
        String consulta = "diagnostico(" + listaSintomas.toString() + ", Enfermedad)";
        Query q = new Query(consulta);
        
        while (q.hasMoreSolutions()) {
            Map<String, Term> solucion = q.nextSolution();
            Term enfTerm = solucion.get("Enfermedad");
            if (enfTerm != null) {
                String nombreEnf = enfTerm.toString();
                if (!enfermedadesEncontradas.contains(nombreEnf)) {
                    enfermedadesEncontradas.add(nombreEnf);
                }
            }
        }
        q.close();
        
        return enfermedadesEncontradas;
    }
    
    public static String obtenerRecomendacion(String enfermedad) {
        String consulta = "recomendacion(" + enfermedad + ", R)";
        Query q = new Query(consulta);
        
        if (q.hasSolution()) {
            Map<String, Term> solucion = q.oneSolution();
            Term recTerm = solucion.get("R");
            q.close();
            if (recTerm != null) {
                return recTerm.toString().replace("'", "");
            }
        }
        q.close();
        return "Consultar con un medico";
    }
    
    public static String obtenerCategoria(String enfermedad) {
        String consulta = "categoria(" + enfermedad + ", C)";
        Query q = new Query(consulta);
        
        if (q.hasSolution()) {
            Map<String, Term> solucion = q.oneSolution();
            Term catTerm = solucion.get("C");
            q.close();
            if (catTerm != null) {
                return catTerm.toString();
            }
        }
        q.close();
        return "desconocida";
    }
    
    public static List<String> enfermedadesPorSintoma(String sintoma) {
        List<String> enfermedades = new ArrayList<>();
        
        String consulta = "enfermedades_por_sintoma(" + sintoma + ", Enfermedad)";
        Query q = new Query(consulta);
        
        while (q.hasMoreSolutions()) {
            Map<String, Term> solucion = q.nextSolution();
            Term enfTerm = solucion.get("Enfermedad");
            if (enfTerm != null) {
                String nombre = enfTerm.toString();
                if (!enfermedades.contains(nombre)) {
                    enfermedades.add(nombre);
                }
            }
        }
        q.close();
        
        return enfermedades;
    }
    
    
    public static List<String> diagnosticarPorCategoria(List<String> sintomas, String categoria) {
        List<String> enfermedades = new ArrayList<>();
        
        StringBuilder listaSintomas = new StringBuilder("[");
        for (int i = 0; i < sintomas.size(); i++) {
            listaSintomas.append(sintomas.get(i));
            if (i < sintomas.size() - 1) {
                listaSintomas.append(", ");
            }
        }
        listaSintomas.append("]");
        
        String consulta = "diagnostico_categoria(" + listaSintomas + ", " + categoria + ", Enfermedad)";
        Query q = new Query(consulta);
        
        while (q.hasMoreSolutions()) {
            Map<String, Term> solucion = q.nextSolution();
            Term enfTerm = solucion.get("Enfermedad");
            if (enfTerm != null) {
                String nombre = enfTerm.toString();
                if (!enfermedades.contains(nombre)) {
                    enfermedades.add(nombre);
                }
            }
        }
        q.close();
        
        return enfermedades;
    }
    
    
    public static List<String> enfermedadesCronicas() {
        List<String> enfermedades = new ArrayList<>();
        
        String consulta = "enfermedades_cronicas(Enfermedad)";
        Query q = new Query(consulta);
        
        while (q.hasMoreSolutions()) {
            Map<String, Term> solucion = q.nextSolution();
            Term enfTerm = solucion.get("Enfermedad");
            if (enfTerm != null) {
                String nombre = enfTerm.toString();
                if (!enfermedades.contains(nombre)) {
                    enfermedades.add(nombre);
                }
            }
        }
        q.close();
        
        return enfermedades;
    }
    
}