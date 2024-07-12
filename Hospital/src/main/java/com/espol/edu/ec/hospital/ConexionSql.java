/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.edu.ec.hospital;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Pauleth
 */
public class ConexionSql {

    Connection con;

    public Connection estableceConexion() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_Hospital", "root", "admin");
            System.out.println("Se conecto a la base de datos");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return con;
    }
}
