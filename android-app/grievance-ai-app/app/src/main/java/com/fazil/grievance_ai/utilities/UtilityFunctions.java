package com.fazil.grievance_ai.utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fazil.grievance_ai.R;

// A Class for using common functions.
public class UtilityFunctions {

    Activity activity;

    public UtilityFunctions(Activity activity) {
        this.activity = activity;
    }

    // To load the Activity with animations.
    public void moveToActivity(Activity activityToStart) {
        Intent intent = new Intent(activity, activityToStart.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        // * (New Activity Animation, Current Activity Animation).
        activity.overridePendingTransition(R.anim.intent_enter_animation, R.anim.intent_no_animation);
    }

    // To show the animation to the ImageViews.
    public void animateIcons(ImageView[] imageViews){
        // * Animation Effect in the Icons in CardViews.
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView imageView : imageViews) {
                    YoYo.with(Techniques.Tada).duration(1000).playOn(imageView);
                }
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

}
