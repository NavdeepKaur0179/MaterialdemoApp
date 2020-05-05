package com.app.materialmeapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SportNew implements Parcelable {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    private String info;
    private int imageResource;

    public SportNew(String title, String info, int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    protected SportNew(Parcel in) {
        title=in.readString();
        info=in.readString();
        imageResource=in.readInt();
    }

    public static final Creator<SportNew> CREATOR = new Creator<SportNew>() {
        @Override
        public SportNew createFromParcel(Parcel in) {
            return new SportNew(in);
        }

        @Override
        public SportNew[] newArray(int size) {
            return new SportNew[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(info);
        parcel.writeInt(imageResource);
    }
}
