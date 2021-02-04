package com.dicoding.listviewparcel.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.dicoding.listviewparcel.R;
import com.dicoding.listviewparcel.alarm.AlarmReceiver;

public class NotifPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private String ALARM;
    private SwitchPreference isAlarmPreference;
    private AlarmReceiver alarmReceiver;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        alarmReceiver = new AlarmReceiver();

        init();
        setSummaries();
    }

    private void init(){
        ALARM = getResources().getString(R.string.key_notication);
        isAlarmPreference =  findPreference(ALARM);
    }

    private void setSummaries(){
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        isAlarmPreference.setChecked(sh.getBoolean(ALARM, false));
        isAlarmPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isChecked = (boolean) newValue;
                if (isChecked){
                    //set waktu reminder
                    alarmReceiver.setRepeatingAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING, "09:00", getString(R.string.alarm_repeating));
                }
                else{
                    alarmReceiver.cancelAlarm(getActivity(), AlarmReceiver.TYPE_REPEATING);
                }
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(ALARM)){
            isAlarmPreference.setChecked(sharedPreferences.getBoolean(ALARM, false));
        }
    }
}
