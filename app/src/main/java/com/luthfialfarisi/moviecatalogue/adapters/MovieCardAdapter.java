package com.luthfialfarisi.moviecatalogue.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luthfialfarisi.moviecatalogue.activities.MovieDetailActivity;
import com.luthfialfarisi.moviecatalogue.models.MovieItem;
import com.luthfialfarisi.moviecatalogue.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCardAdapter.ViewHolder> {
    private LinkedList<MovieItem> listMovie;
    private Context context;

    public MovieCardAdapter(LinkedList<MovieItem> listMovie, Context context) {
        this.context = context;
        this.listMovie = listMovie;
    }

    private LinkedList<MovieItem> getlistMovie() {
        return listMovie;
    }

    public void setListMovie(LinkedList<MovieItem> listMovie) {
        this.listMovie = listMovie;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String imgUrl = "http://image.tmdb.org/t/p/w185/";

        final MovieItem item = getlistMovie().get(position);

        Picasso.with(context).load(imgUrl+item.getPoster()).into(holder.imgPoster);

        holder.lblJudul.setText(item.getMovieTitle());
        holder.lblKeterangan.setText(item.getMovieOverview());

        String mReleaseDate = item.getMovieReleaseDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(mReleaseDate);

            SimpleDateFormat nDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String nReleaseDate = nDateFormat.format(date);
            holder.lblTanggalRilis.setText(nReleaseDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);

                intent.putExtra(MovieDetailActivity.EXTRA_ID, item.getId());
                intent.putExtra(MovieDetailActivity.EXTRA_POSTER, item.getPoster());
                intent.putExtra(MovieDetailActivity.EXTRA_TITLE, item.getMovieTitle());
                intent.putExtra(MovieDetailActivity.EXTRA_OVERVIEW, item.getMovieOverview());
                intent.putExtra(MovieDetailActivity.EXTRA_RELEASE, item.getMovieReleaseDate());
                intent.putExtra(MovieDetailActivity.EXTRA_POPULARITY, item.getMoviePopularity());
                intent.putExtra(MovieDetailActivity.EXTRA_BACKDROP, item.getBackdrop());

                v.getContext().startActivity(intent);
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, item.getMovieTitle()+"\n\n"+item.getMovieOverview()+"\n\n Release Date : "+item.getMovieReleaseDate());
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent,"Share via");
                v.getContext().startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getlistMovie().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView lblJudul, lblKeterangan, lblTanggalRilis;
        Button btnDetail, btnShare;

        ViewHolder(View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.posterGrid);
            lblJudul = itemView.findViewById(R.id.lblJudulGrid);
            lblKeterangan = itemView.findViewById(R.id.lblKeteranganGrid);
            lblTanggalRilis = itemView.findViewById(R.id.lblTanggalRilisGrid);
            btnDetail = itemView.findViewById(R.id.btnDetail);
            btnShare = itemView.findViewById(R.id.btnShare);
        }
    }
}
