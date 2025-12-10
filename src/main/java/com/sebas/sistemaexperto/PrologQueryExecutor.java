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