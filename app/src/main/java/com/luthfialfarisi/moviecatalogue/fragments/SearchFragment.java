package com.luthfialfarisi.moviecatalogue.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.luthfialfarisi.moviecatalogue.adapters.MovieLoader;
import com.luthfialfarisi.moviecatalogue.R;
import com.luthfialfarisi.moviecatalogue.adapters.MovieAdapter;
import com.luthfialfarisi.moviecatalogue.models.MovieItem;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {

    RecyclerView listView;
    MovieAdapter adapter;
    SearchView keyword;

    private ArrayList<MovieItem> listMovie;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        keyword = view.findViewById(R.id.keyword);
        listView = view.findViewById(R.id.listMovie);

        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovie = new ArrayList<>();

        adapter = new MovieAdapter(listMovie, getActivity().getApplicationContext());
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        keyword.onActionViewExpanded();
        keyword.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String pencarian = keyword.getQuery().toString();

                if (TextUtils.isEmpty(pencarian)) return false;

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_MOVIE, pencarian);
                getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pencarian = keyword.getQuery().toString();

                if (TextUtils.isEmpty(pencarian)) return false;

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_MOVIE, pencarian);
                getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
                return false;
            }
        });

        String judul = keyword.getQuery().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, judul);

        getLoaderManager().initLoader(0, bundle, SearchFragment.this);

        return view;
    }

    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int id, Bundle args) {
        String movie = "";
        if (args != null) {
            movie = args.getString(EXTRAS_MOVIE);
        }

        return new MovieLoader(getActivity(), movie);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }
}
