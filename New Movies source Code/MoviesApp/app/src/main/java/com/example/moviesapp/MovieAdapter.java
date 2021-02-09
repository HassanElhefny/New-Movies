package com.example.moviesapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.MovieModel.MovieService;
import com.example.moviesapp.MovieModel.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    List<Result> moview_list;
    onMovieClicked listener;

    public MovieAdapter(List<Result> moview_list, onMovieClicked listener) {
        this.moview_list = moview_list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_recycler_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Result currentMovie = moview_list.get(position);
        holder.tv_movie_name.setText(currentMovie.getTitle());
        String imageURL = MovieService.BaseImageUrl + MovieService.imageSizeSmall + currentMovie.getPosterPath();
        Log.e("TAG", "onBindViewHolder: " + imageURL);
        Picasso.get().load(imageURL)
                .placeholder(R.drawable.error)
                .into(holder.iv_movie_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getClickedMovie(currentMovie.getId(),currentMovie.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return moview_list.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_movie_image;
        TextView tv_movie_name;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_movie_image = itemView.findViewById(R.id.recycler_movie_design_movie_iv);
            tv_movie_name = itemView.findViewById(R.id.recycler_movie_design_movie_name_tv);
        }
    }

    public interface onMovieClicked {
        void getClickedMovie(int movieId,String movieTitle);

    }
}
