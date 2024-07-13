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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pauleth
 */
public class InformacionPacienteController {

    @FXML
    public VBox NombrePaciente;
    @FXML
    public VBox InformacionPacienteVbox;
    private int cedula;

    public void setCedula(int cedula) {
        this.cedula = cedula;
        initialize();
    }

    public void initialize() {
        InformacionPersonal paciente = InformacionPersonal.getPacienteByCedula(cedula);
        if (paciente != null) {

            // Crear y configurar los HBox para cada campo
            HBox bienvenidoBox = createHBox("Bienvenido de nuevo, ", paciente.getNombre());
            ImageView imageView = createImageView("Imagenes/mujer.png");

            VBox avatar = new VBox(imageView);
            HBox pacienteIdBox = createHBox("ID del Paciente: ", paciente.getPaciente_id());
            HBox nombreBox = createHBox("Nombre: ", paciente.getNombre());
            HBox apellidoBox = createHBox("Apellido: ", paciente.getApellido());
            HBox edadBox = createHBox("Edad: ", String.valueOf(paciente.getEdad()));
            HBox fcumpleanosBox = createHBox("Fecha de Cumpleaños: ", paciente.getFcumpleanos());
            HBox direccionBox = createHBox("Dirección: ", paciente.getDireccion());

            // Agregar los HBox al VBox
            NombrePaciente.getChildren().addAll(bienvenidoBox,avatar, pacienteIdBox, nombreBox, apellidoBox, edadBox, fcumpleanosBox, direccionBox);
        }
    }

    private HBox createHBox(String labelText, String valueText) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold;"); // Aplicar negrita al título
        Label value = new Label(valueText);

        HBox hbox = new HBox(10); // Espaciado entre los elementos
        hbox.getChildren().addAll(label, value);
        return hbox;
    }
     private ImageView createImageView(String imagePath) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // Ajusta el ancho deseado
        imageView.setPreserveRatio(true); // Mantén la relación de aspecto
        return imageView;
    }


    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
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
    
    }


