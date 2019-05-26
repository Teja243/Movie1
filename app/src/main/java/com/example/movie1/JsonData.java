package com.example.movie1;

public class JsonData {
    int id, vote_count;
    boolean video, adult;
    long vote_average, popularity;
    String title, poster_path, original_language, original_title,
            backdrop_path, overview, release_date;

    JsonData(int vote_count, int id, boolean video, long vote_average, String title, long popularity, String poster_path,
             String original_language, String original_title, String backdrop_path, boolean adult, String overview, String release_date) {
        this.vote_average = vote_average;
        this.id = id;
        this.vote_count = vote_count;
        this.video = video;
        this.adult = adult;
        this.popularity = popularity;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }

    public long getVote_average() {
        return vote_average;
    }

    public int getId() {
        return id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public boolean isAdult() {
        return adult;
    }

    public long getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
