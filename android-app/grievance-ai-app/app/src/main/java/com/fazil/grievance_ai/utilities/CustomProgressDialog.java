package com.fazil.grievance_ai.utilities;

import android.content.Context;
import android.graphics.Color;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

public class CustomProgressDialog {

    SimpleArcDialog simpleArcDialog;

    Context context;
    public CustomProgressDialog(Context context) {
        this.context = context;
    }

    public void showProgressDialog(){
        // * Create a Dialog.
        simpleArcDialog = new SimpleArcDialog(context);
        ArcConfiguration arcConfiguration = new ArcConfiguration(context);
        arcConfiguration.setLoaderStyle(SimpleArcLoader.STYLE.COMPLETE_ARC);
        arcConfiguration.setText("Please wait...");
        arcConfiguration.setTextSize(18);
        arcConfiguration.setArcMargin(6);
        arcConfiguration.setTextColor(Color.BLACK);
        simpleArcDialog.setConfiguration(arcConfiguration);
        simpleArcDialog.setCancelable(false);
        // * It will not hide even if the user accidentally clicked the screen outside the progress dialog.
        simpleArcDialog.setCanceledOnTouchOutside(false);
        simpleArcDialog.show();
    }

    public void hideProgressDialog(){
        simpleArcDialog.dismiss();
    }

}
