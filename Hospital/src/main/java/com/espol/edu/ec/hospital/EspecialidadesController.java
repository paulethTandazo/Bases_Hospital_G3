/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import Doctor.InformacionDoctor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class EspecialidadesController {

    @FXML
    private VBox NombreEspecialidad;
    @FXML
    private VBox InformacionEspecialidadVbox;
    private TableView<InformacionDoctor> TablaxEspecialidades;
    private final Label label1 = new Label("Tus Especialidades");
    private final VBox Espaciado = new VBox();
    private final Button Nuevo = new Button();
    private final Button Editar = new Button();
    private final Button Guardar = new Button();
    private final HBox grupoBotones = new HBox();
    private int cedula;

    public void setCedula(int cedula) {
        System.out.println("Cédula recibida: " + cedula); // Debug
        initialize();
    }

    @FXML
    private void initialize() {

        if (TablaxEspecialidades == null) {
            TablaxEspecialidades = new TableView<>();
            // Creando las columnas para llenar luego los datos
            TableColumn<InformacionDoctor, String> colCodigoDoctor = new TableColumn<>("Código Doctor");
            colCodigoDoctor.setCellValueFactory(new PropertyValueFactory<>("Doctor_id"));
            colCodigoDoctor.setStyle("-fx-alignment: CENTER;");
            TableColumn<InformacionDoctor, String> colNombre = new TableColumn<>("Especialidad");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("especializacion"));
            colNombre.setStyle("-fx-alignment: CENTER;");
            TableColumn<InformacionDoctor, String> colEspecialidad = new TableColumn<>("Descripción");
            colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("DescripcionCargo"));
            colEspecialidad.setStyle("-fx-alignment: CENTER;");
            TableColumn<InformacionDoctor, Integer> colAnios = new TableColumn<>("Años Experiencia");
            colAnios.setCellValueFactory(new PropertyValueFactory<>("aniosExperiencia"));
            colAnios.setStyle("-fx-alignment: CENTER;");
            Nuevo.setText("Nuevo");
            estiloBoton(Nuevo, "Imagenes/boton-editar.png", "#FFFFFF", "#000000", true);
            Editar.setText("Editar");
            estiloBoton(Editar, "Imagenes/boton-editar.png", "#FFFFFF", "#000000", true);
            Guardar.setText("Guardar");
            estiloBoton(Guardar, "Imagenes/disquete.png", "#FFFFFF", "#000000", true);
            label1.setStyle("-fx-font-weight: bold;fx-font-size:18px;");
            Espaciado.setSpacing(50);
            grupoBotones.getChildren().addAll(Nuevo, Editar, Guardar);
            grupoBotones.setStyle("-fx-alignment: CENTER;");
            grupoBotones.setSpacing(50);
            Espaciado.getChildren().add(grupoBotones);
            TablaxEspecialidades.getColumns().addAll(colCodigoDoctor, colNombre, colEspecialidad, colAnios);
            NombreEspecialidad.getChildren().addAll(label1, TablaxEspecialidades, Espaciado);
            NombreEspecialidad.setPadding(new Insets(10, 20, 10, 20));
            NombreEspecialidad.setSpacing(50);
        }

        // Llenar el TableView con datos
        TablaxEspecialidades.setItems(getEspecialidades());
    }

    private ObservableList<InformacionDoctor> getEspecialidades() {
    ObservableList<InformacionDoctor> doctorList = FXCollections.observableArrayList();

    // Conexión a la base de datos y obtención de datos
    try (Connection con = new ConexionSql().estableceConexion()) {
        String consulta = "SELECT d.Doctor_id, e.nombre_de_especializacion, e.Descripcion_Especializacion, e.anios_experiencia "
                + "FROM Doctor d "
                + "JOIN Especializacion e ON d.Doctor_id = e.Doctor_id "
                + "WHERE d.Cedula = ?";

        PreparedStatement st = con.prepareStatement(consulta);
        st.setInt(1, this.cedula);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String doctorId = rs.getString("Doctor_id");
            String nombreEspecializacion = rs.getString("nombreEspecializacion");
            String descripcionEspecializacion = rs.getString("descripcionEspecializacion");
            int aniosExperiencia = rs.getInt("aniosExperiencia");
           
           doctorList.add(new InformacionDoctor(doctorId, nombreEspecializacion, descripcionEspecializacion, aniosExperiencia));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return doctorList;
}

    @FXML
    private void handlePaginaDoctor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EntornoDoctor.fxml"));
            Parent root = loader.load();

            EntornoDoctorController controller = loader.getController();
            controller.setCedula(this.cedula);

            Stage stage = (Stage) InformacionEspecialidadVbox.getScene().getWindow();
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
}
