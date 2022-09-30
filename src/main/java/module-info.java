module com.example.carch2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.carch2 to javafx.fxml;
    exports com.example.carch2;
}