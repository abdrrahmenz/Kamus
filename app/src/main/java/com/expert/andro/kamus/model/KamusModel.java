package com.expert.andro.kamus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adul on 20/09/17.
 */

public class KamusModel implements Parcelable{

    private int id;
    private String name;
    private String description;

    public KamusModel() {
    }

    public KamusModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected KamusModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<KamusModel> CREATOR = new Creator<KamusModel>() {
        @Override
        public KamusModel createFromParcel(Parcel in) {
            return new KamusModel(in);
        }

        @Override
        public KamusModel[] newArray(int size) {
            return new KamusModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String keyword) {
        this.name = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
    }
}
