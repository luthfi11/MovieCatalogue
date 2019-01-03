package com.luthfialfarisi.moviecatalogue.fragments;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luthfialfarisi.moviecatalogue.R;
import com.luthfialfarisi.moviecatalogue.adapters.FavoriteAdapter;

import static com.luthfialfarisi.moviecatalogue.utils.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends Fragment {

    private RecyclerView rv;
    private FavoriteAdapter adapter;
    private View view;
    private Cursor listMovie;
    
    private static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=60bf79a20ebb1e7e480ffe0d5c1bb2eb&language=en-US";

    public FavoriteFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_movie, container,false);

        rv = view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new FavoriteAdapter(listMovie, getContext());
        adapter.setListMovie(listMovie);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new loadData().execute();
    }

    public class loadData extends AsyncTask<Void, Void, Cursor> {
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public Cursor doInBackground(Void... voids) {
            return getActivity().getApplicationContext().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        public void onPostExecute(Cursor movie) {
            super.onPostExecute(movie);

            listMovie = movie;
            adapter.setListMovie(listMovie);
            adapter.notifyDataSetChanged();

            if (listMovie.getCount() == 0){
                showSnackbarMessage(getResources().getString(R.string.data_favorit));
            }
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rv, message, Snackbar.LENGTH_SHORT).show();
    }
}
