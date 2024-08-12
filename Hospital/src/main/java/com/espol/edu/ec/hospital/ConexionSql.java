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
            // No es un método seguro porque queda muy expuesta nuestra contraseña de la base de datos
            //pero es una de las formas para acceder a nuestra base de datos
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Grupo3_Hospital", "root", "admin");
            //comenten la linea 24 y pongan sus credencials de 
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Base_Hospital", "1709cmc", "1709cmc_MM");
            System.out.println("Se conecto a la base de datos");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return con;
    }
}
