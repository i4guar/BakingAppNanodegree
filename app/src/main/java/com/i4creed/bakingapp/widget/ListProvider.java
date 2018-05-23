package com.i4creed.bakingapp.widget;

/**
 * Created by felix on 23-May-18 at 10:55.
 */

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.model.Ingredient;
import com.i4creed.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.Collections;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 *
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private final int mAppWidgetId;
    private ArrayList<Ingredient> listItemList = new ArrayList<>();
    private Context context;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(context.getString(R.string.recipe_key_sp), null);
        Recipe recipe = json == null ? null : new Gson().fromJson(json, Recipe.class);
        Collections.addAll(listItemList, recipe.getIngredients());
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