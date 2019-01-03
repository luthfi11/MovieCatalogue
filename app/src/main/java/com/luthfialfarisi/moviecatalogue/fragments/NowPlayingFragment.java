package com.luthfialfarisi.moviecatalogue.fragments;

import android.app.ProgressDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.luthfialfarisi.moviecatalogue.R;
import com.luthfialfarisi.moviecatalogue.adapters.MovieCardAdapter;
import com.luthfialfarisi.moviecatalogue.models.MovieItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class NowPlayingFragment extends Fragment {

    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private View view;
    private LinkedList<MovieItem> listMovie;

    private static final String API_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=60bf79a20ebb1e7e480ffe0d5c1bb2eb&language=en-US";

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card_movie, container,false);

        rv = view.findViewById(R.id.recycle_view);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        listMovie = new LinkedList<>();

        loadNowPlaying();

        return view;
    }

    public void loadNowPlaying() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.setMessage(getResources().getString(R.string.memuat));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray array = jsonObject.getJSONArray("results");
                    for (int i = 0; i < array.length(); i++){

                        MovieItem movie = new MovieItem(array.getJSONObject(i));

                        JSONObject data = array.getJSONObject(i);
                        movie.setMovieTitle(data.getString("title"));
                        movie.setMovieOverview(data.getString("overview"));
                        movie.setMovieReleaseDate(data.getString("release_date"));
                        movie.setPoster(data.getString("poster_path"));

                        listMovie.add(movie);

                    }

                    adapter = new MovieCardAdapter(listMovie, getActivity());
                    rv.setAdapter(adapter);

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                loadNowPlaying();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
