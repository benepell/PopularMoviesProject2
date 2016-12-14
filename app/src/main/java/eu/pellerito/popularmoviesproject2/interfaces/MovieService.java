package eu.pellerito.popularmoviesproject2.interfaces;

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


import android.support.annotation.NonNull;

import eu.pellerito.popularmoviesproject2.model.MovieGeneral;
import eu.pellerito.popularmoviesproject2.model.MovieReviewResult;
import eu.pellerito.popularmoviesproject2.model.MovieTrailerResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
    @NonNull
    @GET("movie/{categories}")
    Call<MovieGeneral> getMovieService(@Path("categories") String categories, @SuppressWarnings("SameParameterValue") @Query("api_key") String apiKey);

    @NonNull
    @GET("movie/{id}/videos")
    Call<MovieTrailerResult> getMovieTrailer(@Path("id") int id, @SuppressWarnings("SameParameterValue") @Query("api_key") String apiKey);

    @NonNull
    @GET("movie/{id}/reviews")
    Call<MovieReviewResult> getReviews(@Path("id") int id, @SuppressWarnings("SameParameterValue") @Query("api_key") String apiKey);


}
