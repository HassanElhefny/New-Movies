package com.example.moviesapp.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    String BaseImageUrl = "https://image.tmdb.org/t/p";
    String imageSizeSmall = "/w185";
    String imageSizeBannar = "/w780";

    @GET("movie/popular")
    Call<MovieResponse> getMovieList(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}?append_to_response=videos,credits,reviews")
    Call<ResultDetails> getMovieDetails(@Path("movie_id") int movie_id,
                                        @Query("api_key") String apiKey);
}
