package com.i4creed.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Service providing the adapter for the widget.
 * Created by felix on 23-May-18 at 10:52.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListProvider(this.getApplicationContext(), intent));
    }

}
