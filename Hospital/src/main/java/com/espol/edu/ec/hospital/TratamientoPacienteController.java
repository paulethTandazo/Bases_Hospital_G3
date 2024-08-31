/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

   private TableView<Tratamiento> TablaxTratamientos;

  
     public void setCedula(int cedula) {
        this.cedula = cedula;
        System.out.println("Cédula recibida dentro del controlador especialidades: " + cedula); // Debug
        loadTratamientos();  // Carga los datos una vez que la cédula ha sido establecida
    }

   @FXML
    private void initialize() {
        if (TablaxTratamientos == null) {
        TablaxTratamientos = new TableView<>();
        
        // Configuración de las columnas
        TableColumn<Tratamiento, String> colCodigoTratamiento = new TableColumn<>("Código Tratamiento");
        colCodigoTratamiento.setCellValueFactory(new PropertyValueFactory<>("codigoTratamiento"));
        colCodigoTratamiento.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Tratamiento, String> colEnfermedad = new TableColumn<>("Enfermedad");
        colEnfermedad.setCellValueFactory(new PropertyValueFactory<>("enfermedad"));
        colEnfermedad.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Tratamiento, String> colNombreDoctor = new TableColumn<>("Nombre Doctor");
        colNombreDoctor.setCellValueFactory(new PropertyValueFactory<>("nombreDoctor"));
        colNombreDoctor.setStyle("-fx-alignment: CENTER;");
        
        TableColumn<Tratamiento, String> colApellidoDoctor = new TableColumn<>("Apellido Doctor");
        colApellidoDoctor.setCellValueFactory(new PropertyValueFactory<>("apellidoDoctor"));
        colApellidoDoctor.setStyle("-fx-alignment: CENTER;");
        
        // Nueva columna para el departamento
        TableColumn<Tratamiento, String> colNombreDepartamento = new TableColumn<>("Departamento");
        colNombreDepartamento.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));
        colNombreDepartamento.setStyle("-fx-alignment: CENTER;");
        
        // Agregar columnas al TableView
        TablaxTratamientos.getColumns().addAll(colCodigoTratamiento, colEnfermedad, colNombreDoctor, colApellidoDoctor, colNombreDepartamento);
        
        // Añadir el TableView al VBox
        TratamientoPacienteVbox.getChildren().add(TablaxTratamientos);
    }
    }
private void loadTratamientos() {
        // Llenar la tabla con los datos
        TablaxTratamientos.setItems(getTratamientos(this.cedula));
    }

    private ObservableList<Tratamiento> getTratamientos(int c) {
        ObservableList<Tratamiento> tratamientoList = FXCollections.observableArrayList();

        // Conexión a la base de datos y obtención de datos
        try (Connection con = new ConexionSql().estableceConexion()) {
            String consulta = "SELECT t.Tratamiento_id, t.Enfermedad_a_tratar, d.Nombre AS nombreDoctor, d.Apellido AS apellidoDoctor, dp.Nombre_Departamento AS nombreDepartamento "
                            + "FROM Tratamiento t "
                            + "JOIN Paciente p ON t.Paciente_id = p.Paciente_id "
                            + "JOIN Doctor d ON t.Doctor_id = d.Doctor_id "
                            + "JOIN DoctorxDepartamento dd ON d.Doctor_id = dd.Doctor_id "
                            + "JOIN Departamento dp ON dd.Departamento_id = dp.Departamento_id "
                            + "JOIN (SELECT Doctor_id, MIN(Departamento_id) AS Departamento_id FROM DoctorxDepartamento GROUP BY Doctor_id) dd_min "
                            + "ON dd.Doctor_id = dd_min.Doctor_id AND dd.Departamento_id = dd_min.Departamento_id "
                            + "WHERE p.Cedula = ?";

            PreparedStatement st = con.prepareStatement(consulta);
            st.setInt(1, c); // Usar la cédula para buscar el Paciente_id
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String codigoTratamiento = rs.getString("Tratamiento_id");
                String enfermedad = rs.getString("Enfermedad_a_tratar");
                String nombreDoctor = rs.getString("nombreDoctor");
                String apellidoDoctor = rs.getString("apellidoDoctor");
                String nombreDepartamento = rs.getString("nombreDepartamento");

                // Agrega el tratamiento completo a la lista
                tratamientoList.add(new Tratamiento(codigoTratamiento, enfermedad, nombreDoctor, apellidoDoctor, nombreDepartamento));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tratamientoList;
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

    @FXML
    private void handleMedicina(ActionEvent event) {
                    try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Medicina.fxml"));
            Parent root = loader.load();

            MedicinaController controller = loader.getController();
            controller.setCedula(this.cedula);
             Scene scene = new Scene(root, 660, 520);  
            Stage stage = (Stage) TratamientoVbox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de información del paciente: " + e.getMessage());
        }
        
    }
}
