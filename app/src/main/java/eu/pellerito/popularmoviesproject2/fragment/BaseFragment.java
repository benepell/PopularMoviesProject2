package eu.pellerito.popularmoviesproject2.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import eu.pellerito.popularmoviesproject2.R;
import eu.pellerito.popularmoviesproject2.activity.DetailActivity;
import eu.pellerito.popularmoviesproject2.adapter.LayoutBaseFragment;
import eu.pellerito.popularmoviesproject2.adapter.UIAdapter;
import eu.pellerito.popularmoviesproject2.data.Contract;
import eu.pellerito.popularmoviesproject2.data.Db;
import eu.pellerito.popularmoviesproject2.data.FetchRestManager;
import eu.pellerito.popularmoviesproject2.model.MovieContent;
import eu.pellerito.popularmoviesproject2.model.MovieGeneral;
import eu.pellerito.popularmoviesproject2.utility.Costants;
import eu.pellerito.popularmoviesproject2.utility.NetworkState;
import eu.pellerito.popularmoviesproject2.utility.PrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseFragment extends LayoutBaseFragment {

    @Nullable
    private ArrayList<MovieContent> movieContentArrayList;

    private FetchRestManager fetchRestManager;

    @Nullable
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.my_movie_grid)
    protected GridView gridView;

    private boolean isGridViewDone;
    private UIAdapter restoreUIAdapter;

    private Db mDb;

    private Context mContext;
    @Nullable
    private String mOrder;

    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        // add method to fragment menu events
        setHasOptionsMenu(true);


        // Read SharedPreference and initialize value
        mOrder = new PrefUtil(getActivity(), getString(R.string.pref_key_sort))
                .getString(getString(R.string.pref_key_sort));

        // initialize default
        if ((mOrder != null) && mOrder.isEmpty()) {
            mOrder = Costants.SORTBY_DEFAULT_PARAM;
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_sort_popular:

                mOrder = String.valueOf(Costants.POPULAR);

                updateUI();

                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                return true;

            case R.id.menu_sort_top_rated:

                mOrder = String.valueOf(Costants.TOP_RATED);

                updateUI();

                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                return true;

            case R.id.menu_sort_favorites:

                mOrder = String.valueOf(Costants.FAVORITES);

                UpdateFavorite();

                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (mOrder != null) {
            switch (mOrder) {

                case Costants.TOP_RATED:
                    menu.findItem(R.id.menu_sort_top_rated).setChecked(true);
                    break;
                case Costants.FAVORITES:
                    menu.findItem(R.id.menu_sort_favorites).setChecked(true);
                    break;
                case Costants.POPULAR:
                default:
                    menu.findItem(R.id.menu_sort_popular).setChecked(true);
                    break;

            }
        }


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fetchRestManager = FetchRestManager.getInstance();
        movieContentArrayList = new ArrayList<>();

        assert gridView != null;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detailActivity = new Intent(mContext, DetailActivity.class)
                        .putExtra("MovieContent", movieContentArrayList.get(i));
                startActivity(detailActivity);

            }
        });


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (savedInstanceState != null) {
            //Restore the fragment state
            movieContentArrayList = savedInstanceState.getParcelableArrayList(String.valueOf(R.string.movie_save_state));
            if (movieContentArrayList != null) {
                setGridView(movieContentArrayList);
            }
        } else {
            if (mOrder.equalsIgnoreCase(Costants.FAVORITES)) {
                //  fetchDatabase();
            } else {
                fetchNetwork(mOrder);
            }

        }

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_base;
    }


    private void fetchNetwork(final String moviesCategories) {
        Callback<MovieGeneral> moviesGeneralCallback = new Callback<MovieGeneral>() {

            @Override
            public void onResponse(Call<MovieGeneral> call, @NonNull Response<MovieGeneral> responseContent) {
                if (responseContent.isSuccessful()) {

                    if (movieContentArrayList != null) {
                        movieContentArrayList.addAll(responseContent.body().movieContentList);
                    }

                    if (isGridViewDone) {
                        restoreUIAdapter.notifyDataSetChanged();
                    } else {
                        if (movieContentArrayList != null) {
                            setGridView(movieContentArrayList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieGeneral> call, Throwable t) {

                if (new NetworkState(mContext).isOffLine()) {

                    Toast.makeText(mContext, R.string.network_state_not_connected, Toast.LENGTH_LONG).show();
                }
            }

        };
        fetchRestManager.getMovieGeneral(moviesCategories, moviesGeneralCallback);
    }

    private void setGridView(@NonNull final List<MovieContent> movieContentArrayList) {
        restoreUIAdapter = new UIAdapter(mContext, movieContentArrayList);
        if (gridView != null) {
            gridView.setAdapter(restoreUIAdapter);
        }
        isGridViewDone = true;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(String.valueOf(R.string.movie_save_state), movieContentArrayList);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mDb != null) {
            mDb.close();
        }

        // save SharedPreferences
        new PrefUtil(getActivity(), getString(R.string.pref_key_sort))
                .putString(getString(R.string.pref_key_sort), mOrder);

    }

    private void updateUI() {

        if ((movieContentArrayList != null ? movieContentArrayList.size() : 0) > 0) {
            if (movieContentArrayList != null) {
                movieContentArrayList.clear();
            }
        }

        // favorites work offline
        if ((mOrder != null) && (mOrder.equalsIgnoreCase(Costants.FAVORITES))) {

            restoreUIAdapter.notifyDataSetChanged();
            Toast.makeText(mContext, "offline", Toast.LENGTH_LONG).show();
        } else {
            fetchNetwork(mOrder);
        }

        if (movieContentArrayList.size() > 0) {
            restoreUIAdapter.notifyDataSetChanged();
        }


    }

    private void UpdateFavorite() {

        if ((movieContentArrayList != null ? movieContentArrayList.size() : 0) > 0) {
            if (movieContentArrayList != null) {
                movieContentArrayList.clear();
            }
        }

        fetchDatabase();

        if (movieContentArrayList.size() > 0) {
            restoreUIAdapter.notifyDataSetChanged();
        }

    }


    private void fetchDatabase() {

        mDb = new Db(getActivity());

        String[] columnName = {
                Contract.Favorites.COLUMN_ID,
                Contract.Favorites.COLUMN_TITLE,
                Contract.Favorites.COLUMN_POSTER,
                Contract.Favorites.COLUMN_SYNOPSIS,
                Contract.Favorites.COLUMN_USER_RATING,
                Contract.Favorites.COLUMN_RELEASE_DATE,

        };

        Cursor cursor = mDb.getReadableDatabase().query(Contract.Favorites.TABLE_NAME,
                columnName, null, null, null, null, null);


        MovieContent movieContent;
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    movieContent = new MovieContent();
                    movieContent.setId(cursor.getInt(cursor.getColumnIndex(Contract.Favorites.COLUMN_ID)));
                    movieContent.setTitle(cursor.getString(cursor.getColumnIndex(Contract.Favorites.COLUMN_TITLE)));
                    movieContent.setPosterPath(cursor.getString(cursor.getColumnIndex(Contract.Favorites.COLUMN_POSTER)));
                    movieContent.setOverview(cursor.getString(cursor.getColumnIndex(Contract.Favorites.COLUMN_SYNOPSIS)));
                    movieContent.setVoteAverage(cursor.getFloat(cursor.getColumnIndex(Contract.Favorites.COLUMN_USER_RATING)));
                    movieContent.setReleaseDate(cursor.getString(cursor.getColumnIndex(Contract.Favorites.COLUMN_RELEASE_DATE)));
                    assert movieContentArrayList != null;
                    movieContentArrayList.add(movieContent);


                } while (cursor.moveToNext());
            }

            cursor.close();


            setGridView(movieContentArrayList);
            if (mDb != null) {
                mDb.close();
            }
        }

    }
}



