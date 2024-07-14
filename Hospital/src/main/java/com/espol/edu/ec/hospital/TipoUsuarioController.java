/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Pauleth
 */
public class TipoUsuarioController {
 @FXML
    public void handleDoctorLogin() {
        try {
            App.setRoot("DoctorLogin");
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de inicio de sesión de doctor: " + e.getMessage());
        }
    }
  @FXML
    public void handlePacienteLogin() {
        try {
            App.setRoot("PaginaPaciente");
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de inicio de sesión de paciente: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
