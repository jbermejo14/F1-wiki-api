package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.F1OpenApi;
import com.svalero.f1wiki.domain.Driver;
import com.svalero.f1wiki.domain.OpenF1Driver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

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
    @FXML
    private ImageView headshotImage;

    public void setDriver(Driver driver) {
        nameLabel.setText(driver.getFullName());
        dobLabel.setText("Date of Birth: " + driver.getDateOfBirth());
        nationalityLabel.setText("Nationality: " + driver.getNationality());
        numberLabel.setText("Number: " + driver.getPermanentNumber());
        codeLabel.setText("Code: " + driver.getCode());

        fetchHeadshot(driver.getPermanentNumber());
    }

    private void fetchHeadshot(String driverNumber) {
        if (driverNumber == null || driverNumber.isEmpty()) {
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openf1.org/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        F1OpenApi api = retrofit.create(F1OpenApi.class);
        Call<List<OpenF1Driver>> call = api.getDriverByNumber(driverNumber, "latest");

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<OpenF1Driver>> call, Response<List<OpenF1Driver>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String headshotUrl = response.body().get(0).getHeadshotUrl();
                    System.out.println("Headshot URL: " + headshotUrl); // Debug log
                    if (headshotUrl != null && !headshotUrl.isEmpty()) {
                        Platform.runLater(() -> {
                            Image image = new Image(headshotUrl, true);
                            headshotImage.setImage(image);
                        });
                    }
                } else {
                    System.out.println("Failed to load headshot: Empty or unsuccessful response.");
                }
            }

            @Override
            public void onFailure(Call<List<OpenF1Driver>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
