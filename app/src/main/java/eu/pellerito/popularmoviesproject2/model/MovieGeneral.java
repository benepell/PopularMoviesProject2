package eu.pellerito.popularmoviesproject2.model;

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


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieGeneral implements Parcelable {

    @SerializedName("results")
    public final List<MovieContent> movieContentList;

    @SuppressWarnings("WeakerAccess")
    protected MovieGeneral(@NonNull Parcel in) {
        movieContentList = in.createTypedArrayList(MovieContent.CREATOR);
    }

    public static final Creator<MovieGeneral> CREATOR = new Creator<MovieGeneral>() {
        @NonNull
        @Override
        public MovieGeneral createFromParcel(@NonNull Parcel in) {
            return new MovieGeneral(in);
        }

        @NonNull
        @Override
        public MovieGeneral[] newArray(int size) {
            return new MovieGeneral[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {
        out.writeTypedList(movieContentList);
    }
}