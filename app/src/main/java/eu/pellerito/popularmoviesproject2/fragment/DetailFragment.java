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

package eu.pellerito.popularmoviesproject2.fragment;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.pellerito.popularmoviesproject2.R;
import eu.pellerito.popularmoviesproject2.adapter.LayoutBaseFragment;
import eu.pellerito.popularmoviesproject2.data.Contract;
import eu.pellerito.popularmoviesproject2.data.Db;
import eu.pellerito.popularmoviesproject2.data.FetchRestManager;
import eu.pellerito.popularmoviesproject2.data.FetchTaskControl;
import eu.pellerito.popularmoviesproject2.model.MovieContent;
import eu.pellerito.popularmoviesproject2.model.MovieReview;
import eu.pellerito.popularmoviesproject2.model.MovieReviewResult;
import eu.pellerito.popularmoviesproject2.model.MovieTrailer;
import eu.pellerito.popularmoviesproject2.model.MovieTrailerResult;
import eu.pellerito.popularmoviesproject2.utility.Costants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends LayoutBaseFragment {

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_overview)
    TextView overViewText;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_title)
    TextView titleText;


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.image_youtube)
    ImageView imageYoutube;


    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_poster)
    ImageView poster;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_release_date)
    TextView releaseDateText;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detail_rating)
    RatingBar ratingBar;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.layout_review_wrapper)
    LinearLayout layoutReviewWrapper;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.layout_wrapper)
    LinearLayout layoutWrapper;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.layout_base_trailer)
    LinearLayout layoutBaseTrailer;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.review_title)
    TextView reviewTitle;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.trailer_title)
    TextView trailerTitle;


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.separator_review)
    View separatorReview;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.separator_trailer)
    View separatorTrailer;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.image_favorite)
    ImageView imageFavorite;


    private ArrayList<MovieReview> reviewArrayList;

    private ArrayList<MovieTrailer> trailerArrayList;
    private MovieContent mMovieContent;

    private static String sYoutubeVideo;
    private int mFavoriteId;

    private FetchRestManager fetchRestManager;
    private Context mContext;

    private Db mDb;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        // add method to fragment menu events
        setHasOptionsMenu(true);

        mMovieContent = getActivity().getIntent().getParcelableExtra(getString(R.string.intent_extra_movie_model));

        mContext = getActivity();

        fetchRestManager = FetchRestManager.getInstance();

        FetchTaskControl control = new FetchTaskControl(getActivity());

        String title = control.getTitle(mMovieContent.getTitle());


        if (!(title != null && title.isEmpty())) {

            title = getString(R.string.details_udacity_label_text) + " - " + "\"" + title + "\"";

            getActivity().setTitle(title);

        }


        String moviePoster = mMovieContent.getPosterPath();

        // orientation Portrait
        if (poster != null) {
            Glide.with(getActivity())
                    .load(control.getPosterPath(moviePoster))
                    .placeholder(R.drawable.download_in_progress)
                    .error(R.drawable.no_image)
                    .into(poster);
        }


        imageYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youtubeVideo = getYoutubeVideo();
                if (youtubeVideo != null) {
                    playTrailer(youtubeVideo);
                }
            }
        });


        if (titleText != null) {
            titleText.setText(mMovieContent.getTitle());
        }


        if (overViewText != null) {
            overViewText.setText(control.getOverView(mMovieContent.getOverview()));
        }

        if (releaseDateText != null) {
            releaseDateText.setText(control.getReleaseDateString(mMovieContent.getReleaseDate()));
        }

        if (ratingBar != null) {
            ratingBar.setRating(control.getRating(mMovieContent.getVoteAverage()));
        }


        if (isFavorite()) {

            getTypeImageFavorite(true);

            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    deleteFavorite();
                    getTypeImageFavorite(false);
                }
            });


        } else {

            imageFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setFavoriteId(mMovieContent.getId());

                    storeFavorite();
                    getTypeImageFavorite(true);
                }
            });

        }


    }


    @Override
    public void onCreateOptionsMenu(Menu shareMenu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.detail, shareMenu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_share) {
            shareYoutube(getYoutubeVideo());
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_detail;
    }


    private void fetchTrailer(int id) {

        if (trailerArrayList == null) {
            final Callback<MovieTrailerResult> movieTrailerInfoCallback;
            movieTrailerInfoCallback = new Callback<MovieTrailerResult>()

            {

                @Override
                public void onResponse
                        (Call<MovieTrailerResult> call, @NonNull final Response<MovieTrailerResult> responseTrailer) {
                    if ((responseTrailer.isSuccessful()) && (responseTrailer.body().getResultList().size() > 0)) {

                        trailerArrayList = new ArrayList<>();
                        setYoutubeVideo(responseTrailer.body().getResultList().get(0).getKey());

                        trailerArrayList.addAll(responseTrailer.body().getResultList());
                        viewTrailer(trailerArrayList);


                    }
                }

                @Override
                public void onFailure(Call<MovieTrailerResult> call, Throwable t) {

                }

            };
            fetchRestManager.getTrailer(id, movieTrailerInfoCallback);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mDb != null) {
            mDb.close();
        }

    }

    private void fetchReview(int id) {


        final Callback<MovieReviewResult> movieReviewCallback;
        movieReviewCallback = new Callback<MovieReviewResult>()

        {

            @Override
            public void onResponse
                    (Call<MovieReviewResult> call, @NonNull final Response<MovieReviewResult> responseReview) {
                if ((responseReview.isSuccessful()) && (responseReview.body().getResultList().size() > 0)) {

                    reviewArrayList = new ArrayList<>();
                    reviewArrayList.addAll(responseReview.body().getResultList());
                    readReview(reviewArrayList);


                }
            }

            @Override
            public void onFailure(Call<MovieReviewResult> call, Throwable t) {

            }

        };
        fetchRestManager.getReview(id, movieReviewCallback);
    }


    private void viewTrailer(@NonNull List<MovieTrailer> trailer) {

        if ((mContext != null) && (trailerArrayList != null)) {

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final String[] strKey = new String[1];

            for (int i = 0; i < trailer.size(); i++) {

                strKey[0] = trailer.get(i).getKey();

                View view = layoutInflater.inflate(R.layout.layout_trailer, layoutWrapper, false);

                LinearLayout linearLayoutWrapper = ButterKnife.findById(view, R.id.trailer_wrapper);


                LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                layoutParamsImage.setMargins(8, 8, 8, 8);

                if (trailerTitle != null) {
                    separatorTrailer.setVisibility(View.VISIBLE);
                    trailerTitle.setVisibility(View.VISIBLE);
                }

                final ImageView image = new ImageView(mContext);
                image.setTag(trailer.get(i).getName());
                image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.youtube));

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playTrailer(strKey[0]);
                    }
                });

                LinearLayout.LayoutParams layoutParamsTextView = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParamsTextView.setMargins(8, 8, 8, 8);


                TextView textView = new TextView(mContext);
                textView.setText(trailer.get(i).getName());
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setLines(2);


                linearLayoutWrapper.addView(image, layoutParamsImage);
                linearLayoutWrapper.addView(textView, layoutParamsTextView);

                if (layoutBaseTrailer != null) {
                    layoutBaseTrailer.addView(linearLayoutWrapper);
                }

            }
        }
    }


    private void readReview(@NonNull List<MovieReview> review) {

        if ((mContext != null) && (reviewArrayList != null)) {

            separatorReview.setVisibility(View.VISIBLE);
            reviewTitle.setVisibility(View.VISIBLE);

            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            for (int i = 0; i < review.size(); i++) {

                View reviewView = layoutInflater.inflate(R.layout.layout_review, layoutReviewWrapper, false);
                TextView reviewAuthor = ButterKnife.findById(reviewView, R.id.review_author);
                TextView reviewContent = ButterKnife.findById(reviewView, R.id.review_content);

                reviewAuthor.setText(review.get(i).getAuthor());
                reviewContent.setText(review.get(i).getContent());

                if (layoutReviewWrapper != null) {
                    layoutReviewWrapper.addView(reviewView);
                }


            }
        }
    }

    private void playTrailer(String key) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Costants.YOUTUBE_URI_INTENT + key));
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void shareYoutube(String key) {
        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setText(Uri.parse(Costants.YOUTUBE_URI_INTENT + key).toString());
        if (key != null) {
            startActivity(builder.getIntent());
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(String.valueOf(R.string.movie_save_state), mMovieContent);
        outState.putParcelableArrayList(String.valueOf(R.string.review_save_state), reviewArrayList);
        outState.putParcelableArrayList(String.valueOf(R.string.trailer_save_state), trailerArrayList);

        super.onSaveInstanceState(outState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            reviewArrayList = new ArrayList<>();
            trailerArrayList = new ArrayList<>();

            //Restore the fragment state
            mMovieContent = savedInstanceState.getParcelable(String.valueOf(R.string.movie_save_state));
            reviewArrayList = savedInstanceState.getParcelableArrayList(String.valueOf(R.string.review_save_state));
            trailerArrayList = savedInstanceState.getParcelableArrayList(String.valueOf(R.string.trailer_save_state));


            if (trailerArrayList != null) {
                viewTrailer(trailerArrayList);
            }
            readReview(reviewArrayList);


        } else {
            fetchTrailer(mMovieContent.getId());
            fetchReview(mMovieContent.getId());

        }
    }


    private String getYoutubeVideo() {
        return sYoutubeVideo;
    }

    private void setYoutubeVideo(String youtubeVideo) {
        sYoutubeVideo = youtubeVideo;
    }

    private void setFavoriteId(int favoriteId) {
        mFavoriteId = favoriteId;
    }

    private int getFavoriteId() {
        return mFavoriteId;
    }

    private void storeFavorite() {

        mDb = new Db(mContext);

        ContentValues contentValues = new ContentValues();

        contentValues.put(Contract.Favorites.COLUMN_ID,
                mMovieContent.getId());
        contentValues.put(Contract.Favorites.COLUMN_TITLE,
                mMovieContent.getTitle());
        contentValues.put(Contract.Favorites.COLUMN_POSTER,
                mMovieContent.getPosterPath());
        contentValues.put(Contract.Favorites.COLUMN_SYNOPSIS,
                mMovieContent.getOverview());
        contentValues.put(Contract.Favorites.COLUMN_USER_RATING,
                mMovieContent.getVoteAverage());
        contentValues.put(Contract.Favorites.COLUMN_RELEASE_DATE,
                mMovieContent.getReleaseDate());


        mDb.getWritableDatabase().insert(Contract.Favorites.TABLE_NAME, null, contentValues);
        if (mDb != null){
            mDb.close();

        }

    }

    private boolean isFavorite() {

        mDb = new Db(getActivity());

        String[] columnName = {
                Contract.Favorites.COLUMN_ID,
                Contract.Favorites.COLUMN_TITLE,
                Contract.Favorites.COLUMN_POSTER,
                Contract.Favorites.COLUMN_SYNOPSIS,
                Contract.Favorites.COLUMN_USER_RATING,
                Contract.Favorites.COLUMN_RELEASE_DATE,

        };

        String[] selectionArgs = {String.valueOf(mMovieContent.getId())};

        Cursor cursor = mDb.getReadableDatabase().query(Contract.Favorites.TABLE_NAME,
                columnName, Contract.Favorites.COLUMN_ID + " = ? ",
                selectionArgs, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.close();
            mDb.close();
            return true;
        } else {
            cursor.close();
            mDb.close();
            return false;
        }

    }


    private void deleteFavorite() {

        mDb = new Db(getActivity());

        String[] selectionArgs = {String.valueOf(mMovieContent.getId())};

        mDb.getWritableDatabase().delete(Contract.Favorites.TABLE_NAME, Contract.Favorites.COLUMN_ID + " = ? ", selectionArgs);

        mDb.close();

    }


    private void getTypeImageFavorite(boolean fullImageType) {

        if (fullImageType) {
            Glide.with(mContext)
                    .load(R.drawable.icon_favorites_full)
                    .override(150, 150)
                    .placeholder(R.drawable.download_in_progress)
                    .error(R.drawable.no_image)
                    .into(imageFavorite);

        } else {

            Glide.with(mContext)
                    .load(R.drawable.icon_favorites)
                    .override(150, 150)
                    .placeholder(R.drawable.download_in_progress)
                    .error(R.drawable.no_image)
                    .into(imageFavorite);
        }

    }


}