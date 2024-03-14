module com.example.last2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.last2 to javafx.fxml;
    exports com.example.last2;
}