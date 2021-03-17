package edu.quinnipiac.gadacy.foodcalories;

/*
Thomas Gadacy
Professor Ruby ElKharboutly
Assignment 2 Part 2
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

//This Activity allows the user to switch the theme of the app between light and dark mode
public class SettingsActivity extends AppCompatActivity {

    public Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        aSwitch = findViewById(R.id.theme_switch);
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        aSwitch.setChecked(sharedPreferences.getBoolean("value", false));
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            aSwitch.setChecked(true);
        } else {
            aSwitch.setChecked(false);
        }

    }

    public void switchTheme(View view) {
        if (aSwitch.isChecked()) {
            System.out.println("isChecked is true!");
            SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
            editor.putBoolean("value", true);
            editor.apply();
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
            editor.putBoolean("value", false);
            editor.apply();
            aSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}