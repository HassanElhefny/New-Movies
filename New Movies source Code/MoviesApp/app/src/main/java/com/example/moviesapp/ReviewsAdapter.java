package com.example.moviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.moviesapp.MovieModel.ReviewResult;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.reviewPlaceHolder> {
    List<ReviewResult> reviews;

    public ReviewsAdapter(List<ReviewResult> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public reviewPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new reviewPlaceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recycler_design, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull reviewPlaceHolder holder, int position) {
        ReviewResult result = reviews.get(position);

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder().buildRound(result.getAuthor().substring(0,1).toUpperCase(),color);
        holder.iv_author_image.setImageDrawable(drawable);

        holder.tv_author_name.setText(result.getAuthor());
        holder.tv_author_description.setText(result.getContent());

    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public class reviewPlaceHolder extends RecyclerView.ViewHolder {

        ImageView iv_author_image;
        TextView tv_author_name, tv_author_description;

        public reviewPlaceHolder(@NonNull View itemView) {
            super(itemView);
            tv_author_description = itemView.findViewById(R.id.review_recycler_design_author_description_tv);
            tv_author_name = itemView.findViewById(R.id.review_recycler_design_author_name_tv);
            iv_author_image = itemView.findViewById(R.id.review_recycler_design_author_iv);
        }
    }
}
