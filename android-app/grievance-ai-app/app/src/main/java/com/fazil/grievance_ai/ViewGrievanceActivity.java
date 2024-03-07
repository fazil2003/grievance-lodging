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
import android.widget.ImageButton;
import android.widget.TextView;

import com.fazil.grievance_ai.utilities.ButtonScaleAnimation;
import com.fazil.grievance_ai.utilities.CustomActionBar;
import com.fazil.grievance_ai.utilities.TinyDB;

import org.w3c.dom.Text;

public class ViewGrievanceActivity extends AppCompatActivity {

    // * Set the Title of the Activity.
    String ACTIVITY_TITLE = "Grievance";
    TextView textViewActivityTitle;
    ImageButton actionBarButton;

    TinyDB tinyDB;
    // * TinyDB Instances.
    String settingsAppTheme = "settings_app_theme";

    String title, description, department_1, department_2, department_3;
    TextView textViewTitle, textViewDescription;
    TextView textViewDepartmentOne, textViewDepartmentTwo, textViewDepartmentThree;

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
                setTheme(R.style.LightTheme);
                break;
        }

        setContentView(R.layout.activity_view_grievance);

        // * Remove the Dark Mode.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // * Custom ActionBar.
        ActionBar actionBar = this.getSupportActionBar();
        Window window = this.getWindow();
        new CustomActionBar(this).setCustomActionBar(actionBar, window);

        // * Set the Title of the Activity.
        textViewActivityTitle = findViewById(R.id.textview_activity_title);
        textViewActivityTitle.setText(ACTIVITY_TITLE);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String isSubscribed = sharedPreferences.getString("subscribed_or_not","0");

        // * Set the Action Bar Button.
        actionBarButton = findViewById(R.id.action_bar_button);
        if(isSubscribed.equals("1")){
            actionBarButton.setVisibility(View.GONE);
        }

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");

        department_1 = getIntent().getStringExtra("department_1");
        department_2 = getIntent().getStringExtra("department_2");
        department_3 = getIntent().getStringExtra("department_3");

        textViewTitle = findViewById(R.id.textview_title);
        textViewDescription = findViewById(R.id.textview_description);

        textViewDepartmentOne = findViewById(R.id.textview_department_1);
        textViewDepartmentTwo = findViewById(R.id.textview_department_2);
        textViewDepartmentThree = findViewById(R.id.textview_department_3);

        textViewTitle.setText(title);
        textViewDescription.setText(description);
        textViewDepartmentOne.setText(department_1);
        textViewDepartmentTwo.setText(department_2);
        textViewDepartmentThree.setText(department_3);
    }
}