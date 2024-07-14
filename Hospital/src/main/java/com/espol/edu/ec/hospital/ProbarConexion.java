/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.espol.edu.ec.hospital;

import java.sql.Connection;

/**
 *
 * @author Pauleth
 */
public class ProbarConexion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ConexionSql probandoConexion= new ConexionSql();
        Connection con=probandoConexion.estableceConexion();
        if (con!=null){
            System.out.println("Conexion existosa");
        }else{
            System.out.println("Fallo de conexion");
        }
        
    }
    
}
