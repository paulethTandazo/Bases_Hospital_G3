/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Doctor;

import com.espol.edu.ec.hospital.ConexionSql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pauleth
 */
public class InformacionDoctor {

    private String Doctor_id;
    private int cedula;
    private String nombre;
    private String apellido;
    private String especializacion;
    private String DescripcionCargo;
    private int aniosExperiencia;

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
        String consulta = "SELECT d.Doctor_id, d.Cedula, d.Nombre, d.Apellido, e.nombre AS Especializacion, e.Descripcion, ex.Years_exp " +
                          "FROM Doctor d " +
                          "JOIN Experiencia ex ON d.Doctor_id = ex.Doctor_ID " +
                          "JOIN Especializacion e ON ex.Spec_ID = e.Especializacion_id " +
                          "WHERE d.Cedula = ?";
        CallableStatement st = con.prepareCall(consulta);
        st.setInt(1, cedula);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            String doctorId = rs.getString("Doctor_id");
            int cedulaDb = rs.getInt("Cedula");
            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellido");
            String especializacion = rs.getString("Especializacion");
            String descripcionCargo = rs.getString("Descripcion");
            int aniosExperiencia = rs.getInt("Years_exp");

            doctor = new InformacionDoctor(doctorId, cedulaDb, nombre, apellido, especializacion, descripcionCargo, aniosExperiencia);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return doctor;
}

}
