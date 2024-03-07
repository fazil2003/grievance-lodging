package com.fazil.grievance_ai.utilities;

import android.content.Context;
import android.graphics.drawable.InsetDrawable;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

// * Used to show the custom prompt message.
public class CustomPrompt {

    Context context;
    public CustomPrompt(Context context) {
        this.context = context;
    }

    public AlertDialog showPrompt(View promptView){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptView);
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();

        // * Remove the background of AlertDialog.
        InsetDrawable background = (InsetDrawable) alertDialog.getWindow().getDecorView().getBackground();
        background.setAlpha(0);

        alertDialog.show();

        return alertDialog;

    }

}
