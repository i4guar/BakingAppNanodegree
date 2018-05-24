package com.i4creed.bakingapp.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i4creed.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass and views all recipes.
 */
public class RecipesFragment extends Fragment {

    @BindView(R.id.recipes_list)
    RecyclerView recipesRecyclerView;

    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, root);
        MainActivity main = (MainActivity) getActivity();
        if (main != null) {
            int spanCount = 1;
            if(main.isTwoPane()) {
                spanCount = 3;
            }
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),spanCount));

            recipesRecyclerView.setAdapter(main.getRecipesAdapter());
        }


        return root;
    }

}
