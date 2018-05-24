package com.i4creed.bakingapp.backgroundtasks;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Loaders fetches the Recipes from url
 * Created by felix on 16-May-18 at 21:00.
 */
public class RecipeQueryLoader extends Loader<ArrayList<Recipe>> {


    /**
     * URL to fetch recipes from
     */
    private static final String URL_RECIPE = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private Recipe[] recipes;

    public RecipeQueryLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {

        if(recipes != null) {
            deliverResult(new ArrayList<>(Arrays.asList(recipes)));
        }else {
            forceLoad();
        }
    }

    @Override
    protected void onForceLoad() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_RECIPE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        recipes = gson.fromJson(response, Recipe[].class);
                        deliverResult(new ArrayList<>(Arrays.asList(recipes)));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), R.string.recipes_url_fail, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

}
