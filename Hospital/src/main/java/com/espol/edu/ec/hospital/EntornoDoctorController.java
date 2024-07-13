/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.edu.ec.hospital;

import javafx.application.Platform;
import javafx.fxml.FXML;


/**
 * FXML Controller class
 *
 * @author dell
 */
public class EntornoDoctorController  {

  @FXML
    private void handleSalir() {
        Platform.exit();
    }
}
