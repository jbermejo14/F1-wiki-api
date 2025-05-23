package com.svalero.f1wiki.controllers;

import com.google.gson.Gson;
import com.svalero.f1wiki.ErgastApi;

import com.svalero.f1wiki.domain.Driver;
import com.svalero.f1wiki.response.DriversResponse;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchDriversController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> driversListView;

    private List<Driver> allDrivers = new ArrayList<>();
    private ObservableList<String> displayedDrivers = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Fetch drivers for a season, example: 2021
        fetchDrivers();

        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterDrivers(newVal));

        driversListView.setOnMouseClicked(event -> {
            String selectedName = driversListView.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                Driver selected = allDrivers.stream()
                        .filter(d -> d.getFullName().equals(selectedName))
                        .findFirst()
                        .orElse(null);
                if (selected != null) {
                    openDriverDetail(selected);
                }
            }
        });
    }

    // Fetch drivers from the API
    private void fetchDrivers() {
        ErgastApi api = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ErgastApi.class);

        Observable<DriversResponse> observable = Observable.create(emitter -> {
            Call<DriversResponse> call = api.getDrivers();
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<DriversResponse> call, Response<DriversResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        emitter.onNext(response.body());
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Exception("API response was unsuccessful"));
                    }
                }

                @Override
                public void onFailure(Call<DriversResponse> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        response -> Platform.runLater(() -> {
                            allDrivers = response.getMRData().getDriverTable().getDrivers();
                            if (allDrivers != null && !allDrivers.isEmpty()) {
                                displayedDrivers.setAll(allDrivers.stream()
                                        .map(Driver::getFullName)
                                        .collect(Collectors.toList()));
                                driversListView.setItems(displayedDrivers);
                            } else {
                                System.out.println("No drivers found in the response.");
                            }
                        }),
                        Throwable::printStackTrace
                );
    }



    // Filter drivers based on the search query
    private void filterDrivers(String query) {
        if (query == null || query.isBlank()) {
            displayedDrivers.setAll(allDrivers.stream().map(Driver::getFullName).collect(Collectors.toList()));
        } else {
            displayedDrivers.setAll(
                    allDrivers.stream()
                            .filter(driver -> driver.getFullName().toLowerCase().contains(query.toLowerCase()))
                            .map(Driver::getFullName)
                            .collect(Collectors.toList())
            );
        }
    }

    private void openDriverDetail(Driver driver) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/driver_detail.fxml"));
            Parent root = loader.load();

            DriverDetailController controller = loader.getController();
            controller.setDriver(driver);

            Stage stage = new Stage();
            stage.setTitle("Driver Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
