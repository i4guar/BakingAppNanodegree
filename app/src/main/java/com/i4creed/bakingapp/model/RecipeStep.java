package com.i4creed.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by felix on 07-May-18 at 17:23.
 */
public class RecipeStep implements Parcelable{
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumnailURL;

    protected RecipeStep(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumnailURL);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumnailURL() {
        return thumnailURL;
    }

    public void setThumnailURL(String thumnailURL) {
        this.thumnailURL = thumnailURL;
    }
}
