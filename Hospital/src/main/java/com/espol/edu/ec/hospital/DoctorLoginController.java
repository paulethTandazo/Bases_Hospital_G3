/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class DoctorLoginController {

    @FXML
    private Button Ingresar;
    @FXML
    private Button BotonRegresar;
    @FXML
    private TextField Cedula;

    @FXML
    private PasswordField contrasenia;

    @FXML
    public void comprobarUsuario() {
        if (Cedula.getText().isEmpty() || contrasenia.getText().isEmpty()) {
            showErrorAlert("Llenar todos los campos");
        } else {
            try (Connection con = new ConexionSql().estableceConexion()) {
                String consulta = "CALL almacenadoLogInDoctor(?, ?, ?)";
                CallableStatement st = con.prepareCall(consulta);
                st.setInt(1, Integer.parseInt(Cedula.getText()));
                st.setString(2, contrasenia.getText());
                st.registerOutParameter(3, java.sql.Types.BOOLEAN);

                st.execute();
                boolean result = st.getBoolean(3);

                if (result) {
                    showConfirmationAlert("El usuario es correcto, Ingresando a la aplicación...");
                    // Cambiar a la vista de doctor
                    App.setRoot("EntornoDoctor");
                } else {
                    showErrorAlert("El usuario o la contraseña son incorrectos");
                    Cedula.clear();
                    contrasenia.clear();
                    Cedula.requestFocus();
                }
            } catch (SQLException | IOException e) {
                showErrorAlert("Error en la base de datos: " + e.getMessage());
            }
        }
    }
@FXML
    private void handleBack() {
        try {
            App.setRoot("TipoUsuario");
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de selección de usuario: " + e.getMessage());
        }
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
