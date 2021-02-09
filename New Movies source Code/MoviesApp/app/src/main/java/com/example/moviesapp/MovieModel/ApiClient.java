package com.example.moviesapp.MovieModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASEURL = "https://api.themoviedb.org/3/";
    private static final OkHttpClient client;
    private static Retrofit instance;
    public static final String apiKey = "7c513cbab31f5add1ba5eb93d2e54d70";

    static {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(logging).build();
    }

    private static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return instance;
    }

    public static MovieService getMovieService(){
        return getInstance().create(MovieService.class);
    }
}
