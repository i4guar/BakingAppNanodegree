package com.i4creed.bakingapp.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.i4creed.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {

    @BindView(R.id.recipes_list) RecyclerView recipesRecyclerView;

    MainActivity main;
    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, root);

        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            main = (MainActivity) getActivity();
        if (main != null) {
            recipesRecyclerView.setAdapter(main.getRecipesAdapter());
        }

        return root;
    }

}
