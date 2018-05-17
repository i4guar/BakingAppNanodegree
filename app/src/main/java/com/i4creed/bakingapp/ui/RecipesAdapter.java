package com.i4creed.bakingapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by felix on 16-May-18 at 21:56.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    final private ListItemClickListener onClickListener;
    private ArrayList<Recipe> recipeList = new ArrayList<>();

    public RecipesAdapter(ListItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RecipesAdapter(ListItemClickListener onClickListener, ArrayList<Recipe> recipeList) {
        this.onClickListener = onClickListener;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (recipeList != null) {
            return recipeList.size();
        } else {
            return 0;
        }
    }

    /**
     * Set the data for adapter
     *
     * @param recipeList the array list of recipes for the data
     */
    void setRecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public Recipe getRecipe(int index) {
        return recipeList.get(index);
    }

    /**
     * Click listener for list items
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    /**
     * ViewHolder for the list items of recipes
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_title)
        TextView recipeTitleTv;
        @BindView(R.id.recipe_servings)
        TextView recipeServingsTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onListItemClick(clickedPosition);
        }

        public void bind(Recipe recipe) {
            recipeTitleTv.setText(recipe.getName());
            recipeServingsTv.setText(String.valueOf(recipe.getServings()));
        }
    }
}
