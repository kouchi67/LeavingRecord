
package com.niyaty.leavingrecord;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

    Preference preference;

    public String ARRIVAL_KEY = "settingsArrivalPreference";
    public String LEAVING_KEY = "settingsLeavingPreference";
    public String RESTTIME_KEY = "settingsRestTimePreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.pref);
//        String[] searchbooksKeys = getResources().getStringArray(R.array.search_books);
//        String[] searchbooksValues = getResources().getStringArray(R.array.search_books_value);
        preference = findPreference(ARRIVAL_KEY);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            if(key.equals()) {
//                searchbooksPref.setSummary(searchbooksMap.get(sharedPreferences.getString(key, "Books")));
//            }
        }
    };

}
