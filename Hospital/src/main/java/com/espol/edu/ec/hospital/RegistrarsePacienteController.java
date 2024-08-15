/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class RegistrarsePacienteController {

    @FXML
    private VBox RegistrandoPaciente;
    HBox AgregandoTodo = new HBox();
    HBox Campos = new HBox();
    HBox imagenBox = new HBox();
    HBox CamposIngreso = new HBox();
    VBox primeraColumna = new VBox();
    VBox segundaColumna = new VBox();
    HBox botones = new HBox();
    @FXML
    private HBox BienvenidoHbox;
    private TextField ingresandoCedula;

    private TextField ingresandoNombre;
    private TextField ingresandoApellido;
    private TextField ingresandoEdad;
    private DatePicker escogiendoFecha;
    private TextField ingresandoDireccion;
    private PasswordField ingresandoContrasenia;
    private boolean isEditing = false;

    public void initialize() {
        Label bienvenida = new Label("Bienvenido@, Ingresar sus datos");
        bienvenida.setStyle("-fx-font-weight: bold;");
        ImageView imagen = createImageView("Imagenes/mujer.png");
        Label cedula = new Label("Cédula: ");
        cedula.setStyle("-fx-font-weight: bold;");
        Label contrasenia = new Label("Contraseña: ");
        contrasenia.setStyle("-fx-font-weight: bold;");
        Label nombrePaciente = new Label("Nombre: ");
        nombrePaciente.setStyle("-fx-font-weight: bold;");
        Label apellidoPaciente = new Label("Apellido: ");
        apellidoPaciente.setStyle("-fx-font-weight: bold;");
        Label Edad = new Label("Edad: ");
        Edad.setStyle("-fx-font-weight: bold;");
        Label FechaCumpleanios = new Label("F.Cumpleaños: ");
        FechaCumpleanios.setStyle("-fx-font-weight: bold;");
        Label direccion = new Label("Dirección: ");
        direccion.setStyle("-fx-font-weight: bold;");
        ingresandoCedula = new TextField();
        ingresandoContrasenia = new PasswordField();

        ingresandoNombre = new TextField();
        ingresandoApellido = new TextField();
        ingresandoEdad = new TextField();
        escogiendoFecha = new DatePicker();
        ingresandoDireccion = new TextField();
        imagenBox.getChildren().add(imagen);
        imagenBox.setAlignment(Pos.CENTER);
        primeraColumna.getChildren().addAll(cedula, contrasenia, nombrePaciente, apellidoPaciente, Edad, FechaCumpleanios, direccion);
        primeraColumna.setSpacing(50);

        Campos.getChildren().add(primeraColumna);
        Campos.setPadding(new Insets(20));
        segundaColumna.getChildren().addAll(ingresandoCedula, ingresandoContrasenia, ingresandoNombre, ingresandoApellido, ingresandoEdad, escogiendoFecha, ingresandoDireccion);
        segundaColumna.setSpacing(40);
        CamposIngreso.getChildren().add(segundaColumna);
        CamposIngreso.setPadding(new Insets(20));
        AgregandoTodo.getChildren().addAll(Campos, CamposIngreso);
        Button botonGuardar = new Button("Guardar");
        estiloBoton(botonGuardar, "Imagenes/disquete.png", "#FFFFFF", "#000000", true);
        botonGuardar.setAlignment(Pos.CENTER);
        botonGuardar.setOnAction(event -> {
            try {
                handleGuardar();
            } catch (IOException ex) {
                Logger.getLogger(RegistrarsePacienteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Button botonCancelar = new Button("Cancelar");
        estiloBoton(botonCancelar, "Imagenes/error.png", "#FFFFFF", "#000000", true);

        botones.getChildren().addAll(botonGuardar, botonCancelar);
        botones.setAlignment(Pos.CENTER);
        botones.setSpacing(15);
        BienvenidoHbox.getChildren().add(bienvenida);
        RegistrandoPaciente.getChildren().addAll(imagenBox, AgregandoTodo, botones);
        RegistrandoPaciente.setPadding(new Insets(20));
        RegistrandoPaciente.setSpacing(40);
        RegistrandoPaciente.setAlignment(Pos.CENTER);
    }

    private ImageView createImageView(String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public void estiloBoton(Button button, String imagePath, String bgColor, String textColor, boolean bold) {
        // Añadir imagen al botón
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20); // Ajustar altura de la imagen
        imageView.setFitWidth(20);  // Ajustar ancho de la imagen
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);

        // Cambiar color de fondo del botón
        button.setStyle("-fx-background-color: " + bgColor + ";");

        // Cambiar tipo de letra a negrita y color del texto
        if (bold) {
            button.setFont(Font.font("System", javafx.scene.text.FontWeight.BOLD, 12));
        }
        button.setTextFill(Color.web(textColor));
    }

       @FXML
    private void handleGuardar() throws IOException {
        // Validar que todos los campos estén llenos
        if (ingresandoCedula.getText().isEmpty()
                || ingresandoContrasenia.getText().isEmpty()
                || ingresandoNombre.getText().isEmpty()
                || ingresandoApellido.getText().isEmpty()
                || ingresandoEdad.getText().isEmpty()
                || escogiendoFecha.getValue() == null
                || ingresandoDireccion.getText().isEmpty()) {
            showErrorAlert("No puedes guardar, necesitas llenar todos los campos");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Guardado");
        alert.setHeaderText("¿Estás seguro de que deseas guardar los cambios?");
        alert.setContentText("Presiona Continuar para guardar o Cancelar para regresar.");

        ButtonType buttonTypeContinuar = new ButtonType("Continuar");
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(buttonTypeContinuar, buttonTypeCancelar);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeContinuar) {
            // Obtener los datos del formulario
            int cedula = Integer.parseInt(ingresandoCedula.getText());
            String contrasenia = ingresandoContrasenia.getText();
            String nombre = ingresandoNombre.getText();
            String apellido = ingresandoApellido.getText();
            int edad = Integer.parseInt(ingresandoEdad.getText());
            java.sql.Date fechaCumpleanos = java.sql.Date.valueOf(escogiendoFecha.getValue());
            String direccion = ingresandoDireccion.getText();

            try (Connection con = new ConexionSql().estableceConexion()) {
                // Obtener el siguiente ID de paciente
                String obtenerSiguienteIDProcedimiento = "{call GetNextPacienteId(?)}";
                String pacienteID;
                try (CallableStatement cs = con.prepareCall(obtenerSiguienteIDProcedimiento)) {
                    cs.registerOutParameter(1, java.sql.Types.VARCHAR);
                    cs.execute();
                    pacienteID = cs.getString(1);
                }

                // Insertar el nuevo paciente en la base de datos
                String insertarQuery = "INSERT INTO Paciente (Paciente_id, Cedula, Contrasenia, Nombre, Apellido, Edad, Fcumpleanos, Direccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(insertarQuery)) {
                    ps.setString(1, pacienteID);
                    ps.setInt(2, cedula);
                    ps.setString(3, contrasenia);
                    ps.setString(4, nombre);
                    ps.setString(5, apellido);
                    ps.setInt(6, edad);
                    ps.setDate(7, fechaCumpleanos);
                    ps.setString(8, direccion);

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        showConfirmationAlert("Paciente guardado exitosamente" );
                        // Limpiar los campos después de guardar
                        clearFields();
                        App.setRoot("PaginaPaciente");
                    } else {
                        showErrorAlert("No se pudo guardar el paciente.");
                    }
                }
            } catch (SQLException e) {
                showErrorAlert("Error en la base de datos: " + e.getMessage());
            }
        }
    }

    private void showErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    private void showConfirmationAlert(String message) {
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Éxito");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText(message);
        confirmationAlert.showAndWait();
    }

    private void clearFields() {
        ingresandoContrasenia.clear();
        ingresandoCedula.clear();
        ingresandoNombre.clear();
        ingresandoApellido.clear();
        ingresandoEdad.clear();
        escogiendoFecha.setValue(null);
        ingresandoDireccion.clear();
    }

    
    public void HandleCancelar() {

    }

 

}
