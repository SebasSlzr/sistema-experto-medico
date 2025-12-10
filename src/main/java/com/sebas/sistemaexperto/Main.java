package com.sebas.sistemaexperto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        // Cargar archivo Prolog
        PrologQueryExecutor.cargarArchivoProlog("prolog.pl");
        
        // Cargar enfermedades desde MySQL a Prolog
        FactsBuilder.cargarEnfermedadesAProlog();
        
        // Probar diagnostico con fiebre y tos
        List<String> sintomas = new ArrayList<>();
        sintomas.add("fiebre");
        sintomas.add("tos");
        
        System.out.println("Sintomas: " + sintomas);
        
        List<String> resultados = PrologQueryExecutor.diagnosticar(sintomas);
        
        for (String enf : resultados) {
            System.out.println("Posible enfermedad: " + enf);
            System.out.println("Recomendacion: " + PrologQueryExecutor.obtenerRecomendacion(enf));
        }
    }
}