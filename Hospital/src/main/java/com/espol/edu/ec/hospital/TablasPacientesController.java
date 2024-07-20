package com.espol.edu.ec.hospital;

import Departamento.Departamento;
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

    @FXML
    private void initialize() {
         // Crear el TableView solo una vez
        if (TablaxDepartamentos == null) {
            TablaxDepartamentos = new TableView<>();
        // Creando las columnas para llenar luego los datos
        TableColumn<Departamento, String> colDepartamentoId = new TableColumn<>("ID Departamento");
        colDepartamentoId.setCellValueFactory(new PropertyValueFactory<>("Departamento_id"));

        TableColumn<Departamento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));

        TableColumn<Departamento, String> colLocalizacion = new TableColumn<>("Localizacion");
        colLocalizacion.setCellValueFactory(new PropertyValueFactory<>("locazionDepartamento"));

        TablaxDepartamentos.getColumns().addAll(colDepartamentoId, colNombre, colLocalizacion);
        TablaDepartamento.getChildren().add(TablaxDepartamentos);
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

}
