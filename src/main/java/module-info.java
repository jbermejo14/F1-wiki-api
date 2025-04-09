module com.svalero.f1wiki {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.svalero.f1wiki to javafx.fxml;
    exports com.svalero.f1wiki;
}