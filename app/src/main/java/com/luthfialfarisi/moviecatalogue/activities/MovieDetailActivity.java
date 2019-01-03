package com.luthfialfarisi.moviecatalogue.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luthfialfarisi.moviecatalogue.R;
import com.luthfialfarisi.moviecatalogue.models.MovieItem;
import com.luthfialfarisi.moviecatalogue.utils.MovieHelper;
import com.luthfialfarisi.moviecatalogue.widgets.FavoriteWidget;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.luthfialfarisi.moviecatalogue.utils.DatabaseContract.CONTENT_URI;
import static com.luthfialfarisi.moviecatalogue.utils.DatabaseContract.MovieColumns.*;

public class MovieDetailActivity extends AppCompatActivity {

    public static String EXTRA_ID = "EXTRA_ID";
    public static String EXTRA_POSTER = "EXTRA_POSTER";
    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_RELEASE = "EXTRA_RELEASE";
    public static String EXTRA_OVERVIEW = "EXTRA_OVERVIEW";
    public static String EXTRA_POPULARITY = "EXTRA_POPULATIRY";
    public static String EXTRA_BACKDROP = "EXTRA_BACKDROP";

    public Context context;
    public MovieHelper helper;
    public MovieItem movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar2 = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imgPoster = findViewById(R.id.imgPoster);
        ImageView imgBackdrop = findViewById(R.id.imgBackdrop);
        TextView lblJudul = findViewById(R.id.lblJudul);
        TextView lblTanggal = findViewById(R.id.lblTanggalRilis);
        TextView lblKeterangan = findViewById(R.id.lblKeterangan);
        TextView lblPopularitas = findViewById(R.id.lblPopularitas);

        final int movieId = getIntent().getIntExtra(EXTRA_ID,0);
        final String moviePoster = getIntent().getStringExtra(EXTRA_POSTER);
        final String movieBackdrop = getIntent().getStringExtra(EXTRA_BACKDROP);
        final String movieTitle = getIntent().getStringExtra(EXTRA_TITLE);
        final String movieRelease = getIntent().getStringExtra(EXTRA_RELEASE);
        final String movieoverview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        final String moviePopularity = getIntent().getStringExtra(EXTRA_POPULARITY);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w300/"+moviePoster).into(imgPoster);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500/"+movieBackdrop).into(imgBackdrop);

        lblJudul.setText(movieTitle);
        lblKeterangan.setText(movieoverview);
        lblPopularitas.setText(moviePopularity);

        String mReleaseDate = movieRelease;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(mReleaseDate);

            SimpleDateFormat nDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String nReleaseDate = nDateFormat.format(date);
            lblTanggal.setText(nReleaseDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        final FloatingActionButton fab_fav = findViewById(R.id.fab_favorit);
        helper = new MovieHelper(this);
        helper.open();

        if(helper.checkData(movieId)){
            fab_fav.setImageResource(R.drawable.star_on);
        }

        fab_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put(_ID,movieId);
                values.put(POSTER,moviePoster);
                values.put(BACKDROP,movieBackdrop);
                values.put(TITLE,movieTitle);
                values.put(RELEASE_DATE,movieRelease);
                values.put(POPULARITY,moviePopularity);
                values.put(OVERVIEW,movieoverview);

                if(helper.checkData(movieId) == false) {
                    getContentResolver().insert(CONTENT_URI,values);
                    fab_fav.setImageResource(R.drawable.star_on);
                    Snackbar.make(v, getResources().getString(R.string.data_ditambahkan),Snackbar.LENGTH_SHORT).show();

                    int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FavoriteWidget.class));
                    for (int id : widgetIDs) {
                        AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(id, R.id.stack_view);
                    }
                } else {
                    showAlertDialog(movieId, fab_fav, v);
                }
            }
        });
    }

    public void shareIntent() {
        Intent sendIntent = new Intent();
        String movieTitle = getIntent().getStringExtra(EXTRA_TITLE);
        String movieRelease = getIntent().getStringExtra(EXTRA_RELEASE);
        String movieoverview = getIntent().getStringExtra(EXTRA_OVERVIEW);

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, movieTitle+"\n\n"+movieoverview+"\n\n Release Date : "+movieRelease);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent,"Share via");
        startActivity(sendIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareIntent();
                return true;
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

    private void showAlertDialog(final int movieID, final FloatingActionButton fab_fav, final View v) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(getResources().getString(R.string.hapus));
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.pesan_hapus))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.ya), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movieID),null,null);
                        fab_fav.setImageResource(R.drawable.star_off);
                        Snackbar.make(v, getResources().getString(R.string.data_dihapus),Snackbar.LENGTH_SHORT).show();

                        int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FavoriteWidget.class));
                        for (int ids : widgetIDs) {
                            AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(ids, R.id.stack_view);
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.tidak), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
