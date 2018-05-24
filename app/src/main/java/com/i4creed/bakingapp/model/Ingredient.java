package com.i4creed.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class models an ingredient
 * Created by felix on 07-May-18 at 17:22.
 */
public class Ingredient implements Parcelable {
    private double quantity;
    private String measure;
    private String ingredient;

    private Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    /**
     * Returns the quantity.
     * @return quantity.
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity.
     * @param quantity quantity.
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the measurement unit.
     * @return measurement unit.
     */
    public String getMeasure() {
        return measure;
    }

    /**
     * Sets the measurement unit.
     * @param measure measurement unit.
     */
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    /**
     * Returns the name of the ingredient.
     * @return name of ingredient.
     */
    public String getIngredient() {
        return ingredient;
    }

    /**
     * Sets the name of the ingredient.
     * @param ingredient name of ingredient.
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
