package com.svalero.f1wiki;
import com.google.gson.Gson;
import com.svalero.f1wiki.domain.Race;
import com.svalero.f1wiki.domain.Result;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RaceResultsApp extends Application {
    private ListView<String> resultsListView;
    private TextField searchField;
    private ObservableList<String> resultsObservableList;

    @Override
    public void start(Stage primaryStage) throws IOException {
        resultsListView = new ListView<>();
        searchField = new TextField();
        searchField.setPromptText("Buscar resultados...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterResults(newValue));
        VBox vbox = new VBox(searchField, resultsListView);
        Scene scene = new Scene(vbox, 400, 300);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/f1_view.fxml")));
        primaryStage.setTitle("Resultados de Carreras F1");
        primaryStage.setScene(new Scene (root, 400, 300));
        primaryStage.show();

        fetchRaceResults(2008, 6);
    }


    @FXML
    private void onSearchDrivers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("driver_detail.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Search Drivers");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchRaceResults(int season, int round) {
        ErgastApi api = new Retrofit.Builder()
                .baseUrl("https://ergast.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ErgastApi.class);

        Observable<RaceResultsResponse> observable = Observable.create(emitter -> {
            Call<RaceResultsResponse> call = api.getRaceResults(season, round);
            call.enqueue(new Callback<RaceResultsResponse>() {
                @Override
                public void onResponse(Call<RaceResultsResponse> call, Response<RaceResultsResponse> response) {
                    if (response.isSuccessful()) {
                        RaceResultsResponse body = response.body();
                        System.out.println(new Gson().toJson(response.body()));
                        if (body != null && body.getMRData() != null && body.getMRData().getRaceTable() != null) {
                            emitter.onNext(body);
                            emitter.onComplete();
                        } else {
                            System.err.println("MRData o RaceTable es null");
                            emitter.onError(new NullPointerException("MRData o RaceTable es null"));
                        }
                    } else {
                        emitter.onError(new Exception("Error en la respuesta: " + response.code()));
                    }
                }

                @Override
                public void onFailure(Call<RaceResultsResponse> call, Throwable t) {
                    emitter.onError(t);
                }
            });
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline())
                .subscribe(this::updateUI, Throwable::printStackTrace);
    }

    @FXML
    private void onSearchConstructors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("constructor_detail.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Search Constructors");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateUI(RaceResultsResponse response) {
        if (response == null || response.getMRData() == null || response.getMRData().getRaceTable() == null) {
            System.err.println("updateUI: response o sus datos internos son null");
            return;
        }
        List<String> results = new ArrayList<>();
        for (Race race : response.getMRData().getRaceTable().getRaces()) {
            for (Result result : race.getResults()) {
                results.add(race.getRaceName() + " - " + result.getDriver().getFullName() + " - Posición: " + result.getPosition());
            }
        }
        resultsObservableList = FXCollections.observableArrayList(results);
        resultsListView.setItems(resultsObservableList);
    }

    private void filterResults(String query) {
        ObservableList<String> filteredResults = FXCollections.observableArrayList();
        for (String result : resultsObservableList) {
            if (result.toLowerCase().contains(query.toLowerCase())) {
                filteredResults.add(result);
            }
        }
        resultsListView.setItems(filteredResults);
    }

    public static void main(String[] args) {
        launch(args);
    }
}