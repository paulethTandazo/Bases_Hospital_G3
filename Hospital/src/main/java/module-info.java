module com.espol.edu.ec.hospital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.espol.edu.ec.hospital to javafx.fxml;
    exports com.espol.edu.ec.hospital;
}
