package com.fazil.grievance_ai.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FetchDataInterface {
    // Use filename (fetch.php) or use it in the Activity.
    @GET("get.php")
    Call<String> STRING_CALL(
        @Query("start") int start,
        @Query("limit") int limit,
        @Query("userid") int userID
    );
}
