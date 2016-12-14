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

package eu.pellerito.popularmoviesproject2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.pellerito.popularmoviesproject2.R;
import eu.pellerito.popularmoviesproject2.fragment.DetailFragment;
import eu.pellerito.popularmoviesproject2.utility.Costants;


public class DetailActivity extends AppCompatActivity {

    private DetailFragment detailFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, detailFragment)
                    .commit();
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_container).getFragmentManager()
                .getFragment(savedInstanceState, Costants.BUNDLE_KEY_FRAGMENT_DETAIL);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_container, detailFragment)
                .commit();

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //save fragment
        getSupportFragmentManager().findFragmentById(R.id.detail_container).getFragmentManager().putFragment(outState, Costants.BUNDLE_KEY_FRAGMENT_DETAIL, detailFragment);

    }

}
