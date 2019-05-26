package com.example.movie1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MovieActivity extends AppCompatActivity {
    static final String poster_path_string = "POSTER_PATH";
    static final String backdrop_path_string = "BACK_DROP_PATH";
    static final String original_title_string = "O_TITLE";
    static final String title_string = "TITLE";
    static final String synopsis_string = "SYNOPSIS";
    static final String rating_string = "RATING";
    static final String release_string = "RELEASE_DATE";
    static final String language_string = "LANGUAGE";
    ImageView back_drop, poster_image;
    TextView title, synopsis, rating, release_date, language;
    JsonUtils js;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        //back_drop = (ImageView) findViewById(R.id.id_backdrop_poster);
        poster_image = (ImageView) findViewById(R.id.id_poster);
        title = (TextView) findViewById(R.id.id_title);
        rating = (TextView) findViewById(R.id.id_user_rating);
        synopsis = (TextView) findViewById(R.id.id_synopsis);
        release_date = (TextView) findViewById(R.id.id_release_date);
        language = (TextView) findViewById(R.id.id_language);
        ratingBar = (RatingBar) findViewById(R.id.id_rating_bar);
        js = new JsonUtils();
        Bundle bundle = getIntent().getExtras();
        String backdrop_path = bundle.getString(backdrop_path_string);
        String tv_poster = bundle.getString(poster_path_string);
        String tv_title = bundle.getString(original_title_string);
        String title_normal = bundle.getString(title_string);
        String tv_synopsis = bundle.getString(synopsis_string);
        double tv_rating = bundle.getDouble(rating_string);
        String tv_releaseDate = bundle.getString(release_string);
        String tv_language = bundle.getString(language_string);
       /* Picasso.with(this).load(String.valueOf(js.buildImageUrl(backdrop_path))).into(back_drop);
        Picasso.with(this).load(String.valueOf(js.buildImageUrl(tv_poster))).into(poster_image);*/
        Glide.with(this).load("https://image.tmdb.org/t/p/w185"+tv_poster).into(poster_image);
        title.setText(tv_title);
        synopsis.setText(tv_synopsis);
        release_date.setText(tv_releaseDate);
        rating.setText(Double.toString(tv_rating));
        language.setText(tv_language);
        ratingBar.setRating((float) tv_rating);
        setTitle(tv_title);
    }
}
