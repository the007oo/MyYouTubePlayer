package com.splanet.myyoutubeplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Phattarapong on 2/20/2018.
 */

public class Video implements Parcelable {
    private String title;
    private String description;
    private String thumbnailURL;
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.thumbnailURL);
        dest.writeString(this.id);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.thumbnailURL = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
