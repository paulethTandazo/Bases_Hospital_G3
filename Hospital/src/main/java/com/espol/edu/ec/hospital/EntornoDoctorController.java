package com.espol.edu.ec.hospital;

import Doctor.InformacionDoctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EntornoDoctorController {

    private int cedula;
    @FXML
    public GridPane infoGrid;
    @FXML
    private VBox NombreDoctor;

    private Label bienvenidaLabel;
    private TextField doctorIdField;
    private TextField nombreField;
    private TextField apellidoField;
    private TextField especializacionField;
    private TextField descripcionField;
    private TextField aniosExperienciaField;

    public void setCedula(int cedula) {
        this.cedula = cedula;
        System.out.println("Cédula recibida: " + cedula); // Debug
        initialize();
    }

    public void initialize() {
        // Limpiar el GridPane y VBox antes de agregar elementos
        infoGrid.getChildren().clear();
        NombreDoctor.getChildren().clear();

        InformacionDoctor doctor = InformacionDoctor.getDoctorByCedula(cedula);
        if (doctor != null) {
            bienvenidaLabel = new Label("Bienvenid@ de nuevo, " + doctor.getNombre());
            System.out.println("ENTROOOOOO");
            bienvenidaLabel.setStyle("-fx-font-weight: bold;");
            ImageView imageView = createImageView("Imagenes/mujer.png");
            VBox avatar = new VBox(imageView);
            avatar.setAlignment(Pos.CENTER);
            avatar.setPadding(new Insets(10, 0, 10, 0));
            doctorIdField = createTextField("ID del Doctor: ", doctor.getDoctor_id(), 0);
            doctorIdField.setEditable(false);
            nombreField = createTextField("Nombre: ", doctor.getNombre(), 1);
            apellidoField = createTextField("Apellido: ", doctor.getApellido(), 2);
            especializacionField = createTextField("Tipo Especialización:", doctor.getEspecializacion(), 3);
            descripcionField = createTextField("Descripción del cargo:", doctor.getDescripcionCargo(), 4);
            aniosExperienciaField = createTextField("Años de Experiencia:", String.valueOf(doctor.getAniosExperiencia()), 5);

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

            NombreDoctor.getChildren().addAll(bienvenidaLabel, avatar, infoGrid);
            System.out.println("Datos del doctor cargados correctamente."); // Debug
        } else {
            System.out.println("Doctor no encontrado."); // Debug
        }
    }

    @FXML
    private void handleSalir() {
        Platform.exit();
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
        if (result.get() == buttonTypeContinuar) {
            nombreField.setEditable(true);
            apellidoField.setEditable(true);
            especializacionField.setEditable(true);
            descripcionField.setEditable(true);
            aniosExperienciaField.setEditable(true);
        }
    }

    @FXML
    private void handleGuardar() {
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
            String especializacion = especializacionField.getText();
            String descripcionCargo = descripcionField.getText();
            int aniosExperiencia = Integer.parseInt(aniosExperienciaField.getText());

            try (Connection con = new ConexionSql().estableceConexion()) {
                // Actualizar información del doctor
                String updateDoctorQuery = "UPDATE Doctor SET Nombre = ?, Apellido = ? WHERE Cedula = ?";
                PreparedStatement psDoctor = con.prepareStatement(updateDoctorQuery);
                psDoctor.setString(1, nombre);
                psDoctor.setString(2, apellido);
                psDoctor.setInt(3, cedula);
                int rowsAffectedDoctor = psDoctor.executeUpdate();

                // Obtener el ID de la especialización desde la tabla Experiencia
                String getSpecIdQuery = "SELECT Spec_ID FROM Experiencia WHERE Doctor_ID = ?";
                PreparedStatement psGetSpecId = con.prepareStatement(getSpecIdQuery);
                psGetSpecId.setString(1, doctorIdField.getText());
                ResultSet rs = psGetSpecId.executeQuery();
                String specId = null;
                if (rs.next()) {
                    specId = rs.getString("Spec_ID");
                }

                // Actualizar la descripción y nombre de la especialización en la tabla Especializacion
                String updateEspecializacionQuery = "UPDATE Especializacion SET Descripcion = ?, Nombre = ? WHERE Especializacion_id = ?";
                PreparedStatement psEspecializacion = con.prepareStatement(updateEspecializacionQuery);
                psEspecializacion.setString(1, descripcionCargo);
                psEspecializacion.setString(2, especializacion);
                psEspecializacion.setString(3, specId);
                int rowsAffectedEspecializacion = psEspecializacion.executeUpdate();

                // Actualizar años de experiencia en la tabla Experiencia
                String updateYearsExpQuery = "UPDATE Experiencia SET Years_exp = ? WHERE Doctor_ID = ?";
                PreparedStatement psYearsExp = con.prepareStatement(updateYearsExpQuery);
                psYearsExp.setInt(1, aniosExperiencia);
                psYearsExp.setString(2, doctorIdField.getText());
                int rowsAffectedYearsExp = psYearsExp.executeUpdate();

                if (rowsAffectedDoctor > 0 && rowsAffectedEspecializacion > 0 && rowsAffectedYearsExp > 0) {
                    showConfirmationAlert("Información actualizada correctamente.");
                    nombreField.setEditable(false);
                    apellidoField.setEditable(false);
                    especializacionField.setEditable(false);
                    descripcionField.setEditable(false);
                    aniosExperienciaField.setEditable(false);

                    bienvenidaLabel.setText("Bienvenid@ de nuevo, " + nombre);
                } else {
                    showErrorAlert("No se pudo actualizar la información.");
                }
            } catch (SQLException e) {
                showErrorAlert("Error en la base de datos: " + e.getMessage());
            }
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

    private void estiloBoton(Button button, String imagePath, String bgColor, String textColor, boolean bold) {
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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
