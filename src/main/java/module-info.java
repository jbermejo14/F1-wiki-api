module com.svalero.f1wiki {
    requires javafx.controls;
    requires javafx.fxml;
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires okio;
    requires io.reactivex.rxjava3;
    requires com.google.gson;

    opens com.svalero.f1wiki.response to com.google.gson;
    opens com.svalero.f1wiki to javafx.fxml, retrofit2.converter.gson, com.google.gson;

    exports com.svalero.f1wiki;
    exports com.svalero.f1wiki.domain;
    exports com.svalero.f1wiki.table;
    exports com.svalero.f1wiki.controllers;
    exports com.svalero.f1wiki.response;
    opens com.svalero.f1wiki.controllers to javafx.fxml;
    opens com.svalero.f1wiki.table to com.google.gson, javafx.fxml, retrofit2.fconverter.gson;
    opens com.svalero.f1wiki.domain to com.google.gson, javafx.fxml, retrofit2.converter.gson;
}