/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Doctor;

import Departamento.Departamento;
import com.espol.edu.ec.hospital.ConexionSql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pauleth
 */
public class InformacionDoctor {

    private String Doctor_id;
    private int cedula;
    private String nombre;
    private String apellido;
    private String especializacion_id;
    private String especializacion;
    private String DescripcionCargo;
    private int aniosExperiencia;
    

    public InformacionDoctor(String Doctor_id, String especializacion_id, String especializacion, String DescripcionCargo, int aniosExperiencia) {
        this.Doctor_id = Doctor_id;
        this.especializacion_id = especializacion_id;
        this.especializacion = especializacion;
        this.DescripcionCargo = DescripcionCargo;
        this.aniosExperiencia = aniosExperiencia;
    }

    public InformacionDoctor(String Doctor_id, String especializacion, String DescripcionCargo, int aniosExperiencia) {
        this.Doctor_id = Doctor_id;
        this.especializacion = especializacion;
        this.DescripcionCargo = DescripcionCargo;
        this.aniosExperiencia = aniosExperiencia;
    }
    

    public InformacionDoctor(String Doctor_id, int cedula, String nombre, String apellido, String especializacion, String DescripcionCargo, int aniosExperiencia) {
        this.Doctor_id = Doctor_id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especializacion = especializacion;
        this.DescripcionCargo = DescripcionCargo;
        this.aniosExperiencia = aniosExperiencia;
    }

    public String getDoctor_id() {
        return Doctor_id;
    }

    public String getEspecializacion_id() {
        return especializacion_id;
    }

    public void setEspecializacion_id(String especializacion_id) {
        this.especializacion_id = especializacion_id;
    }

    public void setDoctor_id(String Doctor_id) {
        this.Doctor_id = Doctor_id;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    public String getDescripcionCargo() {
        return DescripcionCargo;
    }

    public void setDescripcionCargo(String DescripcionCargo) {
        this.DescripcionCargo = DescripcionCargo;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }
// Método para obtener información del doctor por su cédula

    public static InformacionDoctor getDoctorByCedula(int cedula) {
        InformacionDoctor doctor = null;
        try (Connection con = new ConexionSql().estableceConexion()) {
            String consulta = "SELECT d.Doctor_id, d.Cedula, d.Nombre, d.Apellido, e.nombre_de_especializacion AS Especializacion, e.Descripcion_Especializacion, e.anios_experiencia "
                    + "FROM Doctor d "
                    + "JOIN Especializacion e ON d.Doctor_id = e.Doctor_id "
                    + "WHERE d.Cedula = ?";
            CallableStatement st = con.prepareCall(consulta);
            st.setInt(1, cedula);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                String doctorId = rs.getString("Doctor_id");
                int cedulaDb = rs.getInt("Cedula");
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                String especializacion = rs.getString("Especializacion");
                String descripcionCargo = rs.getString("Descripcion_Especializacion");
                int aniosExperiencia = rs.getInt("anios_experiencia");

                doctor = new InformacionDoctor(doctorId, cedulaDb, nombre, apellido, especializacion, descripcionCargo, aniosExperiencia);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctor;
    }
   private ObservableList<Departamento> getDepartamentoData(int cedula) {
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

}
