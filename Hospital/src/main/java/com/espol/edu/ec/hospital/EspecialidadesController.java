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
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private boolean isEditing = false;

    public void setCedula(int cedula) {
        this.cedula = cedula;
        System.out.println("Cédula recibida dentro del controlador especialidades: " + cedula); // Debug
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
            String consulta = "SELECT e.Doctor_id, e.nombre_de_especializacion, e.Descripcion_Especializacion, e.anios_experiencia "
                    + "FROM Especializacion e "
                    + "JOIN Doctor d ON e.Doctor_id = d.Doctor_id "
                    + "WHERE d.Cedula = ?";

            PreparedStatement st = con.prepareStatement(consulta);
            st.setInt(1, this.cedula); // Usar la cédula para buscar el Doctor_id
            ResultSet rs = st.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron especialidades para la cédula: " + this.cedula);
            }

            while (rs.next()) {
                String doctorId = rs.getString("Doctor_id");
                String nombreEspecializacion = rs.getString("nombre_de_especializacion");
                String descripcionEspecializacion = rs.getString("Descripcion_Especializacion");
                int aniosExperiencia = rs.getInt("anios_experiencia");

                doctorList.add(new InformacionDoctor(doctorId, nombreEspecializacion, descripcionEspecializacion, aniosExperiencia));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorList;
    }

    @FXML
    private void handleNuevo() {
        InformacionDoctor nuevaEspecialidad = new InformacionDoctor("", "", "", 0);

        // Agregar la nueva especialidad a la tabla
        TablaxEspecialidades.getItems().add(nuevaEspecialidad);
    }
//@FXML
//private void handleGuardar() {
//    ObservableList<InformacionDoctor> especialidades = TablaxEspecialidades.getItems();
//    
//    try (Connection con = new ConexionSql().estableceConexion()) {
//        // Recorrer cada especialidad en la tabla
//        for (InformacionDoctor especialidad : especialidades) {
//            if (especialidad.getDoctorId() == null || especialidad.getDoctorId().isEmpty()) {
//                especialidad.setDoctorId(getDoctorIdByCedula(con, this.cedula)); // Asignar el Doctor_id correspondiente si no está definido
//            }
//            
//            // Verificar si la especialidad ya existe en la base de datos y decidir si se debe insertar o actualizar
//            if (especialidadEsNueva(especialidad)) {
//                // Insertar nueva especialidad
//                String insertQuery = "INSERT INTO Especializacion (Doctor_id, nombre_de_especializacion, Descripcion_Especializacion, anios_experiencia) "
//                                   + "VALUES (?, ?, ?, ?)";
//                try (PreparedStatement ps = con.prepareStatement(insertQuery)) {
//                    ps.setString(1, especialidad.getDoctor_id());
//                    ps.setString(2, especialidad.getEspecializacion());
//                    ps.setString(3, especialidad.getDescripcionCargo());
//                    ps.setInt(4, especialidad.getAniosExperiencia());
//                    ps.executeUpdate();
//                }
//            } else {
//                // Actualizar especialidad existente
//                String updateQuery = "UPDATE Especializacion SET nombre_de_especializacion = ?, Descripcion_Especializacion = ?, anios_experiencia = ? "
//                                   + "WHERE Doctor_id = ? AND nombre_de_especializacion = ?";
//                try (PreparedStatement ps = con.prepareStatement(updateQuery)) {
//                    ps.setString(1, especialidad.);
//                    ps.setString(2, especialidad.getDescripcionEspecializacion());
//                    ps.setInt(3, especialidad.getAniosExperiencia());
//                    ps.setString(4, especialidad.getDoctorId());
//                    ps.setString(5, especialidad.getNombreEspecializacion()); // Asumiendo que el nombre de la especialización no cambia
//                    ps.executeUpdate();
//                }
//            }
//        }
//        System.out.println("Datos guardados exitosamente");
//    } catch (SQLException e) {
//        e.printStackTrace();
//        showErrorAlert("Error al guardar los datos: " + e.getMessage());
//    }
//}

   

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
