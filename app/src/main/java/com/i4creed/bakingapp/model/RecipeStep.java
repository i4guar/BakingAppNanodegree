package com.i4creed.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class models a step of a recipe.
 * Created by felix on 07-May-18 at 17:23.
 */
public class RecipeStep implements Parcelable{
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private RecipeStep(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
        @Override
        public RecipeStep createFromParcel(Parcel in) {
            return new RecipeStep(in);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
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
     * Returns the short description.
     * @return short description.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the short description.
     * @param shortDescription short description.
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Returns the description.
     * @return description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the description.
     * @param description description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the video URL.
     * @return video URL.
     */
    public String getVideoURL() {
        return videoURL;
    }

    /**
     * Sets the video URL.
     * @param videoURL video URL.
     */
    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    /**
     * Returns the thumbnail url.
     * @return thumbnail url.
     */
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    /**
     * Sets the thumbnail url.
     * @param thumbnailURL thumbnail url.
     */
    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }
}
