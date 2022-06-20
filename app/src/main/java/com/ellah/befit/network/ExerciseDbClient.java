package com.ellah.befit.network;

import static com.ellah.befit.Constants.EXERCISEDB_API_KEY;
import static com.ellah.befit.Constants.EXERCISEDB_BASE_URL;

import com.ellah.befit.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExerciseDbClient {
    private static  Retrofit retrofit = null;
    public static ExerciseDBApi getClient() {

        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("x-rapidapi-key", EXERCISEDB_API_KEY)
                                    .addHeader("x-rapidapi-host", "exercisedb.p.rapidapi.com")
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(EXERCISEDB_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ExerciseDBApi.class);
    }
}
