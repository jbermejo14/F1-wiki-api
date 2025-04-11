package com.svalero.f1wiki;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
        fetchDrivers(2021);

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
    private void fetchDrivers(int season) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ErgastApi api = retrofit.create(ErgastApi.class);
        Call<DriversResponse> call = api.getDrivers(season);

        call.enqueue(new Callback<DriversResponse>() {
            @Override
            public void onResponse(Call<DriversResponse> call, Response<DriversResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allDrivers = response.body().getMRData().getDriverTable().getDrivers();
                    displayedDrivers.setAll(allDrivers.stream().map(Driver::getFullName).collect(Collectors.toList()));
                    driversListView.setItems(displayedDrivers);
                }
            }

            @Override
            public void onFailure(Call<DriversResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
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

    // Open the driver detail view
    private void openDriverDetail(Driver driver) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/svalero/f1wiki/detail-view.fxml"));
            Parent root = loader.load();

            DetailViewController controller = loader.getController();
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
