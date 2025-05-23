package com.svalero.f1wiki.controllers;

import com.svalero.f1wiki.ErgastApi;
import com.svalero.f1wiki.domain.Constructor;

import com.svalero.f1wiki.response.ConstructorsResponse;

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

public class SearchConstructorController {

    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> constructorsListView;

    private List<Constructor> allConstructors = new ArrayList<>();
    private ObservableList<String> displayedConstructors = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        fetchConstructors();

        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterConstructors(newVal));

        constructorsListView.setOnMouseClicked(event -> {
            String selectedName = constructorsListView.getSelectionModel().getSelectedItem();
            if (selectedName != null) {
                Constructor selected = allConstructors.stream()
                        .filter(d -> d.getName().equals(selectedName))
                        .findFirst()
                        .orElse(null);
                if (selected != null) {
                    openConstructorDetail(selected);
                }
            }
        });
    }

    // Fetch drivers from the API
    private void fetchConstructors() {
        ErgastApi api = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ErgastApi.class);

        Observable<ConstructorsResponse> observable = Observable.create(emitter -> {
            Call<ConstructorsResponse> call = api.getConstructors();
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<ConstructorsResponse> call, Response<ConstructorsResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        emitter.onNext(response.body());
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Exception("API response was unsuccessful"));
                    }
                }

                @Override
                public void onFailure(Call<ConstructorsResponse> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        response -> Platform.runLater(() -> {
                            allConstructors = response.getMRData().getConstructorTable().getConstructors();
                            if (allConstructors != null && !allConstructors.isEmpty()) {
                                displayedConstructors.setAll(allConstructors.stream()
                                        .map(Constructor::getName)
                                        .collect(Collectors.toList()));
                                constructorsListView.setItems(displayedConstructors);
                            } else {
                                System.out.println("No constructors found in the response.");
                            }
                        }),
                        Throwable::printStackTrace
                );
    }

    private void filterConstructors(String query) {
        if (query == null || query.isBlank()) {
            displayedConstructors.setAll(allConstructors.stream().map(Constructor::getName).collect(Collectors.toList()));
        } else {
            displayedConstructors.setAll(
                    allConstructors.stream()
                            .filter(constructors -> constructors.getName().toLowerCase().contains(query.toLowerCase()))
                            .map(Constructor::getName)
                            .collect(Collectors.toList())
            );
        }
    }

    private void openConstructorDetail(Constructor constructor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/constructor_detail.fxml"));
            Parent root = loader.load();

            ConstructorDetailController controller = loader.getController();
            controller.setConstructor(constructor);

            Stage stage = new Stage();
            stage.setTitle("Constructor Details");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


