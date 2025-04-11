module com.svalero.f1wiki {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires okio;
    requires io.reactivex.rxjava3;
    requires com.google.gson;

    opens com.svalero.f1wiki to javafx.fxml, retrofit2.converter.gson, com.google.gson;

    exports com.svalero.f1wiki;
}