/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.edu.ec.hospital;

/**
 *
 * @author leoza
 */
public class Tratamiento {
    private String codigoTratamiento;
    private String nombreDoctor;
    private String nombreDepartamento;
    private String fechaInicio;
    private String fechaFin;

    public Tratamiento(String codigoTratamiento, String nombreDoctor, String nombreDepartamento, String fechaInicio, String fechaFin) {
        this.codigoTratamiento = codigoTratamiento;
        this.nombreDoctor = nombreDoctor;
        this.nombreDepartamento = nombreDepartamento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y setters para cada atributo
    public String getCodigoTratamiento() {
        return codigoTratamiento;
    }

    public void setCodigoTratamiento(String codigoTratamiento) {
        this.codigoTratamiento = codigoTratamiento;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }
}
