/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.edu.ec.hospital;

/**
 *
 * @author leoza
 */
public class Medicinas {
    private String codigoTratamiento;
    private String nombreMedicamento;
    private String nombreDoctor;

    public Medicinas(String codigoTratamiento, String nombreMedicamento, String nombreDoctor) {
        this.codigoTratamiento = codigoTratamiento;
        this.nombreMedicamento = nombreMedicamento;
        this.nombreDoctor = nombreDoctor;
    }

    // Getters y setters para cada atributo

    public String getCodigoTratamiento() {
        return codigoTratamiento;
    }

    public void setCodigoTratamiento(String codigoTratamiento) {
        this.codigoTratamiento = codigoTratamiento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }
}

