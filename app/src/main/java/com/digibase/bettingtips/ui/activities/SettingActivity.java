package com.digibase.bettingtips.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.digibase.bettingtips.QuickstartPreferences;

import butterknife.ButterKnife;
import butterknife.InjectView;
import digibase.com.bettingtips.R;

public class SettingActivity extends BaseActivity {
    @InjectView(R.id.cbNotific)
    CheckBox cbNotific;
    @InjectView(R.id.cbNotificSound)
    CheckBox cbNotificSound;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.inject(this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean notific = sharedPreferences.getBoolean(QuickstartPreferences.NOTIFICATION, true);
        boolean notificSound = sharedPreferences.getBoolean(QuickstartPreferences.NOTIFICATION_SOUND, true);

        cbNotific.setChecked(notific);
        if(!notific) {
            cbNotificSound.setChecked(false);
            cbNotificSound.setEnabled(false);
        } else {
            cbNotificSound.setChecked(notificSound);
        }

        cbNotific.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbNotificSound.setChecked(true);
                    cbNotificSound.setEnabled(true);

                    sharedPreferences.edit().putBoolean(QuickstartPreferences.NOTIFICATION, true).apply();
                    sharedPreferences.edit().putBoolean(QuickstartPreferences.NOTIFICATION_SOUND, true).apply();
                } else {
                    cbNotificSound.setChecked(false);
                    cbNotificSound.setEnabled(false);

                    sharedPreferences.edit().putBoolean(QuickstartPreferences.NOTIFICATION, false).apply();
                    sharedPreferences.edit().putBoolean(QuickstartPreferences.NOTIFICATION_SOUND, false).apply();
                }
            }
        });

        cbNotificSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    cbNotificSound.setChecked(true);
                    sharedPreferences.edit().putBoolean(QuickstartPreferences.NOTIFICATION_SOUND, true).apply();
                } else {
                    cbNotificSound.setChecked(false);
                    sharedPreferences.edit().putBoolean(QuickstartPreferences.NOTIFICATION_SOUND, false).apply();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);

    }
}