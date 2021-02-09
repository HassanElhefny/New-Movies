package com.example.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.MovieModel.ApiClient;
import com.example.moviesapp.MovieModel.MovieResponse;
import com.example.moviesapp.MovieModel.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.onMovieClicked {
    RecyclerView rv_movie;
    MovieAdapter movieAdapter;
    List<Result> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        getDataFromApi();
    }

    private void getDataFromApi() {
        ApiClient.getMovieService().getMovieList(ApiClient.apiKey)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            movies.clear();
                            movies.addAll(response.body().getResults());
                            movieAdapter.notifyDataSetChanged();
                        } else {
                            Log.e("TAG", "onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure:  " + t.getMessage());
                    }
                });
    }

    private void initializeViews() {
        rv_movie = findViewById(R.id.main_recyclerView_movies);
        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(movies, this);
        rv_movie.setLayoutManager(new GridLayoutManager(this, 3));
        rv_movie.setAdapter(movieAdapter);
    }

    @Override
    public void getClickedMovie(int movieId,String imageUrl) {
        Intent i = new Intent(MainActivity.this, MovieDetails.class);
        i.putExtra(MovieDetails.MOVIE_EXTRA_ID, movieId);
        i.putExtra(MovieDetails.MOVIE_EXTRA_URL,imageUrl);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
    }
}