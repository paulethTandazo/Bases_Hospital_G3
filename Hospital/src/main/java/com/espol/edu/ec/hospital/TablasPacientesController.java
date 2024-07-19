package com.espol.edu.ec.hospital;


import Departamento.Departamento;
import com.espol.edu.ec.hospital.ConexionSql;
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
        initialize();
    }

    @FXML
    private VBox TablaDepartamento;

    @FXML
    private void initialize() {
        // Crear el TableView
        TableView<Departamento> tableView = new TableView<>();

        // Crear las columnas
        TableColumn<Departamento, String> colDepartamentoId = new TableColumn<>("ID Departamento");
        colDepartamentoId.setCellValueFactory(new PropertyValueFactory<>("Departamento_id"));

        TableColumn<Departamento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));

        TableColumn<Departamento, String> colLocalizacion = new TableColumn<>("Localizacion");
        colLocalizacion.setCellValueFactory(new PropertyValueFactory<>("locazionDepartamento"));

        // Agregar las columnas al TableView
        tableView.getColumns().add(colDepartamentoId);
        tableView.getColumns().add(colNombre);
        tableView.getColumns().add(colLocalizacion);

        // Llenar la tabla con datos
        tableView.setItems(getDepartamentoData());

        // Agregar el TableView al VBox
        TablaDepartamento.getChildren().add(tableView);
    }

    private ObservableList<Departamento> getDepartamentoData() {
        ObservableList<Departamento> departamentoList = FXCollections.observableArrayList();

        // Conexión a la base de datos y obtención de datos
        try (Connection con = new ConexionSql().estableceConexion()) {
            String consulta = "SELECT d.Departamento_id, d.Nombre_Departamento, d.Localizacion " +
                              "FROM Departamento d " +
                              "JOIN DoctorxDepartamento dx ON d.Departamento_id = dx.Departamento_id " +
                              "WHERE dx.Doctor_id = ?";
            // Asegúrate de pasar el ID del doctor como parámetro
            String doctorId = "ID_DEL_DOCTOR"; // Reemplaza con el ID del doctor real
            PreparedStatement st = con.prepareStatement(consulta);
            st.setString(1, doctorId);
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
