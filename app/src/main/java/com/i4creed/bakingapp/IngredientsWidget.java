package com.i4creed.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.i4creed.bakingapp.model.Ingredient;
import com.i4creed.bakingapp.model.Recipe;
import com.i4creed.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(context.getString(R.string.recipe_key_sp), null);
        Recipe recipe = json == null ? null : new Gson().fromJson(json, Recipe.class);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        String widgetText = "";
        if (recipe != null) {
            for (Ingredient i : recipe.getIngredients()) {
                widgetText += i.getIngredient() + ": " + i.getQuantity() + " " + i.getMeasure() + "\n";
            }
        } else {
            widgetText = "Add a recipe to see your ingredients";
        }
        views.setTextViewText(R.id.ingredients, widgetText);
        views.setTextViewText(R.id.widget_title, "Ingredients for " + recipe.getName());
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ingredients, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

