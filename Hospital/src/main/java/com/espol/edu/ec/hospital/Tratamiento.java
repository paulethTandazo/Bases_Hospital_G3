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
    private String enfermedad;
    private String nombreDoctor;
    private String apellidoDoctor;
    private String nombreDepartamento;

    public Tratamiento(String codigoTratamiento, String enfermedad, String nombreDoctor, String apellidoDoctor, String nombreDepartamento) {
        this.codigoTratamiento = codigoTratamiento;
        this.enfermedad = enfermedad;
        this.nombreDoctor = nombreDoctor;
        this.apellidoDoctor = apellidoDoctor;
        this.nombreDepartamento = nombreDepartamento;
    }

    // Getters y setters

    public String getCodigoTratamiento() {
        return codigoTratamiento;
    }

    public void setCodigoTratamiento(String codigoTratamiento) {
        this.codigoTratamiento = codigoTratamiento;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public String getApellidoDoctor() {
        return apellidoDoctor;
    }

    public void setApellidoDoctor(String apellidoDoctor) {
        this.apellidoDoctor = apellidoDoctor;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }
}