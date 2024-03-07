package com.fazil.grievance_ai.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.fazil.grievance_ai.R;

import java.util.Objects;

public class CustomActionBar {

    Activity context;

    public CustomActionBar(Activity context) {
        this.context = context;
    }

    ImageButton buttonGoBack;

    public void setCustomActionBar(ActionBar actionBar, Window window){
        // Enable the Custom ActionBar.
//        Objects.requireNonNull(actionBar).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setDisplayShowCustomEnabled(true);
//        // Get the Custom Layout of the ActionBar.
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View customActionBar = inflater.inflate(R.layout.custom_action_bar, null);
//        // Set the width and height to fill the entire width with some spaces at the corners.
//        actionBar.setCustomView(customActionBar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent));
//        actionBar.setElevation(0);
//
//        // Remove the StatusBar.
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
//        // Make the ActionBar to fit the width.
//        Toolbar parent =(Toolbar) customActionBar.getParent();//first get parent toolbar of current action bar
//        parent.setContentInsetsAbsolute(0,0);// set padding programmatically to 0dp

        Objects.requireNonNull(actionBar).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);

        // Get the Custom Layout of the ActionBar.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customActionBar = inflater.inflate(R.layout.custom_action_bar, null);
        // Set the width and height to fill the entire width with some spaces at the corners.
        actionBar.setCustomView(customActionBar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent));
        actionBar.setElevation(0);

        // Make the ActionBar to fit the width.
        Toolbar parent =(Toolbar) customActionBar.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0,0);// set padding programmatically to 0dp

        // * Set Gradient TextView for the App Name.
//        TextView textViewAppName = context.findViewById(R.id.textview_app_name);
//        new GradientText(context).setGradientText(textViewAppName);

        // * Set the Back Button on the Action Bar.
        buttonGoBack = context.findViewById(R.id.button_go_back);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.onBackPressed();
            }
        });

    }

}
