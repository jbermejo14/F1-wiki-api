package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.domain.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class DriverDetailController {

    @FXML
    private Label nameLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label nationalityLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label codeLabel;

    public void setDriver(Driver driver) {
        nameLabel.setText(driver.getFullName());
        dobLabel.setText("Date of Birth: " + driver.getDateOfBirth());
        nationalityLabel.setText("Nationality: " + driver.getNationality());
        numberLabel.setText("Number: " + driver.getPermanentNumber());
        codeLabel.setText("Code: " + driver.getCode());
    }
}
