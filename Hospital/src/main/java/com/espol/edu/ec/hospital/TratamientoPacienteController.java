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
 *
 * @author Pauleth
 */
public class TratamientoPacienteController {

    @FXML
    private VBox TratamientoVbox;
    @FXML
    private VBox TratamientoPacienteVbox;

    private int cedula;

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
    @FXML
    private void handleSalir() {
        Platform.exit();
    }

     @FXML
    private void handleVolver() {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InformacionPaciente.fxml"));
            Parent root = loader.load();

            InformacionPacienteController controller = loader.getController();
            controller.setCedula(this.cedula);

            Stage stage = (Stage) TratamientoVbox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de información del paciente: " + e.getMessage());
        }
    }
    

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
