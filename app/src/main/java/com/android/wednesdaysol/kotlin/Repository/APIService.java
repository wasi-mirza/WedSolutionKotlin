package com.android.wednesdaysol.kotlin.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("search?")
    Call<String> search_songs(
            @Query("term") String term
    );
}
