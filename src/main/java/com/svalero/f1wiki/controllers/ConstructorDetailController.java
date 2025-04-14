package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.domain.Constructor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConstructorDetailController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label nationalityLabel;

    public void setConstructor(Constructor constructor) {
        nameLabel.setText(constructor.getName());
        nationalityLabel.setText("Nationality: " + constructor.getNationality());
    }

}
