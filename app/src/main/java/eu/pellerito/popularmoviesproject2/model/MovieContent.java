/*
 * | \  / |
 * |  __ \                | |            |  \/  |          (_)
 * |  ___/ _ \| '_ \| | | | |/ _` | '__| | |\/| |/ _ \ \ / / |/ _ \/ __|
 * | |  | (_) | |_) | |_| | | (_| | |    | |  | | (_) \ V /| |  __/\__ \
 * |_|   \___/| .__/ \__,_|_|\__,_|_|    |_|  |_|\___/ \_/ |_|\___||___/
 * | |
 * |_|
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.pellerito.popularmoviesproject2.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import eu.pellerito.popularmoviesproject2.data.FetchRestManager;
import eu.pellerito.popularmoviesproject2.utility.Costants;

public class MovieContent implements Parcelable {


    @SerializedName(Costants.JSON_MOVIES_ID)
    private int mId;

    @SerializedName(Costants.JSON_MOVIES_TITLE)
    private String mTitle;

    @SerializedName(Costants.JSON_MOVIES_RELEASE_DATE)
    private String mReleaseDate;


    @SerializedName(Costants.JSON_MOVIES_POSTER_PATH)
    private String mPosterPath;

    @SerializedName(Costants.JSON_MOVIES_VOTE_AVERAGE)
    private float mVoteAverage;


    @SerializedName(Costants.JSON_MOVIES_OVERVIEW)
    private String mOverview;


    @SuppressWarnings("unused")
    public MovieContent() {
        FetchRestManager.getInstance();
    }


    public static final Creator<MovieContent> CREATOR = new Creator<MovieContent>() {

        @NonNull
        @Override
        public MovieContent createFromParcel(@NonNull Parcel in) {
            return new MovieContent(in);
        }

        @NonNull
        @Override
        public MovieContent[] newArray(int size) {
            return new MovieContent[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }


    private MovieContent(@NonNull Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mPosterPath = in.readString();
        mVoteAverage = in.readFloat();
        mOverview = in.readString();
    }


    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mTitle);
        out.writeString(mReleaseDate);
        out.writeString(mPosterPath);
        out.writeFloat(mVoteAverage);
        out.writeString(mOverview);

    }


    @SuppressWarnings({"unused", "SameReturnValue"})
    public int getId() {
        return mId;
    }

    @SuppressWarnings("unused")
    public void setId(int id) {
        mId = id;
    }

    public String getOverview() {
        return mOverview;
    }

    @SuppressWarnings("unused")
    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    @SuppressWarnings("unused")
    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    @SuppressWarnings("unused")
    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }


    public String getTitle() {
        return mTitle;
    }

    @SuppressWarnings("unused")
    public void setTitle(String title) {
        mTitle = title;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    @SuppressWarnings("unused")
    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }


}