/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * FXML Controller class
 * clase que va en la vista del doctor
 *
 * @author Pauleth
 */
public class TratamientoPacientesController {
    @FXML 
    private VBox VBOXPacientes;
    private int cedula;
    public void setCedula(int cedula) {
        this.cedula = cedula;
        System.out.println("Cédula recibida: " + cedula); // Debug
        initialize();
    }
    /**
     * Initializes the controller class.
     */
  
     public void initialize(){
         
     }
     @FXML
     private void handleSalir(){
         Platform.exit();
     }
    
      private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
