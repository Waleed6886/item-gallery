package com.example.thiqah.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by Thiqah on 2/18/2018.
 */

public class CoverPhotos extends SugarRecord<CoverPhotos> implements Parcelable {

    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("IDBook")
    @Expose
    private int iDBook;
    @SerializedName("Url")
    @Expose
    private String url;

    public CoverPhotos(int iD, int iDBook, String url) {
        this.iD = iD;
        this.iDBook = iDBook;
        this.url = url;
    }

    protected CoverPhotos(Parcel in) {
        iD = in.readInt();
        iDBook = in.readInt();
        url = in.readString();
    }

    public CoverPhotos() {

    }

    public static final Creator<CoverPhotos> CREATOR = new Creator<CoverPhotos>() {
        @Override
        public CoverPhotos createFromParcel(Parcel in) {
            return new CoverPhotos(in);
        }

        @Override
        public CoverPhotos[] newArray(int size) {
            return new CoverPhotos[size];
        }
    };

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public int getIDBook() {
        return iDBook;
    }

    public void setIDBook(int iDBook) {
        this.iDBook = iDBook;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(iD);
        parcel.writeInt(iDBook);
        parcel.writeString(url);
    }
}

