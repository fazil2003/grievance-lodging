package com.fazil.grievance_ai.utilities;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.fazil.grievance_ai.R;

public class ButtonScaleAnimation {
    Animation animationButtonScaleUp, animationButtonScaleDown;
    Context context;
    public ButtonScaleAnimation(Context context) {
        this.context = context;
    }

    public boolean animateButton(View view, MotionEvent motionEvent){

        animationButtonScaleUp = AnimationUtils.loadAnimation(context, R.anim.button_scale_up);
        animationButtonScaleDown = AnimationUtils.loadAnimation(context, R.anim.button_scale_down);

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            view.startAnimation(animationButtonScaleUp);
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            view.startAnimation(animationButtonScaleDown);
        }

        return false;
    }

}
