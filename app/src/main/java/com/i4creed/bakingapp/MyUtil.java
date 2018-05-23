package com.i4creed.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.i4creed.bakingapp.model.Recipe;

/**
 * Created by felix on 21-May-18 at 10:25.
 */
public class MyUtil {
    public static boolean empty( final String s ) {
        return s == null || s.trim().isEmpty();
    }

    public static Recipe getStoredRecipe(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(context.getString(R.string.recipe_key_sp), null);
        return json == null ? null : new Gson().fromJson(json, Recipe.class);
    }
}
