package com.i4creed.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class models a recipe.
 * Created by felix on 07-May-18 at 17:22.
 */
public class Recipe implements Parcelable{
    private int id;
    private String name;
    private Ingredient[] ingredients;
    private RecipeStep[] steps;
    private int servings;
    private String image;

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArray(Ingredient.CREATOR);
        steps = in.createTypedArray(RecipeStep.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedArray(ingredients, flags);
        dest.writeTypedArray(steps, flags);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    /**
     * Returns the id.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the recipe.
     * @return name of recipe.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the recipe.
     * @param name name of recipe.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of ingredients.
     * @return list of ingredients.
     */
    public Ingredient[] getIngredients() {
        return ingredients;
    }

    /**
     * Sets the list of ingredients.
     * @param ingredients list of ingredients.
     */
    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Returns the steps for the recipe.
     * @return steps of recipe.
     */
    public RecipeStep[] getSteps() {
        return steps;
    }

    /**
     * Sets the steps for the recipe.
     * @param steps steps of recipe.
     */
    public void setSteps(RecipeStep[] steps) {
        this.steps = steps;
    }

    /**
     * Returns the number of servings.
     * @return number of servings.
     */
    public int getServings() {
        return servings;
    }

    /**
     * Sets the number of servings.
     * @param servings number of servings.
     */
    public void setServings(int servings) {
        this.servings = servings;
    }

    /**
     * Returns the image url.
     * @return image url.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image url.
     * @param image image url.
     */
    public void setImage(String image) {
        this.image = image;
    }
}
