module com.example.table_de_tri_fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.table_de_tri_fx to javafx.fxml;
    exports com.example.table_de_tri_fx;
}