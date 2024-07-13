/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paciente;

import com.espol.edu.ec.hospital.ConexionSql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author dell
 */
public class InformacionPersonal {
    private String Paciente_id;
    private int Cedula;
    private String contrasenia;
    private String Nombre;
    private String Apellido;
    private int edad;
    private String  Fcumpleanos;
    private String direccion;

    public InformacionPersonal(String Paciente_id, int Cedula, String contrasenia, String Nombre, String Apellido, int edad, String  Fcumpleanos, String direccion) {
        this.Paciente_id = Paciente_id;
        this.Cedula = Cedula;
        this.contrasenia = contrasenia;
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.edad = edad;
        this.Fcumpleanos = Fcumpleanos;
        this.direccion = direccion;
    }


    public int getCedula() {
        return Cedula;
    }

    public void setCedula(int Cedula) {
        this.Cedula = Cedula;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPaciente_id() {
        return Paciente_id;
    }

    public void setPaciente_id(String Paciente_id) {
        this.Paciente_id = Paciente_id;
    }

    public String getFcumpleanos() {
        return Fcumpleanos;
    }

    public void setFcumpleanos(String Fcumpleanos) {
        this.Fcumpleanos = Fcumpleanos;
    }

   
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    public static InformacionPersonal getPacienteByCedula(int cedula) {
    InformacionPersonal paciente = null;

    try (Connection con = new ConexionSql().estableceConexion()) {
        String consulta = "SELECT Paciente_id, Cedula, Contrasenia, Nombre, Apellido, Edad, Fcumpleanos, Direccion FROM Paciente WHERE Cedula = ?";
        CallableStatement st = con.prepareCall(consulta);
        st.setInt(1, cedula);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            String pacienteId = rs.getString("Paciente_id");
            int cedulaDb = rs.getInt("Cedula");
            String contrasenia = rs.getString("Contrasenia");
            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellido");
            int edad = rs.getInt("Edad");
            String fcumpleanos = rs.getString("Fcumpleanos");
            String direccion = rs.getString("Direccion");

            paciente = new InformacionPersonal(pacienteId, cedulaDb, contrasenia, nombre, apellido, edad, fcumpleanos, direccion);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return paciente;
}

}
