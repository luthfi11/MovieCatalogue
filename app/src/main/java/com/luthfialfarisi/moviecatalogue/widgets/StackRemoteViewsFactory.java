package com.luthfialfarisi.moviecatalogue.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.luthfialfarisi.moviecatalogue.R;
import com.luthfialfarisi.moviecatalogue.models.MovieItem;
import com.luthfialfarisi.moviecatalogue.utils.MovieHelper;
import com.luthfialfarisi.moviecatalogue.widgets.FavoriteWidget;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<MovieItem> movieList;
    private Context mContext;
    private int mAppWidgetId;
    private MovieHelper helper;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        helper = new MovieHelper(mContext);
        helper.open();
        movieList = new ArrayList<>();
        movieList.addAll(helper.getData());
        helper.close();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;
        try {
            bmp =  Picasso.with(mContext).load("http://image.tmdb.org/t/p/w300/"+movieList.get(position).getBackdrop()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("log", movieList.get(position).getBackdrop());
        rv.setImageViewBitmap(R.id.imageView, bmp);
        rv.setTextViewText(R.id.tv_title, movieList.get(position).getMovieTitle());

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);
        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
