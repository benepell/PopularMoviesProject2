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

/**
 * Defines several constants used in application
 */

package eu.pellerito.popularmoviesproject2.utility;

public class Costants {

    private Costants() {
    } // use private Constructor to prevents instantiation of class

    /**
     * SHARED PREFERENCES -
     */

    public static final String MOVIE_PREF_KEY_DEFAULT = "KEY";

    public static final String MOVIE_OBJECT = "movie_object";


    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String FAVORITES = "favorites";


    public static final String SORTBY_DEFAULT_PARAM = "popular";

    /**
     * Costants URI
     * Possible parameters are avaiable at
     * https://www.themoviedb.org/documentation/api/discover
     */

    public static final String MOVIE_API_BASE_URL = "https://api.themoviedb.org/3/";

    public static final String JSON_MOVIES_ID = "id";
    public static final String JSON_MOVIES_TITLE = "title";
    public static final String JSON_MOVIES_POSTER_PATH = "poster_path";
    public static final String JSON_MOVIES_BACKDROP_PATH = "backdrop_path";
    public static final String JSON_MOVIES_OVERVIEW = "overview";
    public static final String JSON_MOVIES_VOTE_AVERAGE = "vote_average";
    public static final String JSON_MOVIES_VOTE_COUNT = "vote_count";
    public static final String JSON_MOVIES_POPULARITY = "popularity";

    public static final String JSON_MOVIES_RELEASE_DATE = "release_date";

    public static final String JSON_MOVIES_DATE_FORMAT = "yyyy-MM-dd";
    public static final String MOVIE_DETAIL_DATE_FORMAT = "MMM dd, yyyy";

    public static final String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String MOVIE_IMAGE_ERROR = "http://image.tmdb.org/error/error.jpg";
    public static final String MOVIE_IMAGE_WIDTH = "w500";

    public static final int MOVIE_IMAGE_GRID_VIEW_WIDTH = 500;
    public static final int MOVIE_IMAGE_GRID_VIEW_HEIGHT = 750;


    public static final String YOUTUBE_URI_INTENT = "http://www.youtube.com/watch?v=";

    public static final String BUNDLE_KEY_FRAGMENT_BASE = "fragment_base";
    public static final String BUNDLE_KEY_FRAGMENT_DETAIL = "fragment_detail";
}
