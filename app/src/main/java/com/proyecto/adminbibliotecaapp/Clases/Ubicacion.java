package com.proyecto.adminbibliotecaapp.Clases;

public class Ubicacion {

    private String idUbicacion;
    private String nomUbicacion;

    public Ubicacion() { }

    public Ubicacion(String idUbicacion, String nomUbicacion) {
        this.idUbicacion = idUbicacion;
        this.nomUbicacion = nomUbicacion;
    }

    public String getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(String idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getNomUbicacion() {
        return nomUbicacion;
    }

    public void setNomUbicacion(String nomUbicacion) {
        this.nomUbicacion = nomUbicacion;
    }


    @Override
    public String toString() {
        return this.idUbicacion+","+this.nomUbicacion;
    }
}
