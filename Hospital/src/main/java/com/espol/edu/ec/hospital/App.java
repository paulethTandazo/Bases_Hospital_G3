package com.espol.edu.ec.hospital;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        setRoot("TipoUsuario"); // Carga la vista inicial con los botones de Paciente y Doctor
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        primaryStage.setScene(new Scene(loader.load()));
    }

    public static void main(String[] args) {
        launch();
    }
}
