/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import Paciente.InformacionPersonal;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InformacionPacienteController {

    @FXML
    public VBox NombrePaciente;
    @FXML
    public GridPane infoGrid;

    private Label bienvenidaLabel;

    private TextField nombreField;
    private TextField apellidoField;
    private TextField edadField;
    private TextField fcumpleanosField;
    private TextField direccionField;
    private TextField pacienteIdField;

    private int cedula;
    private boolean isEditing = false;

    public void setCedula(int cedula) {
        this.cedula = cedula;
        initialize();
    }

    public void initialize() {
        // Limpiar el GridPane y VBox antes de agregar elementos
        infoGrid.getChildren().clear();
        NombrePaciente.getChildren().clear();

        InformacionPersonal paciente = InformacionPersonal.getPacienteByCedula(cedula);
        if (paciente != null) {
            bienvenidaLabel = new Label("Bienvenid@ de nuevo, " + paciente.getNombre());
            bienvenidaLabel.setStyle("-fx-font-weight: bold;");
            ImageView imageView = createImageView("Imagenes/mujer.png");
            VBox avatar = new VBox(imageView);
            avatar.setAlignment(Pos.CENTER);
            avatar.setPadding(new Insets(10, 0, 10, 0));
            pacienteIdField = createTextField("ID del Paciente: ", paciente.getPaciente_id(), 0);
            pacienteIdField.setEditable(false);
            nombreField = createTextField("Nombre: ", paciente.getNombre(), 1);
            apellidoField = createTextField("Apellido: ", paciente.getApellido(), 2);
            edadField = createTextField("Edad: ", String.valueOf(paciente.getEdad()), 3);
            fcumpleanosField = createTextField("Fecha de Cumpleaños: ", paciente.getFcumpleanos(), 4);
            direccionField = createTextField("Dirección: ", paciente.getDireccion(), 5);
            Button editarButton = new Button("Editar  ");
            estiloBoton(editarButton, "Imagenes/boton-editar.png", "#FFFFFF", "#000000", true);
            editarButton.setOnAction(e -> handleEditar());

            Button guardarButton = new Button("Guardar");
            estiloBoton(guardarButton, "Imagenes/disquete.png", "#FFFFFF", "#000000", true);
            guardarButton.setOnAction(e -> handleGuardar());

            HBox buttonBox = new HBox(10, editarButton, guardarButton);
            buttonBox.setPadding(new Insets(20, 0, 0, 0)); // Añadir espacio superior a los botones
            buttonBox.setAlignment(Pos.CENTER);
            infoGrid.add(buttonBox, 0, 6, 2, 1); // Colocar botones en la fila 7 y que abarquen dos columnas

            NombrePaciente.getChildren().addAll(bienvenidaLabel, avatar, infoGrid);
        }
    }

    private TextField createTextField(String labelText, String valueText, int rowIndex) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;");
        TextField textField = new TextField(valueText);
        textField.setEditable(false);

        infoGrid.add(label, 0, rowIndex);
        infoGrid.add(textField, 1, rowIndex);
        return textField;
    }

    private ImageView createImageView(String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    @FXML
    private void handleEditar() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación de Edición");
    alert.setHeaderText("¿Estás seguro de que deseas editar la información?");
    alert.setContentText("Presiona continuar para editar o cancelar para regresar.");

    ButtonType buttonTypeContinuar = new ButtonType("Continuar");
    ButtonType buttonTypeCancelar = new ButtonType("Cancelar");

    alert.getButtonTypes().setAll(buttonTypeContinuar, buttonTypeCancelar);

    Optional<ButtonType> result = alert.showAndWait();
        isEditing = true;
        nombreField.setEditable(true);
        apellidoField.setEditable(true);
        edadField.setEditable(true);
        fcumpleanosField.setEditable(true);
        direccionField.setEditable(true);
    }

 @FXML
private void handleGuardar() {
    if (!isEditing) {
        showErrorAlert("No puedes guardar, primero necesitas actualizar los campos.");
        return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmación de Guardado");
    alert.setHeaderText("¿Estás seguro de que deseas guardar los cambios?");
    alert.setContentText("Presiona continuar para guardar o cancelar para regresar.");

    ButtonType buttonTypeContinuar = new ButtonType("Continuar");
    ButtonType buttonTypeCancelar = new ButtonType("Cancelar");

    alert.getButtonTypes().setAll(buttonTypeContinuar, buttonTypeCancelar);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == buttonTypeContinuar) {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        int edad = Integer.parseInt(edadField.getText());
        String fcumpleanos = fcumpleanosField.getText();
        String direccion = direccionField.getText();

        try (Connection con = new ConexionSql().estableceConexion()) {
            String updateQuery = "UPDATE Paciente SET Nombre = ?, Apellido = ?, Edad = ?, Fcumpleanos = ?, Direccion = ? WHERE Cedula = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, edad);
            ps.setString(4, fcumpleanos);
            ps.setString(5, direccion);
            ps.setInt(6, cedula);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                showConfirmationAlert("Información actualizada correctamente.");
                isEditing = false;
                nombreField.setEditable(false);
                apellidoField.setEditable(false);
                edadField.setEditable(false);
                fcumpleanosField.setEditable(false);
                direccionField.setEditable(false);
                 // Actualizar el texto del Label de bienvenida
                bienvenidaLabel.setText("Bienvenid@ de nuevo, " + nombre);
            } else {
                showErrorAlert("No se pudo actualizar la información.");
            }
        } catch (SQLException e) {
            showErrorAlert("Error en la base de datos: " + e.getMessage());
        }
    }
}


    @FXML
    private void handleSalir() {
        Platform.exit();
    }

    @FXML
    private void handleTratamiento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TratamientoPaciente.fxml"));
            Parent root = loader.load();

            TratamientoPacienteController controller = loader.getController();
            controller.setCedula(this.cedula);

            Stage stage = (Stage) NombrePaciente.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de tratamiento: " + e.getMessage());
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
}
