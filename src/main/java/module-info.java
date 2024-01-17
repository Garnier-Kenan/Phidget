module com.example.table_de_tri_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires phidget22;
    requires java.sql;


    opens com.table_de_tri_fx to javafx.fxml;
    exports com.table_de_tri_fx;
}