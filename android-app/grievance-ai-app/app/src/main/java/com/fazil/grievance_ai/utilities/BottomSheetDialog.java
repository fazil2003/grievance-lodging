package com.fazil.grievance_ai.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.fazil.grievance_ai.R;

public class BottomSheetDialog {

    Context context;
    final Dialog bottomSheetDialog;

    public BottomSheetDialog(Context context, int layoutID) {
        this.context = context;
        bottomSheetDialog = new Dialog(context);
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bottomSheetDialog.setContentView(layoutID);
        bottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.BottomSheetDialogAnimation;
        bottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void showBottomSheetDialog(View view) {
        this.bottomSheetDialog.show();
    }

    public void hideBottomSheetDialog(View view) {
        this.bottomSheetDialog.hide();
    }

    public void setCancelable(boolean value){
        this.bottomSheetDialog.setCancelable(value);
    }

    public View findViewById(int layoutID){
        return this.bottomSheetDialog.findViewById(layoutID);
    }

}
