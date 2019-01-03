package com.luthfialfarisi.moviecatalogue.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luthfialfarisi.moviecatalogue.activities.MovieDetailActivity;
import com.luthfialfarisi.moviecatalogue.models.MovieItem;
import com.luthfialfarisi.moviecatalogue.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<MovieItem> movieData = new ArrayList<>();
    private Context context;

    public MovieAdapter(ArrayList<MovieItem> movieData, Context context) {
        this.context = context;
        this.movieData = movieData;
    }

    public void setData(ArrayList<MovieItem> items) {
        movieData.clear();
        movieData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, final int position) {

        String imgUrl = "http://image.tmdb.org/t/p/w185/";

        Picasso.with(context).load(imgUrl+movieData.get(position).getPoster()).into(holder.moviePoster);

        holder.movieName.setText(movieData.get(position).getMovieTitle());
        holder.movieoverview.setText(movieData.get(position).getMovieOverview());

        String mReleaseDate = movieData.get(position).getMovieReleaseDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(mReleaseDate);

            SimpleDateFormat nDateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String nReleaseDate = nDateFormat.format(date);
            holder.movieRelease.setText(nReleaseDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItem item = movieData.get(position);

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
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieName, movieoverview, movieRelease;
        LinearLayout item;

        ViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.posterMovie);
            movieName = itemView.findViewById(R.id.txtJudul);
            movieoverview = itemView.findViewById(R.id.txtKeterangan);
            movieRelease = itemView.findViewById(R.id.txtRilis);
            item = itemView.findViewById(R.id.rvItem);
        }
    }
}
