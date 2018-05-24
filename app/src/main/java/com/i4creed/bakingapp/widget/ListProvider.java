package com.i4creed.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.i4creed.bakingapp.util.MyUtil;
import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 * Adapter for the list view in the widget.
 */
class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<Ingredient> listItemList = new ArrayList<>();
    private Context context;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Collections.addAll(listItemList, Objects.requireNonNull(MyUtil.getStoredRecipe(context)).getIngredients());
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.ingredient_textview_widget);
        Ingredient listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.ingredients, listItem.getIngredient() + ": " + listItem.getQuantity() + " " + listItem.getMeasure() );
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}