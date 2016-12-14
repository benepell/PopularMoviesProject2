package eu.pellerito.popularmoviesproject2.data;

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


import eu.pellerito.popularmoviesproject2.BuildConfig;
import eu.pellerito.popularmoviesproject2.interfaces.MovieService;
import eu.pellerito.popularmoviesproject2.model.MovieReviewResult;
import eu.pellerito.popularmoviesproject2.model.MovieGeneral;
import eu.pellerito.popularmoviesproject2.model.MovieTrailerResult;
import eu.pellerito.popularmoviesproject2.utility.Costants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetchRestManager {

    private static MovieService movieService;
    private static FetchRestManager fetchRestManager;

    private FetchRestManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costants.MOVIE_API_BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofit.create(MovieService.class);
    }

    public static FetchRestManager getInstance() {
        if (fetchRestManager == null) {
            fetchRestManager = new FetchRestManager();
        }
        return fetchRestManager;
    }

    public void getMovieGeneral(String categories, Callback<MovieGeneral> callback) {
        Call<MovieGeneral> call = movieService.getMovieService(categories, BuildConfig.MOVIE_DB_API_KEY);
        call.enqueue(callback);
    }


    public void getTrailer(int movieId, @SuppressWarnings("SameParameterValue")  Callback<MovieTrailerResult> callback) {
        Call<MovieTrailerResult> movieTrailerCall = movieService.getMovieTrailer(movieId, BuildConfig.MOVIE_DB_API_KEY);
        movieTrailerCall.enqueue(callback);
    }

    public void getReview(int movieId, Callback<MovieReviewResult> callback) {
        Call<MovieReviewResult> movieReviewsCall = movieService.getReviews(movieId, BuildConfig.MOVIE_DB_API_KEY);
        movieReviewsCall.enqueue(callback);
    }


}