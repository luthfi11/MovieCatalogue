package com.luthfialfarisi.favoritemovie.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luthfialfarisi.favoritemovie.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.luthfialfarisi.favoritemovie.utils.DatabaseContract.MovieColumns.*;
import static com.luthfialfarisi.favoritemovie.utils.DatabaseContract.getColumnString;

public class FavoriteMovieAdapter extends CursorAdapter {
    private Cursor listMovie;
    private Context context;

    public FavoriteMovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_list, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView imgPoster;
        TextView tvTitle, tvOverview, tvRelease;

        if (cursor != null){
            imgPoster = view.findViewById(R.id.img_poster);
            tvTitle = view.findViewById(R.id.tv_title);
            tvOverview = view.findViewById(R.id.tv_overview);
            tvRelease = view.findViewById(R.id.tv_release);

            String imgUrl = "http://image.tmdb.org/t/p/w185/";
            Picasso.with(context).load(imgUrl+getColumnString(cursor,POSTER)).into(imgPoster);

            tvTitle.setText(getColumnString(cursor,TITLE));
            tvOverview.setText(getColumnString(cursor,OVERVIEW));

            String mReleaseDate = getColumnString(cursor,RELEASE_DATE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(mReleaseDate);

                SimpleDateFormat nDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
                String nReleaseDate = nDateFormat.format(date);
                tvRelease.setText(nReleaseDate);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }
}
