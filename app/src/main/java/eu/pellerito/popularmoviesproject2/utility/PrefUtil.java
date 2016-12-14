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

package eu.pellerito.popularmoviesproject2.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class PrefUtil {

    private final SharedPreferences preferences;

    public PrefUtil(@NonNull Context context, String namePreferences) {

        preferences = context.getSharedPreferences(namePreferences, MODE_PRIVATE);

    }


    public void putString(String keyStr, String valueStr) {

        preferences.edit().putString(keyStr, valueStr).apply();
    }

    @Nullable
    public String getString(@Nullable String keyStr) {

        return ((keyStr == null) || (keyStr.isEmpty())) ? Costants.MOVIE_PREF_KEY_DEFAULT : preferences.getString(keyStr, "");

    }


} 