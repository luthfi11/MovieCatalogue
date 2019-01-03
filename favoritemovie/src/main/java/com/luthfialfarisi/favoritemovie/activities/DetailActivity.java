package com.luthfialfarisi.favoritemovie.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luthfialfarisi.favoritemovie.R;
import com.luthfialfarisi.favoritemovie.entity.MovieItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.luthfialfarisi.favoritemovie.utils.DatabaseContract.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    public Context context;
    private MovieItem movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView imgPoster = findViewById(R.id.imgPoster);
        ImageView imgBackdrop = findViewById(R.id.imgBackdrop);
        TextView lblJudul = findViewById(R.id.lblJudul);
        TextView lblTanggal = findViewById(R.id.lblTanggalRilis);
        TextView lblKeterangan = findViewById(R.id.lblKeterangan);
        TextView lblPopularitas = findViewById(R.id.lblPopularitas);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null){

                if(cursor.moveToFirst()) movie = new MovieItem(cursor);
                cursor.close();
            }
        }

        String moviePoster = movie.getPoster();
        String movieBackdrop = movie.getBackdrop();
        final String movieTitle = movie.getMovieTitle();
        final String movieRelease = movie.getMovieReleaseDate();
        final String movieoverview = movie.getMovieOverview();
        String moviePopularity = movie.getMoviePopularity();

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

        final int movieId = movie.getId();

        Button btnDelete = findViewById(R.id.btnHapus);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(movieId);
            }
        });

        Button btnShare = findViewById(R.id.btnBagikan);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, movieTitle+"\n\n"+movieoverview+"\n\n Release Date : "+movieRelease);
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
    }

    private void showAlertDialog(final int movieID) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(getResources().getString(R.string.hapus));
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.pesan_hapus))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.ya), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + movieID),null,null);
                        finish();
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
