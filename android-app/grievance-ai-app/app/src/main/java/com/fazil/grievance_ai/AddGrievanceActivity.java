package com.fazil.grievance_ai;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fazil.grievance_ai.utilities.CustomActionBar;
import com.fazil.grievance_ai.utilities.TinyDB;

public class AddGrievanceActivity extends AppCompatActivity {

    // * Set the Title of the Activity.
    String ACTIVITY_TITLE = "Add Grievance";
    TextView textViewActivityTitle;
    ImageButton actionBarButton;

    EditText editTextProjectName, editTextProjectDescription;
    AutoCompleteTextView spinnerProjectLanguage;
    String projectName, projectDescription, projectLanguage;
    Button buttonCreateProject, buttonUpdateProject;
    String projectID;

    Button optionPHP, optionHTML, optionPython;
    String selectedProjectLanguage;

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

        setContentView(R.layout.activity_add_grievance);

        // * Remove the Dark Mode.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // * Custom ActionBar.
        ActionBar actionBar = this.getSupportActionBar();
        Window window = this.getWindow();
        new CustomActionBar(this).setCustomActionBar(actionBar, window);

        // * Set the Title of the Activity.
        textViewActivityTitle = findViewById(R.id.textview_activity_title);
        textViewActivityTitle.setText(ACTIVITY_TITLE);

        actionBarButton = findViewById(R.id.action_bar_button);
        actionBarButton.setVisibility(View.GONE);

        editTextProjectName = findViewById(R.id.edittext_project_name);
        editTextProjectDescription = findViewById(R.id.edittext_project_description);
        buttonCreateProject = findViewById(R.id.button_create_project);
    }
}