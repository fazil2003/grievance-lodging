package com.fazil.grievance_ai.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.InsetDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.fazil.grievance_ai.R;

// * Used to show the custom toast message.
public class CustomToast {

    int delayMillis = 1200;

    // * To calculate the toast length.
    public enum toastLength {SHORT, MEDIUM, LONG}

    Context context;
    public CustomToast(Context context) {
        this.context = context;
    }

    /**
     @param delayType
     0 - SHORT -> 1200
     1 - MEDIUM -> 2000
     2 - LONG -> 3000
    */

    public void showToast(String message, toastLength delayType){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.custom_toast_message, null);
        TextView toastTextView = promptView.findViewById(R.id.toast_textview);
        toastTextView.setText(message);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(promptView);
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();

        // Remove the background of AlertDialog.
        InsetDrawable background = (InsetDrawable) alertDialog.getWindow().getDecorView().getBackground();
        background.setAlpha(0);

        // Set the AlertDialog at the bottom of screen.
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);

        alertDialog.show();

        // Hide the AlertDialog after some seconds
        final Handler handler  = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        };
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        if(delayType == toastLength.SHORT){
            delayMillis = 1200;
        }
        else if (delayType == toastLength.MEDIUM){
            delayMillis = 2000;
        }
        else{
            delayMillis = 3000;
        }

        // Time limit to hide the AlertDialog.
        handler.postDelayed(runnable, delayMillis);

    }

}
