package com.svalero.f1wiki;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class RaceResultsController {

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<Integer> seasonComboBox;

    @FXML
    private ComboBox<Integer> roundComboBox;

    @FXML
    private ListView<String> resultsListView;

    private ObservableList<String> resultsObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        resultsListView.setItems(resultsObservableList);
        List<Integer> seasons = new ArrayList<>();
        for (int year = 2000; year <= 2024; year++) {
            seasons.add(year);
        }

        List<Integer> rounds = new ArrayList<>();
        for (int r = 1; r <= 22; r++) {
            rounds.add(r);
        }

        seasonComboBox.getItems().addAll(seasons);
        roundComboBox.getItems().addAll(rounds);

        seasonComboBox.setValue(2023);
        roundComboBox.setValue(1);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterResults(newVal));

    }

    @FXML
    public void fetchRaceResults(int season, int round) {
        ErgastApi api = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ErgastApi.class);

        Observable<RaceResultsResponse> observable = Observable.create(emitter -> {
            Call<RaceResultsResponse> call = api.getRaceResults(season, round);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<RaceResultsResponse> call, Response<RaceResultsResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        emitter.onNext(response.body());
                        emitter.onComplete();
                    } else {
                        emitter.onError(new Exception("API response was unsuccessful"));
                    }
                }

                @Override
                public void onFailure(Call<RaceResultsResponse> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        response -> Platform.runLater(() -> updateUI(response)),
                        Throwable::printStackTrace
                );
    }

    @FXML
    public void onFetchResults(ActionEvent event) {
        Integer season = seasonComboBox.getValue();
        Integer round = roundComboBox.getValue();

        if (season != null && round != null) {
            fetchRaceResults(season, round);
        }
    }

    private void updateUI(RaceResultsResponse response) {
        List<String> results = new ArrayList<>();
        response.getMRData().getRaceTable().getRaces().forEach(race ->
                race.getResults().forEach(result ->
                        results.add(race.getRaceName() + " - " +
                                result.getDriver().getFullName() +
                                " - Position: " + result.getPosition())
                )
        );

        resultsObservableList.setAll(results);
    }

    private void filterResults(String query) {
        if (query == null || query.isBlank()) {
            resultsListView.setItems(resultsObservableList);
            return;
        }

        ObservableList<String> filtered = FXCollections.observableArrayList();
        for (String result : resultsObservableList) {
            if (result.toLowerCase().contains(query.toLowerCase())) {
                filtered.add(result);
            }
        }
        resultsListView.setItems(filtered);
    }
}
