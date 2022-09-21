module com.example.bdtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bdtest to javafx.fxml;
    exports com.example.bdtest;
}