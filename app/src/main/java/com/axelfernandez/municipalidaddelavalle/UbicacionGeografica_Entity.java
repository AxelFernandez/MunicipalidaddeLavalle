package com.axelfernandez.municipalidaddelavalle;

import java.util.List;

/**
 * Created by axelfernandez on 5/9/17.
 */

public class UbicacionGeografica_Entity {
    String titulo;
    String descripcion;

private List<UbicacionGeografica_Entity> ubicacion;
    public UbicacionGeografica_Entity(String titulo, String descripcion){
        this.titulo=titulo;
        this.descripcion=descripcion;


    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        descripcion = descripcion;
    }





}

