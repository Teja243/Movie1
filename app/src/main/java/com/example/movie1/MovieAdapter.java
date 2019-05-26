package com.example.movie1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.RecyclerViewHolder> {
    Context c;
    ArrayList<JsonData> jsonPOJOs;
    public final static String Image_URL = "https://image.tmdb.org/t/p/w185";

    public MovieAdapter(Context c, ArrayList<JsonData> jsonPOJOs) {
        this.c = c;
        this.jsonPOJOs = jsonPOJOs;
    }

    @Override
    public MovieAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.recycler_activity, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.RecyclerViewHolder holder, final int position) {
        JsonUtils js = new JsonUtils();
        String s = js.buildImageUrl(jsonPOJOs.get(position).getPoster_path()).toString();
        Glide.with(c).load(s).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return jsonPOJOs.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.id_recycler_poster);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(c, jsonPOJOs.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(c, MovieActivity.class);

                    intent.putExtra("BACK_DROP_PATH", jsonPOJOs.get(position).getBackdrop_path());
                    intent.putExtra("POSTER_PATH", jsonPOJOs.get(position).getPoster_path());
                    intent.putExtra("O_TITLE", jsonPOJOs.get(position).getOriginal_title());
                    intent.putExtra("TITLE", jsonPOJOs.get(position).getTitle());
                    intent.putExtra("RATING", jsonPOJOs.get(position).getVote_average());
                    intent.putExtra("RELEASE_DATE", jsonPOJOs.get(position).getRelease_date());
                    intent.putExtra("LANGUAGE", jsonPOJOs.get(position).getOriginal_language());
                    intent.putExtra("SYNOPSIS", jsonPOJOs.get(position).getOverview());

                    c.startActivity(intent);
                }
            });
        }
    }
}
