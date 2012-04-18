
package com.niyaty.leavingrecord;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

    Preference arrivalPreference;
    Preference leavingPreference;
    Preference resttimePreference;

    public static final String ARRIVAL_KEY = "settingsArrivalPreference";
    public static final String LEAVING_KEY = "settingsLeavingPreference";
    public static final String RESTTIME_KEY = "settingsRestTimePreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref);

        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        arrivalPreference = findPreference(ARRIVAL_KEY);
        arrivalPreference.setSummary(pref.getString(ARRIVAL_KEY, "09:00"));
        leavingPreference = findPreference(LEAVING_KEY);
        leavingPreference.setSummary(pref.getString(LEAVING_KEY, "18:00"));
        resttimePreference = findPreference(RESTTIME_KEY);
        resttimePreference.setSummary(pref.getString(RESTTIME_KEY, "01:00"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(
                listener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(
                listener);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(ARRIVAL_KEY)) {
                arrivalPreference.setSummary(sharedPreferences.getString(ARRIVAL_KEY, ""));
            } else if (key.equals(LEAVING_KEY)) {
                leavingPreference.setSummary(sharedPreferences.getString(LEAVING_KEY, ""));
            } else if (key.equals(RESTTIME_KEY)) {
                resttimePreference.setSummary(sharedPreferences.getString(RESTTIME_KEY, ""));
            }
        }
    };

}
