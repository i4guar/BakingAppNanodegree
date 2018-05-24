package com.i4creed.bakingapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.i4creed.bakingapp.widget.IngredientsWidget;
import com.i4creed.bakingapp.R;
import com.i4creed.bakingapp.util.MyUtil;
import com.i4creed.bakingapp.model.Ingredient;
import com.i4creed.bakingapp.model.Recipe;
import com.i4creed.bakingapp.model.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class handles the data needed for the detail of a recipe like the ingredients and the
 * instruction and is an adapter for the recycler view.
 * Created by felix on 17-May-18 at 16:02.
 */
public class RecipeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NUMBER_OF_INFO_ITEM = 1;
    private static final int STEP_ITEM = 0;
    private static final int INFO_ITEM = 1;
    final private InstructionClickListener onClickListener;
    private Recipe recipe;

    public RecipeDetailAdapter(InstructionClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RecipeDetailAdapter(InstructionClickListener onClickListener, Recipe recipe) {
        this.onClickListener = onClickListener;
        this.recipe = recipe;
    }

    /**
     * Refreshes the ui of the add to widget button.
     * @param btn btn to update.
     * @param context current context.
     */
    private void updateAddToWidgetButton(Button btn, Context context) {
        Recipe storedRecipe = MyUtil.getStoredRecipe(context);
        if ((storedRecipe != null ? storedRecipe.getId() : -1) == recipe.getId()) {
            btn.setEnabled(false);
            btn.setActivated(false);
            btn.setText(R.string.is_shown_in_widget_btn);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case INFO_ITEM:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recipe_info_item, parent, false);

                return new InfoViewHolder(view);
            case STEP_ITEM:
            default:
                View stepView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.step_list_item, parent, false);
                return new StepViewHolder(stepView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case INFO_ITEM:
                ((InfoViewHolder)holder).bind();
                break;
            case STEP_ITEM:
            default:
                ((StepViewHolder)holder).bind(recipe.getSteps()[position - 1]);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return recipe.getSteps().length + NUMBER_OF_INFO_ITEM;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return INFO_ITEM;
        } else {
            return STEP_ITEM;
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Click listener for list items.
     */
    public interface InstructionClickListener {
        void onStepItemClick(int index);
    }

    /**
     * StepViewHolder for the list items of recipes.
     */
    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RecipeStep recipeStep;

        @BindView(R.id.short_description)
        TextView shortDescription;
        @BindView(R.id.instruction_type)
        ImageView instructionType;

        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onStepItemClick(getAdapterPosition() - 1);
        }

        void bind(RecipeStep step) {
            recipeStep = step;
            if(MyUtil.empty(step.getThumbnailURL()) && MyUtil.empty(step.getVideoURL())) {
                instructionType.setBackgroundResource(R.drawable.ic_text_fields_black_24dp);
            }else {
                instructionType.setBackgroundResource(R.drawable.ic_play_circle);
            }
            shortDescription.setText(step.getShortDescription());
        }
    }

    /**
     * InfoViewHolder for the info item at the top.
     */
    class InfoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.add_to_widget)
        Button addToWidget;
        @BindView(R.id.recipe_title)
        TextView recipeTitle;
        @BindView(R.id.ingredients)
        LinearLayout ingredients;

        InfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {
            final Context context = ingredients.getContext();
            recipeTitle.setText(recipe.getName());
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.setMarginStart(16);
            ingredients.removeAllViews();
            for (Ingredient i: recipe.getIngredients()) {
                TextView tv = new TextView(context);
                tv.setTextColor(Color.WHITE);
                tv.setLayoutParams(llp);
                tv.setText(String.format("%s: %s %s", i.getIngredient(), i.getQuantity(), i.getMeasure()));
                ingredients.addView(tv);
            }
            updateAddToWidgetButton(addToWidget, context);
            addToWidget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String json = recipe == null ? null : new Gson().toJson(recipe);
                    SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key),Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString(context.getString(R.string.recipe_key_sp), json).apply();
                    updateWidget(context);
                    updateAddToWidgetButton(addToWidget, context);
                }
            });
        }


        void updateWidget(Context context) {
            IngredientsWidget.updateAllAppWidgets(context);
        }
    }

}