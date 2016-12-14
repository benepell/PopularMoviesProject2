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

package eu.pellerito.popularmoviesproject2.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.pellerito.popularmoviesproject2.R;
import eu.pellerito.popularmoviesproject2.data.FetchTaskControl;
import eu.pellerito.popularmoviesproject2.model.MovieContent;

import static android.widget.ImageView.ScaleType.FIT_XY;

public class UIAdapter extends BaseAdapter {

    @NonNull
    private final Context mContext;
    @NonNull
    @SuppressWarnings("WeakerAccess")
    final
    LayoutInflater inflater;
    @NonNull

    private final List<MovieContent> mMovieContentList;
    private MovieContent movieContent;

    public UIAdapter(@NonNull Context context, @NonNull List<MovieContent> contents) {
        mContext = context;
        mMovieContentList = contents;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mMovieContentList.size();
    }

    @Nullable
    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View view, ViewGroup viewGroup) {

        FetchTaskControl control = new FetchTaskControl(mContext);

        if (mMovieContentList.size() > 0) {
            movieContent = mMovieContentList.get(i);
        }

        ViewHolder viewHolder;

        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.child_fragment_base, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        if (viewHolder.titleTextView != null) {
            viewHolder.titleTextView.setText(movieContent.getTitle());
        }


        if (viewHolder.imageViewPoster != null) {
            viewHolder.imageViewPoster.setAdjustViewBounds(true);
        }
        if (viewHolder.imageViewPoster != null) {
            viewHolder.imageViewPoster.setScaleType(FIT_XY);
        }

        Glide.with(mContext)
                .load(control.getPosterPath(movieContent.getPosterPath()))
                .placeholder(R.drawable.download_in_progress)
                .error(R.drawable.no_image)
                .into(viewHolder.imageViewPoster);


        return view;
    }

    static class ViewHolder {

        @Nullable
        @BindView(R.id.title_child_fragment_base)
        TextView titleTextView;

        @Nullable
        @BindView(R.id.image_child_base_fragment)
        ImageView imageViewPoster;

        ViewHolder(@NonNull View view) {
            ButterKnife.bind(this, view);
        }
    }

}