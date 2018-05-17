package com.i4creed.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.backgroundtasks.RecipeQueryLoader;
import com.i4creed.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>, RecipesAdapter.ListItemClickListener {

    @BindView(R.id.main_container)
    FrameLayout MainContainer;

    private static final int RECIPE_QUERY_LOADER = 22;
    private RecipesAdapter recipesAdapter = new RecipesAdapter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LoaderManager loaderManager = getSupportLoaderManager();
        if (loaderManager.getLoader(RECIPE_QUERY_LOADER) == null) {
            loaderManager.restartLoader(RECIPE_QUERY_LOADER, null, this);
        } else {
            loaderManager.initLoader(RECIPE_QUERY_LOADER, null, this);
        }
        ButterKnife.bind(this);

        RecipesFragment recipesFragment = new RecipesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.main_container, recipesFragment).commit();

    }


    @NonNull
    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipeQueryLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        recipesAdapter.setRecipeList(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Recipe>> loader) {
        recipesAdapter.setRecipeList(null);
    }

    public RecipesAdapter getRecipesAdapter() {
        return recipesAdapter;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //TODO open RecipeDetailFragment
    }
}
