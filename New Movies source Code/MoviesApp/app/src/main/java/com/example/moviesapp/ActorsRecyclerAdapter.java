package com.example.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.MovieModel.Cast;
import com.example.moviesapp.MovieModel.MovieService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActorsRecyclerAdapter extends RecyclerView.Adapter<ActorsRecyclerAdapter.actorViewHolder> {
    Context context;
    List<Cast> actors;

    public ActorsRecyclerAdapter(Context context, List<Cast> actors) {
        this.context = context;
        this.actors = actors;
    }

    @NonNull
    @Override
    public actorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.actors_recycler_design, parent, false);
        return new actorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull actorViewHolder holder, int position) {
        Cast currentActor = actors.get(position);
        holder.actorName.setText(currentActor.getOriginalName());
        String actorProfileImageURL = MovieService.BaseImageUrl + MovieService.imageSizeSmall + currentActor.getProfilePath();
        Picasso.get().load(actorProfileImageURL)
                .placeholder(R.drawable.person)
                .into(holder.actorImage);
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public class actorViewHolder extends RecyclerView.ViewHolder {
        CircleImageView actorImage;
        TextView actorName;

        public actorViewHolder(@NonNull View itemView) {
            super(itemView);
            actorImage = itemView.findViewById(R.id.actors_recycler_design_iv);
            actorName = itemView.findViewById(R.id.actors_recycler_design_actor_name_tv);
        }
    }
}
