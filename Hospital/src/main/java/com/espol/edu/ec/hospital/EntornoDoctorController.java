package com.espol.edu.ec.hospital;

import Departamento.Departamento;
import Doctor.InformacionDoctor;
import Paciente.InformacionPersonal;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pauleth
 */
public class EntornoDoctorController {

    
    @FXML
    public GridPane infoGrid;
    @FXML
    private VBox NombreDoctor;
    //Parte de la informacion del doctor
    private Label bienvenidaLabel;
    private TextField doctorIdField;
    private TextField nombreField;
    private TextField apellidoField;
    private TextField especializacionField;
    private TextField descripcionField;
    private TextField aniosExperienciaField;
    private InformacionDoctor doctor;
   
   public void setDoctor(int cedula) {
       this.doctor=InformacionDoctor.getDoctorByCedula(cedula);
       System.out.println(doctor);
       System.out.println("Cédula recibida: " + cedula);
        
    }
    public void initialize() {
         Platform.runLater(() -> {
        if (doctor != null) {
            infoGrid.getChildren().clear();         
            NombreDoctor.getChildren().clear();
            cargarDatosDoctor(doctor);
        }
    });
    }
    
   //Inicio bloque especialidades
   private void cargarEspecialidades (){
    Label label1 = new Label("Tus Especialidades");
    VBox Espaciado = new VBox();
    Button Nuevo = new Button();
    Button Editar = new Button();
    Button Guardar = new Button();
    HBox grupoBotones = new HBox();
            TableView<InformacionDoctor> TablaxEspecialidades = new TableView<>();
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
            label1.setStyle("-fx-font-weight: bold;fx-font-size:18px;");
            Espaciado.setSpacing(50);
            TablaxEspecialidades.getColumns().addAll(colCodigoDoctor, colNombre, colEspecialidad, colAnios);
            NombreDoctor.getChildren().addAll(label1, TablaxEspecialidades, Espaciado);
            NombreDoctor.setPadding(new Insets(10, 20, 10, 20));
            NombreDoctor.setSpacing(50);
            System.out.println("realizo cambios");
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
            st.setInt(1, doctor.getCedula()); // Usar la cédula para buscar el Doctor_id
            ResultSet rs = st.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron especialidades para la cédula: " + doctor.getCedula());
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
    private void handleEspecialidad(){
         infoGrid.getChildren().clear();
        NombreDoctor.getChildren().clear();
         cargarEspecialidades ();
         System.out.println("Entro especialidades");
    }
    
    //...Fin bloque especialidades
    //
    //Inicio bloque información del doctor 
    private void cargarDatosDoctor(InformacionDoctor doctor){
        
        if (doctor != null) {
            bienvenidaLabel = new Label("Bienvenid@ de nuevo, " + doctor.getNombre());
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
            NombreDoctor.setStyle("-fx-alignment: CENTER;");
            NombreDoctor.getChildren().addAll(bienvenidaLabel, avatar, infoGrid);
            NombreDoctor.setAlignment(Pos.CENTER);
            System.out.println("Datos del doctor cargados correctamente."); // Debug
        } else {
            System.out.println("Doctor no encontrado."); // Debug
        }
    }
    //Función para volver a mostrar la información cuando se presione el boton
    @FXML
    private void handleVolver() {
        infoGrid.getChildren().clear();
        NombreDoctor.getChildren().clear();
        cargarDatosDoctor(doctor);
    }
    
    //...Fin Bloque doctor
    //
    //Inicio Bloque Mostrar Pacientes
    @FXML
    private void HandlePaciente(){
    infoGrid.getChildren().clear();
        NombreDoctor.getChildren().clear();
         cargarPacientes ();
         System.out.println("Entro especialidades");
    };
   private void cargarPacientes (){
    Label label1 = new Label("Tus Pacientes");
    VBox Espaciado = new VBox();
    Button Nuevo = new Button();
    Button Editar = new Button();
    Button Guardar = new Button();
            TableView<InformacionPersonal> TablaxEspecialidades = new TableView<>();
            // Creando las columnas para llenar luego los datos
            TableColumn<InformacionPersonal, String> colCodigoPaciente = new TableColumn<>("Código-Paciente");
            colCodigoPaciente.setCellValueFactory(new PropertyValueFactory<>("Paciente_id"));
            colCodigoPaciente.setStyle("-fx-alignment: CENTER;");
            TableColumn<InformacionPersonal, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
            colNombre.setStyle("-fx-alignment: CENTER;");
            TableColumn<InformacionPersonal, String> colApellido = new TableColumn<>("Apellido");
            colApellido.setCellValueFactory(new PropertyValueFactory<>("Apellido"));
            colApellido.setStyle("-fx-alignment: CENTER;");
            TableColumn<InformacionPersonal, Integer> colEdad = new TableColumn<>("Edad");
            colEdad .setCellValueFactory(new PropertyValueFactory<>("Edad"));
            colEdad .setStyle("-fx-alignment: CENTER;");
            TableColumn<InformacionPersonal, Integer> colEnfermedad = new TableColumn<>("Enfermedad_a_tratar");
            colEnfermedad.setCellValueFactory(new PropertyValueFactory<>("Enfermedad_a_tratar"));
            colEnfermedad .setStyle("-fx-alignment: CENTER;");
            estiloBoton(Guardar, "Imagenes/disquete.png", "#FFFFFF", "#000000", true);
            label1.setStyle("-fx-font-weight: bold;fx-font-size:18px;");
            Espaciado.setSpacing(50);
            TablaxEspecialidades.getColumns().addAll(colCodigoPaciente, colNombre, colApellido, colEdad ,colEnfermedad);
            NombreDoctor.getChildren().addAll(label1, TablaxEspecialidades, Espaciado);
            NombreDoctor.setPadding(new Insets(10, 20, 10, 20));
            NombreDoctor.setSpacing(50);
            System.out.println("realizo cambios");
        // Llenar el TableView con datos
        TablaxEspecialidades.setItems(getPacientes());
   }
   private ObservableList<InformacionPersonal> getPacientes() {
        ObservableList<InformacionPersonal> pacienteList = FXCollections.observableArrayList();

        // Conexión a la base de datos y obtención de datos
        try (Connection con = new ConexionSql().estableceConexion()) {
            String consulta ="SELECT t.Doctor_id, p.Paciente_id, p.Nombre, p.Apellido, p.Edad, t.Enfermedad_a_tratar " +
                             "FROM Doctor d " +
                             "JOIN Tratamiento t ON t.Doctor_id = d.Doctor_id " +
                             "JOIN Paciente p ON p.Paciente_id = t.Paciente_id " +
                              "WHERE d.Cedula = ? ";

            PreparedStatement st = con.prepareStatement(consulta);
            st.setInt(1, doctor.getCedula()); // Usar la cédula para buscar el Doctor_id
            ResultSet rs = st.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron pacientes para la cédula: " + doctor.getCedula());
            }

            while (rs.next()) {
                String Paciente_id = rs.getString("Paciente_id");
                String Nombre = rs.getString("Nombre");
                String Apellido= rs.getString("Apellido");
                int edad = rs.getInt("Edad");
                String Enfermedad_a_tratar= rs.getString("Enfermedad_a_tratar");
                pacienteList.add(new InformacionPersonal(Paciente_id,Nombre,Apellido,edad,Enfermedad_a_tratar));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacienteList;
    }
   
   //Fin Bloque Mostrar Pacientes
   //
   //Inicio Bloque Mostrar Departamento
   @FXML
    private void HandleDepartamento(){
    infoGrid.getChildren().clear();
        NombreDoctor.getChildren().clear();
         cargarDepartamentos ();
         System.out.println("Entro especialidades");
    };
   private void cargarDepartamentos (){
    Label label1 = new Label("Tus Departamentos");
    VBox Espaciado = new VBox();
    Button Nuevo = new Button();
    Button Editar = new Button();
    Button Guardar = new Button();
            TableView<Departamento> TablaxEspecialidades = new TableView<>();
            // Creando las columnas para llenar luego los datos
            TableColumn<Departamento, String> colCodigoDepartamento = new TableColumn<>("Código-Departamento");
            colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<>("Departamento_id"));
            colCodigoDepartamento.setStyle("-fx-alignment: CENTER;");
            TableColumn<Departamento, String> colNombre = new TableColumn<>("Nombre");
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDepartamento"));
            colNombre.setStyle("-fx-alignment: CENTER;");
            TableColumn<Departamento, String> colLocalizacion = new TableColumn<>("Localizacion");
            colLocalizacion.setCellValueFactory(new PropertyValueFactory<>("locazionDepartamento"));
            colLocalizacion.setStyle("-fx-alignment: CENTER;");         
            label1.setStyle("-fx-font-weight: bold;fx-font-size:18px;");
            Espaciado.setSpacing(50);
            TablaxEspecialidades.getColumns().addAll(colCodigoDepartamento, colNombre, colLocalizacion);
            NombreDoctor.getChildren().addAll(label1, TablaxEspecialidades, Espaciado);
            NombreDoctor.setPadding(new Insets(10, 20, 10, 20));
            NombreDoctor.setSpacing(50);
            System.out.println("realizo cambios");
        // Llenar el TableView con datos
        TablaxEspecialidades.setItems(getDepartamentos());
   }
   private ObservableList<Departamento> getDepartamentos() {
        ObservableList<Departamento> pacienteList = FXCollections.observableArrayList();

        // Conexión a la base de datos y obtención de datos
        try (Connection con = new ConexionSql().estableceConexion()) {
            String consulta ="SELECT d.Doctor_id,dp.Departamento_id, dp.Nombre_Departamento,dp.Localizacion " +
                             "FROM Doctor d " +
                             "JOIN doctorxdepartamento dxp ON d.Doctor_id=dxp.Doctor_id  " +
                             "JOIN departamento dp ON dp.Departamento_id=dxp.Departamento_id " +
                              "WHERE d.Cedula = ? ";

            PreparedStatement st = con.prepareStatement(consulta);
            st.setInt(1, doctor.getCedula()); // Usar la cédula para buscar el Doctor_id
            ResultSet rs = st.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No se encontraron pacientes para la cédula: " + doctor.getCedula());
            }

            while (rs.next()) {
                String Departamento_id = rs.getString("Departamento_id");
                String Nombre_departamento = rs.getString("Nombre_Departamento");
                String Localizacion= rs.getString("Localizacion");
                
                pacienteList.add(new Departamento(Departamento_id,Nombre_departamento,Localizacion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pacienteList;
    }
   //Fin Bloque Mostrar Departamento
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
                psDoctor.setInt(3, doctor.getCedula());
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
