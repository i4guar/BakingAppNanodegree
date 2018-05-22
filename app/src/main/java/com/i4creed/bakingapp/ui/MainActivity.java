package com.i4creed.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.backgroundtasks.RecipeQueryLoader;
import com.i4creed.bakingapp.model.Recipe;
import com.i4creed.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>, RecipesAdapter.ListItemClickListener, RecipeDetailAdapter.InstructionClickListener {

    public static final String CURRENT_RECIPE = "currentRecipe";
    public static final String FULLSCREEN_TAG = "fullscreen_tag";

    private static final int RECIPE_QUERY_LOADER = 22;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    @Nullable
    @BindView(R.id.detail_container)
    FrameLayout detailContainer;
    boolean twoPane = false;
    private RecipesAdapter recipesAdapter = new RecipesAdapter(this);
    private RecipeDetailAdapter recipeDetailAdapter = new RecipeDetailAdapter(this);
    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LoaderManager loaderManager = getSupportLoaderManager();
        if (loaderManager.getLoader(RECIPE_QUERY_LOADER) == null) {
            loaderManager.restartLoader(RECIPE_QUERY_LOADER, null, this);
        } else {
            loaderManager.initLoader(RECIPE_QUERY_LOADER, null, this);
        }

        if (detailContainer != null) {
            twoPane = true;
        }
        if (savedInstanceState == null) {
            RecipesFragment recipesFragment = new RecipesFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (twoPane) {
                fragmentManager.beginTransaction().add(R.id.fill_container, recipesFragment, FULLSCREEN_TAG).commit();
            }else {
                fragmentManager.beginTransaction().add(R.id.main_container, recipesFragment).commit();
            }
        }

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

    public RecipeDetailAdapter getRecipeDetailAdapter() {
        return recipeDetailAdapter;
    }

    @Override
    public void onStepItemClick(int stepIndex) {
        DetailStepFragment detailStepFragment = new DetailStepFragment();
        detailStepFragment.setRecipeStep(getCurrentRecipe(), stepIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (twoPane) {
            DetailStepFragment f = (DetailStepFragment) fragmentManager.findFragmentById(R.id.detail_container);
            f.setRecipeStep(getCurrentRecipe(), stepIndex);
            fragmentManager.beginTransaction().detach(f).attach(f).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.main_container, detailStepFragment).addToBackStack(null).commit();
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        setCurrentRecipe(recipesAdapter.getRecipe(clickedItemIndex));
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        DetailStepFragment detailStepFragment = new DetailStepFragment();
        detailStepFragment.setRecipeStep(getCurrentRecipe(),0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (twoPane) {
            Fragment remove = fragmentManager.findFragmentByTag(FULLSCREEN_TAG);
            fragmentManager.beginTransaction().add(R.id.detail_container, detailStepFragment).add(R.id.main_container, recipeDetailFragment).remove(remove).addToBackStack(null).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.main_container, recipeDetailFragment).addToBackStack(null).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CURRENT_RECIPE, currentRecipe);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setCurrentRecipe((Recipe) savedInstanceState.getParcelable(CURRENT_RECIPE));
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public void setCurrentRecipe(Recipe currentRecipe) {
        this.currentRecipe = currentRecipe;
        recipeDetailAdapter.setRecipe(currentRecipe);
    }

    public boolean isTwoPane() {
        return twoPane;
    }

    public void setTwoPane(boolean twoPane) {
        this.twoPane = twoPane;
    }
}
