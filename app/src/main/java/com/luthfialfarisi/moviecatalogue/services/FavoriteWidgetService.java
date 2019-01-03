package com.luthfialfarisi.moviecatalogue.services;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.luthfialfarisi.moviecatalogue.widgets.StackRemoteViewsFactory;

public class FavoriteWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
