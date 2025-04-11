package com.svalero.f1wiki;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ErgastApi {
    @GET("f1/{season}/{round}/results.json")
    Call<RaceResultsResponse> getRaceResults(@Path("season") int season, @Path("round") int round);
}