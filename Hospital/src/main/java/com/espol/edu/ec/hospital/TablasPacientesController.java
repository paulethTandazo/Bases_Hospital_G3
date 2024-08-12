package com.espol.edu.ec.hospital;

import Departamento.Departamento;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TablasPacientesController {

    private int cedula;

    public void setCedula(int cedula) {
        this.cedula = cedula;
        System.out.println("Cédula recibida: " + cedula); // Debug
        cargarDatosDepartamento();
    }

    @FXML
    private VBox TablaDepartamento;
    private TableView<Departamento> TablaxDepartamentos;
    private final Label label = new Label("Tus Departamentos");
    private final VBox Espaciado= new VBox();

    @FXML
    private void initialize() {

        // Crear el TableView solo una vez
        if (TablaxDepartamentos == null) {
            TablaxDepartamentos = new TableView<>();
            // Creando las columnas para llenar luego los datos
            TableColumn<Departamento, String> colDepartamentoId = new TableColumn<>("Código");
            colDepartamentoId.setCellValueFactory(new PropertyValueFactory<>("Departamento_id"));
            colDepartamentoId.setStyle("-fx-alignment: CENTER;");
            TableColumn<Departamento, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));
            colNombre.setStyle("-fx-alignment: CENTER;");
            TableColumn<Departamento, String> colLocalizacion = new TableColumn<>("Localizacion");
            colLocalizacion.setCellValueFactory(new PropertyValueFactory<>("locazionDepartamento"));
            colLocalizacion.setStyle("-fx-alignment: CENTER;");
            label.setStyle("-fx-font-weight: bold;fx-font-size:18px;");
            Espaciado.setSpacing(50);
            TablaxDepartamentos.getColumns().addAll(colDepartamentoId, colNombre, colLocalizacion);
            TablaDepartamento.getChildren().addAll(label, TablaxDepartamentos,Espaciado);
            TablaDepartamento.setPadding(new Insets(10,20,10,20));
            TablaDepartamento.setSpacing(50);
        }
    }
//metodo para que no duplique info 

    private void cargarDatosDepartamento() {
        if (TablaxDepartamentos == null) {
            return;
        }

        // Limpiar los datos
        TablaxDepartamentos.getItems().clear();
        TablaxDepartamentos.setItems(getDepartamentoData());
    }

    private ObservableList<Departamento> getDepartamentoData() {
        ObservableList<Departamento> departamentoList = FXCollections.observableArrayList();

        // Conexión a la base de datos y obtención de datos
        try (Connection con = new ConexionSql().estableceConexion()) {
            String consulta = "SELECT d.Departamento_id, d.Nombre_Departamento, d.Localizacion "
                    + "FROM Departamento d "
                    + "JOIN DoctorxDepartamento dx ON d.Departamento_id = dx.Departamento_id "
                    + "JOIN Doctor doc ON dx.Doctor_id = doc.Doctor_id "
                    + "WHERE doc.Cedula = ?";
            PreparedStatement st = con.prepareStatement(consulta);
            st.setInt(1, this.cedula);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String departamentoId = rs.getString("Departamento_id");
                String nombreDepartamento = rs.getString("Nombre_Departamento");
                String localizacion = rs.getString("Localizacion");

                departamentoList.add(new Departamento(departamentoId, nombreDepartamento, localizacion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departamentoList;
    }

    @FXML
    private void handleSalir() {
        Platform.exit();
    }

    @FXML
    private void handleInformacion() {
        try {
            FXMLLoader pantallaDoctor = new FXMLLoader(getClass().getResource("EntornoDoctor.fxml"));
            Parent root = pantallaDoctor.load();
            EntornoDoctorController controller = pantallaDoctor.getController();
            controller.setCedula(this.cedula);

            Stage stage = (Stage) TablaDepartamento.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de tratamiento: " + e.getMessage());
        }
    }

    @FXML
    private void handlePaciente() {
        try {
            FXMLLoader pantallaDoctor = new FXMLLoader(getClass().getResource("TratamientoPacientes.fxml"));
            Parent root = pantallaDoctor.load();
            TratamientoPacientesController controller = pantallaDoctor.getController();
            controller.setCedula(this.cedula);

            Stage stage = (Stage) TablaDepartamento.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de tratamiento: " + e.getMessage());
        }
    }
   @FXML
    private void handleEspecialidad(){
         try {
            FXMLLoader pantallaEspecialidad = new FXMLLoader(getClass().getResource("Especialidades.fxml"));
            Parent root = pantallaEspecialidad.load();
            EspecialidadesController controller = pantallaEspecialidad.getController();
            controller.setCedula(this.cedula);

            Stage stage = (Stage) TablaDepartamento.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showErrorAlert("Error al cargar la vista de Especialidad: " + e.getMessage());
        }
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
