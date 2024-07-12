/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class IniciarSesionController {

    @FXML
    private Button Ingresar;

    @FXML
    private TextField Cedula;

    @FXML
    private PasswordField contrasenia;

    @FXML
    private RadioButton pacienteRadio;

    @FXML
    private RadioButton doctorRadio;

    @FXML
    private ToggleGroup TipoUsuario;

    @FXML
    public void comprobarUsuario() {
        if (Cedula.getText().isEmpty() || contrasenia.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Llenar todos los campos");
            alerta.showAndWait();
        } else {
            boolean esPaciente = pacienteRadio.isSelected();
            String procedimiento = esPaciente ? "CALL almacenadoLogInPaciente(?, ?, ?)" : "CALL almacenadoLogInDoctor(?, ?, ?)";
            try (Connection con = new ConexionSql().estableceConexion()) {
                CallableStatement st = con.prepareCall(procedimiento);
                st.setInt(1, Integer.parseInt(Cedula.getText()));
                st.setString(2, contrasenia.getText());
                st.registerOutParameter(3, java.sql.Types.BOOLEAN);

                st.execute();
                boolean result = st.getBoolean(3);

                if (result) {
                    Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                    alerta.setContentText("El usuario es correcto, Ingresando a la aplicación...");
                    alerta.showAndWait();

                    // Lógica para cambiar a la vista correspondiente según el tipo de usuario
                    String vista = esPaciente ? "IniciarSesion" : "PaginaPaciente";
                    try {
                        App.setRoot(vista);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setContentText("El usuario o la contraseña son incorrectos");
                    alerta.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setContentText("Error de conexión: " + e.getMessage());
                alerta.showAndWait();
            }
        }
    }
}
