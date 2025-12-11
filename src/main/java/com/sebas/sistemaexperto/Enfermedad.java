package com.sebas.sistemaexperto;

import java.util.ArrayList;
import java.util.List;

public class Enfermedad {
    
    private int id;
    private String nombre;
    private String categoria;
    private String recomendacion;
    private List<String> sintomas;
    
    
    public Enfermedad(int id, String nombre, String categoria, String recomendacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.recomendacion = recomendacion;
        this.sintomas = new ArrayList<>();
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getRecomendacion() {
        return recomendacion;
    }
    
    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }
    
    public List<String> getSintomas() {
        return sintomas;
    }
    
    public void setSintomas(List<String> sintomas) {
        this.sintomas = sintomas;
    }
    
    public void agregarSintoma(String sintoma) {
        this.sintomas.add(sintoma);
    }
}