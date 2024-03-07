package com.fazil.grievance_ai;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fazil.grievance_ai.utilities.CustomActionBar;
import com.fazil.grievance_ai.utilities.GradientText;
import com.fazil.grievance_ai.utilities.TinyDB;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 1500;
    TextView versionName;

    TinyDB tinyDB;
    // * TinyDB Instances.
    String settingsAppTheme = "settings_app_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // * To set the theme of the app.
        tinyDB = new TinyDB(this);
        switch (tinyDB.getString(settingsAppTheme)){
            case "light":
                setTheme(R.style.LightTheme);
                break;
            case "dark":
                setTheme(R.style.DarkTheme);
                break;
            default:
                setTheme(R.style.DarkTheme);
                break;
        }

        setContentView(R.layout.activity_splash_screen);

        // * Remove Dark Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // * Custom Action Bar.
        ActionBar actionBar = this.getSupportActionBar();
        Window window = this.getWindow();
        Objects.requireNonNull(actionBar).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);

        // Get the Custom Layout of the ActionBar.
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customActionBar = inflater.inflate(R.layout.custom_action_bar_for_home, null);
        // Set the width and height to fill the entire width with some spaces at the corners.
        actionBar.setCustomView(customActionBar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent));
        actionBar.setElevation(0);

        // Make the ActionBar to fit the width.
        Toolbar parent =(Toolbar) customActionBar.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0,0);// set padding programmatically to 0dp

        // * Set Gradient TextView for the App Name.
        TextView textViewAppName = findViewById(R.id.textview_app_name);
        new GradientText(this).setGradientText(textViewAppName);

        // * Set Gradient TextView for the Title.
        TextView textViewTitle = findViewById(R.id.textview_title);
        new GradientText(this).setGradientText(textViewTitle);

        // * Display the Version Name.
        versionName = findViewById(R.id.version_name);
        versionName.setText("Version: " + BuildConfig.VERSION_NAME);

        // * Open the Next Activity after some time.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, GetAllGrievancesActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                // * New Activity Animation, Current Activity Animation.
                overridePendingTransition(R.anim.intent_enter_animation, R.anim.intent_no_animation);
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}