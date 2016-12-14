package eu.pellerito.popularmoviesproject2.data;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import eu.pellerito.popularmoviesproject2.R;
import eu.pellerito.popularmoviesproject2.utility.Costants;

public class FetchTaskControl {

    private final Context mContext;

    public FetchTaskControl(Context context) {
        mContext = context;
    }




    public Uri getPosterPath(String path) {

        Uri builtUri;
        // POSTER_PATH  obtain url error image

        if (Costants.MOVIE_IMAGE_BASE_URL.isEmpty()) {
            // PosterDrop_path VALUES NOT 'null'
            builtUri = Uri.parse(Costants.MOVIE_IMAGE_ERROR).buildUpon()
                    .build();

        } else {

            builtUri = Uri.parse(Costants.MOVIE_IMAGE_BASE_URL).buildUpon()
                    .appendEncodedPath(Costants.MOVIE_IMAGE_WIDTH)
                    .appendEncodedPath(path)
                    .build();

        }


        return builtUri;

    }


    @Nullable
    public String getOverView(@Nullable String overView) {

        String overViewStr = "";

        if (overView != null) {
            overViewStr = overView;
        }

        return overViewStr;

    }


    @Nullable
    public String getReleaseDateString(@Nullable String strReleaseDate) {


        String formattedDate;

        if (strReleaseDate != null && strReleaseDate.isEmpty()) {

            formattedDate = mContext.getString(R.string.movies_details_date_error);

        } else {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Costants.JSON_MOVIES_DATE_FORMAT, Locale.US);

            try {

                Date date = simpleDateFormat.parse(strReleaseDate);
                formattedDate = new SimpleDateFormat(Costants.MOVIE_DETAIL_DATE_FORMAT, Locale.US).format(date);

            } catch (ParseException e) {
                return null;
            }

        }

        return formattedDate;
    }


    @Nullable
    public String getTitle(@Nullable String titleStr) {

        String title;

        if ((titleStr == null) ||
                titleStr.isEmpty() ||
                (titleStr.equalsIgnoreCase("null"))) {
            // Default Value Error
            title = "";

        } else {

            title = titleStr;
        }

        return title;
    }

    public int getRating(double rating) {

        int ratingValue;

        if (Double.isNaN(rating)) {

            // Default Value Error
            ratingValue = 0;

        } else {

            ratingValue = (int) (Math.round(rating) * 0.5);
        }

        return ratingValue;
    }

}
