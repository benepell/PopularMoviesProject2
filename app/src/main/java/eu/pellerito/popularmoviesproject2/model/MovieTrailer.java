package eu.pellerito.popularmoviesproject2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieTrailer implements  Parcelable {


    @SerializedName("id")
    private String mId;

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;


    public static final Parcelable.Creator<MovieTrailer> CREATOR = new Parcelable.Creator<MovieTrailer>() {

        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }


    private MovieTrailer(Parcel in) {
        mId = in.readString();
        mKey = in.readString();
        mName = in.readString();
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mId);
        out.writeString(mKey);
        out.writeString(mName);
    }


    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getKey() {
        return mKey;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }


}