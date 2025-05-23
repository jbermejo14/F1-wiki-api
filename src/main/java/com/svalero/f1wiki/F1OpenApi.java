package com.svalero.f1wiki;

import com.svalero.f1wiki.domain.OpenF1Driver;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import java.util.List;

public interface F1OpenApi {
    @GET("drivers")
    Call<List<OpenF1Driver>> getDriverByNumber(
            @Query("driver_number") String driverNumber,
            @Query("session_key") String sessionKey
    );
}