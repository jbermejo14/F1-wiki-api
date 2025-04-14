package com.svalero.f1wiki;

import com.svalero.f1wiki.response.DriversResponse;
import com.svalero.f1wiki.response.ConstructorsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ErgastApi {
    @GET("f1/{season}/{round}/results.json")
    Call<RaceResultsResponse> getRaceResults(@Path("season") int season, @Path("round") int round);

    @GET("f1/drivers.json")
    Call<DriversResponse> getDrivers();

    @GET("f1/constructors.json")
    Call<ConstructorsResponse> getConstructors(@Path("season") int season);
}