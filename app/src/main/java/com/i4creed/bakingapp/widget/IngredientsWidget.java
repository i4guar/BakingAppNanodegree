package com.i4creed.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.i4creed.bakingapp.util.MyUtil;
import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.model.Recipe;
import com.i4creed.bakingapp.ui.MainActivity;

/**
 * Implementation of App Widget functionality showing the ingredients of a recipe.
 */
public class IngredientsWidget extends AppWidgetProvider {

    public static String EXTRA_ITEM = "com.i4creed.IngredientsWidget.EXTRA_ITEM";

    /**
     * Updates specific widget by id.
     * @param context current context.
     * @param appWidgetManager app widget manager.
     * @param appWidgetId app widget id.
     */
    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        Recipe recipe = MyUtil.getStoredRecipe(context);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to list view of the widget
        views.setRemoteAdapter(R.id.widget_list,svcIntent);

        views.setTextViewText(R.id.widget_title, "Ingredients for " + (recipe != null ? recipe.getName() : "not visible item"));
        views.setEmptyView(R.id.widget_list, R.id.empty_view);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_frame, pendingIntent);
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

    /**
     * Updates all available ids.
     * @param context current context.
     */
    public static void updateAllAppWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientsWidget.class));
        for (int id:ids) {
            updateAppWidget(context, appWidgetManager, id);
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

