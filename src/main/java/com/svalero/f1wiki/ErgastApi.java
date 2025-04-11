package com.svalero.f1wiki;

import com.svalero.f1wiki.response.DriversResponse;
import com.svalero.f1wiki.response.ConstructorsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ErgastApi {
    @GET("f1/{season}/{round}/results.json")
    Call<RaceResultsResponse> getRaceResults(@Path("season") int season, @Path("round") int round);

    @GET("f1/{season}/drivers.json")
    Call<DriversResponse> getDrivers(@Path("season") int season);

    @GET("f1/{season}/constructors.json")
    Call<ConstructorsResponse> getConstructors(@Path("season") int season);
}