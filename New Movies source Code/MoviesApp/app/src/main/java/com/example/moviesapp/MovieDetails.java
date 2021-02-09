package com.example.moviesapp;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.MovieModel.ApiClient;
import com.example.moviesapp.MovieModel.Cast;
import com.example.moviesapp.MovieModel.Genre;
import com.example.moviesapp.MovieModel.MovieService;
import com.example.moviesapp.MovieModel.ResultDetails;
import com.example.moviesapp.MovieModel.ReviewResult;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetails extends AppCompatActivity {
    public static final String MOVIE_EXTRA_ID = "MOVIE_ID";
    public static final String MOVIE_EXTRA_URL = "MOVIE_URL";
    private static final int Default_ID = -1;
    private static final String TAG = "MovieDetails";
    AppBarLayout barLayout;
    ImageView Banner, Poster;
    TextView txt_title, txt_release_data, txt_language, txt_overView, text_vote1, text_vote2;
    ChipGroup genres;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView rv_reviews, rv_actors;
    List<Cast> actors;
    ActorsRecyclerAdapter actorsRecyclerAdapter;
    ReviewsAdapter reviewsAdapter;
    List<ReviewResult> reviews;
    Toolbar toolbar;
    ResultDetails responseDetails = new ResultDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        final int movieId = getIntent().getIntExtra(MOVIE_EXTRA_ID, Default_ID);
        if (movieId == Default_ID) {
            return;
        }
        initializeViews();
        buildActorsRecycler();
        buildReviewsRecycler();
        getDataFromTheServer(movieId);
    }

    private void buildActorsRecycler() {
        actors = new ArrayList<>();
        rv_actors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        actorsRecyclerAdapter = new ActorsRecyclerAdapter(this, actors);
        rv_actors.setAdapter(actorsRecyclerAdapter);

    }

    private void buildReviewsRecycler() {
        reviews = new ArrayList<>();
        reviewsAdapter = new ReviewsAdapter(reviews);
        rv_reviews.setLayoutManager(new LinearLayoutManager(this));
        rv_reviews.setAdapter(reviewsAdapter);
    }

    private void getDataFromTheServer(int movieId) {
        ApiClient.getMovieService().getMovieDetails(movieId, ApiClient.apiKey).enqueue(new Callback<ResultDetails>() {
            @Override
            public void onResponse(Call<ResultDetails> call, Response<ResultDetails> response) {
                if (response.isSuccessful()) {
                    responseDetails = response.body();
                    putDataInTheViews();
                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResultDetails> call, Throwable t) {

            }
        });
    }

    private void putDataInTheViews() {
        actors.clear();
        actors.addAll(responseDetails.getCredits().getCast());
        actorsRecyclerAdapter.notifyDataSetChanged();

        reviews.clear();
        reviews.addAll(responseDetails.getReviews().getResults());
        reviewsAdapter.notifyDataSetChanged();

        txt_language.setText(responseDetails.getOriginalLanguage());
        txt_release_data.setText(responseDetails.getReleaseDate());
        txt_title.setText(responseDetails.getOriginalTitle());
        txt_overView.setText(responseDetails.getOverview());

        text_vote1.setText(responseDetails.getVoteAverage() + "");
        text_vote2.setText(responseDetails.getVoteCount() + "");

        Picasso.get().load(MovieService.BaseImageUrl + MovieService.imageSizeBannar + responseDetails.getBackdropPath())
                .placeholder(R.drawable.error)
                .into(Banner);

        Picasso.get().load(MovieService.BaseImageUrl + MovieService.imageSizeSmall + responseDetails.getPosterPath())
                .placeholder(R.drawable.error)
                .into(Poster);

        for (Genre g : responseDetails.getGenres()) {
            Chip chip = new Chip(MovieDetails.this);
            chip.setText(g.getName());
            chip.setChipStrokeWidth(3);
            chip.setChipBackgroundColorResource(android.R.color.transparent);
            chip.setChipStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(MovieDetails.this, R.color.colorPrimary)));
            genres.addView(chip);

        }
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.detail_toolbar);
        text_vote1 = findViewById(R.id.details_part2_vote_tv);
        text_vote2 = findViewById(R.id.details_part2_vote_tv2);
        barLayout = findViewById(R.id.detail_app_bar);
        collapsingToolbarLayout = findViewById(R.id.detail_collapsing_toolbar);
        Banner = findViewById(R.id.detail_iv_banner);
        Poster = findViewById(R.id.detail2_iv_poster);
        txt_language = findViewById(R.id.details_part2_language_tv);
        txt_overView = findViewById(R.id.details_part2_overview_tv);
        txt_title = findViewById(R.id.details_part2_movie_name_tv);
        txt_release_data = findViewById(R.id.details_part2_release_date_tv);

        genres = findViewById(R.id.details_part2_chip_group);


        rv_actors = findViewById(R.id.details_part2_actors_recycler_view);
        rv_reviews = findViewById(R.id.details_part2_reviews_recycler_view);

        setSupportActionBar(toolbar);
        setTitle(getIntent().getStringExtra(MOVIE_EXTRA_URL));
    }
}