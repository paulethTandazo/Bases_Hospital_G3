/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
 * @author leoza
 */
public class MedicinaController implements Initializable {

    @FXML
    private VBox TratamientoVbox;
    @FXML
    private VBox TratamientoPacienteVbox;
    
        private int cedula;

  private TableView<Medicinas> TablaxMedicinas;
  
   public void setCedula(int cedula) {
        this.cedula = cedula;
        System.out.println("Cédula recibida dentro del controlador especialidades: " + cedula); // Debug
        loadMedicinas();  // Carga los datos una vez que la cédula ha sido establecida
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         if (TablaxMedicinas == null) {
            TablaxMedicinas = new TableView<>();
            
            // Configuración de las columnas
            TableColumn<Medicinas, String> colCodigoTratamiento = new TableColumn<>("Código Tratamiento");
            colCodigoTratamiento.setCellValueFactory(new PropertyValueFactory<>("codigoTratamiento"));
            colCodigoTratamiento.setStyle("-fx-alignment: CENTER;");
            
            TableColumn<Medicinas, String> colNombreMedicamento = new TableColumn<>("Medicamento");
            colNombreMedicamento.setCellValueFactory(new PropertyValueFactory<>("nombreMedicamento"));
            colNombreMedicamento.setStyle("-fx-alignment: CENTER;");
            
            TableColumn<Medicinas, String> colNombreDoctor = new TableColumn<>("Doctor");
            colNombreDoctor.setCellValueFactory(new PropertyValueFactory<>("nombreDoctor"));
            colNombreDoctor.setStyle("-fx-alignment: CENTER;");
            
            // Agregar columnas al TableView
            TablaxMedicinas.getColumns().addAll(colCodigoTratamiento, colNombreMedicamento, colNombreDoctor);
            
            // Añadir el TableView al VBox
            TratamientoPacienteVbox.getChildren().add(TablaxMedicinas);
        }
    }    
    
        private void loadMedicinas() {
        // Llenar la tabla con los datos
        TablaxMedicinas.setItems(getMedicinas(this.cedula));
    }

    @FXML
    private void handleVolver(ActionEvent event) {
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
    
     private ObservableList<Medicinas> getMedicinas(int c) {
        ObservableList<Medicinas> medicinasList = FXCollections.observableArrayList();

        // Conexión a la base de datos y obtención de datos
        try (Connection con = new ConexionSql().estableceConexion()) {
            String consulta = "SELECT t.Tratamiento_id, m.Nombre AS nombreMedicamento, d.Nombre AS nombreDoctor "
                            + "FROM Tratamiento t "
                            + "JOIN TratamientoXMedicamento txm ON t.Tratamiento_id = txm.Tratamiento_id "
                            + "JOIN Medicamento m ON txm.Medicamento_id = m.Medicamento_id "
                            + "JOIN Doctor d ON t.Doctor_id = d.Doctor_id "
                            + "JOIN Paciente p ON t.Paciente_id = p.Paciente_id "
                            + "WHERE p.Cedula = ?";
            
            PreparedStatement st = con.prepareStatement(consulta);
            st.setInt(1, c); // Usar la cédula para buscar el Paciente_id
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String codigoTratamiento = rs.getString("Tratamiento_id");
                String nombreMedicamento = rs.getString("nombreMedicamento");
                String nombreDoctor = rs.getString("nombreDoctor");

                // Agrega el registro completo a la lista
                medicinasList.add(new Medicinas(codigoTratamiento, nombreMedicamento, nombreDoctor));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicinasList;
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        
        Platform.exit();
    }
    
    
        private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleTratement(ActionEvent event) {
                try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TratamientoPaciente.fxml"));
            Parent root = loader.load();

            TratamientoPacienteController controller = loader.getController();
            controller.setCedula(this.cedula);
            
            Scene scene = new Scene(root, 660, 520);  

            Stage stage = (Stage) TratamientoVbox.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de tratamiento: " + e.getMessage());
        }
    }
    
}
