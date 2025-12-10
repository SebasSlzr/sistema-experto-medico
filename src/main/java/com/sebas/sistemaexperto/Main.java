package com.sebas.sistemaexperto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        // Cargar Prolog y enfermedades
        PrologQueryExecutor.cargarArchivoProlog("prolog.pl");
        FactsBuilder.cargarEnfermedadesAProlog();
        
        System.out.println("\n--- PRUEBA 1: Diagnostico con fiebre y tos ---");
        List<String> sintomas1 = new ArrayList<>();
        sintomas1.add("fiebre");
        sintomas1.add("tos");
        
        List<String> resultados1 = PrologQueryExecutor.diagnosticar(sintomas1);
        for (String enf : resultados1) {
            System.out.println("-> " + enf);
        }
        
        System.out.println("\n--- PRUEBA 2: Solo enfermedades VIRALES con fiebre y tos ---");
        List<String> virales = PrologQueryExecutor.diagnosticarPorCategoria(sintomas1, "viral");
        for (String enf : virales) {
            System.out.println("-> " + enf);
        }
        
        System.out.println("\n--- PRUEBA 3: Verificar si diabetes es cronica ---");
        boolean diabetesCronica = PrologQueryExecutor.esCronica("diabetes");
        System.out.println("diabetes es cronica: " + diabetesCronica);
        
        System.out.println("\n--- PRUEBA 4: Verificar si gripe es viral ---");
        boolean gripeViral = PrologQueryExecutor.esViral("gripe");
        System.out.println("gripe es viral: " + gripeViral);
        
        System.out.println("\n--- PRUEBA 5: Enfermedades con sed ---");
        List<String> conSed = PrologQueryExecutor.enfermedadesPorSintoma("sed");
        for (String enf : conSed) {
            System.out.println("-> " + enf);
        }
    }
}