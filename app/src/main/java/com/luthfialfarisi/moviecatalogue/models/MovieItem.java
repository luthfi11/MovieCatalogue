package com.luthfialfarisi.moviecatalogue.models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.luthfialfarisi.moviecatalogue.utils.DatabaseContract;

import org.json.JSONObject;

import static com.luthfialfarisi.moviecatalogue.utils.DatabaseContract.getColumnInt;
import static com.luthfialfarisi.moviecatalogue.utils.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {
    private int id;
    private String poster,movieTitle,movieOverview,movieReleaseDate,moviePopularity,backdrop;

    public MovieItem() {

    }

    public MovieItem(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.MovieColumns._ID);
        this.poster = getColumnString(cursor, DatabaseContract.MovieColumns.POSTER);
        this.movieTitle = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.movieOverview = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.movieReleaseDate = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.moviePopularity = getColumnString(cursor, DatabaseContract.MovieColumns.POPULARITY);
        this.backdrop = getColumnString(cursor, DatabaseContract.MovieColumns.BACKDROP);
    }

    public MovieItem(JSONObject object) {
        try {
            int id = object.getInt("id");
            String poster = object.getString("poster_path");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String release = object.getString("release_date");
            String popularity = object.getString("popularity");
            String backdrop = object.getString("backdrop_path");

            this.id = id;
            this.poster = poster;
            this.movieTitle = title;
            this.movieOverview = overview;
            this.movieReleaseDate = release;
            this.moviePopularity = popularity;
            this.backdrop = backdrop;

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    protected MovieItem(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        movieTitle = in.readString();
        movieOverview = in.readString();
        movieReleaseDate = in.readString();
        moviePopularity = in.readString();
        backdrop = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) { this.poster = poster; }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String title) {
        this.movieTitle = title;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setMovieOverview(String overview) {
        this.movieOverview = overview;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String releaseDate) {
        this.movieReleaseDate = releaseDate;
    }

    public String getMoviePopularity() { return moviePopularity; }

    public void setMoviePopularity(String popularity) { this.moviePopularity = popularity; }

    public String getBackdrop() { return  backdrop; }

    public void setBackdrop(String backdrop) { this.backdrop = backdrop; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieOverview);
        dest.writeString(this.movieReleaseDate);
        dest.writeString(this.moviePopularity);
        dest.writeString(this.backdrop);
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
