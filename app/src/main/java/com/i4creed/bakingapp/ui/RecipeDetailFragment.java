package com.i4creed.bakingapp.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.i4creed.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass which displays the detail of a recipe.
 */
public class RecipeDetailFragment extends Fragment {

    @BindView(R.id.recipe_detail_list)
    RecyclerView recipeDetailRv;
    MainActivity main;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, root);

        recipeDetailRv.setLayoutManager(new LinearLayoutManager(getContext()));
        main = (MainActivity) getActivity();
        main.showUpButton();
        if (main != null) {
            recipeDetailRv.setAdapter(main.getRecipeDetailAdapter());
        }

        return root;
    }


}
