module com.espol.edu.ec.hospital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.espol.edu.ec.hospital to javafx.fxml;
        opens Departamento to javafx.base;
        opens Doctor to javafx.base;
        opens Paciente to javafx.base;
    exports com.espol.edu.ec.hospital;
}
