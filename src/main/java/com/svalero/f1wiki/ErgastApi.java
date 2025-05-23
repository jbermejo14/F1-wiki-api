package com.svalero.f1wiki;

import com.svalero.f1wiki.response.DriversResponse;
import com.svalero.f1wiki.response.ConstructorsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ErgastApi {
    @GET("f1/{season}/{round}/results.json")
    Call<RaceResultsResponse> getRaceResults(@Path("season") int season, @Path("round") int round);

    @GET("f1/drivers.json")
    Call<DriversResponse> getDrivers(@Query("limit") int limit, @Query("offset") int offset);

    @GET("f1/constructors.json")
    Call<ConstructorsResponse> getConstructors();
}