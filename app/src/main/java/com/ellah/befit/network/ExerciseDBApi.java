package com.ellah.befit.network;

import com.ellah.befit.model.ExerciseDbResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ExerciseDBApi {
    @GET("exercises")
    Call<List<ExerciseDbResponse>> getExercises();

    @GET("exercises/name")
    Call<List<ExerciseDbResponse>> getExercisesByName(
            @Query("name") String name
    );
}
