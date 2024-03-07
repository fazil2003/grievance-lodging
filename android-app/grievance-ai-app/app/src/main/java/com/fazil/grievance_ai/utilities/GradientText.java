package com.fazil.grievance_ai.utilities;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.widget.TextView;

import com.fazil.grievance_ai.R;

public class GradientText {

    Context context;

    public GradientText(Context context) {
        this.context = context;
    }

    public void setGradientText(TextView textView){
        Shader textShader = new LinearGradient(0,0, 60, 20,
                new int[]{context.getColor(R.color.dodgerblue),context.getColor(R.color.deepskyblue)},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
    }
}
