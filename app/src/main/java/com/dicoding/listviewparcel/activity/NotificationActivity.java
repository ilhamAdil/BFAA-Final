package com.dicoding.listviewparcel.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.listviewparcel.R;
import com.dicoding.listviewparcel.fragment.NotifPreferenceFragment;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new NotifPreferenceFragment()).commit();
    }
}