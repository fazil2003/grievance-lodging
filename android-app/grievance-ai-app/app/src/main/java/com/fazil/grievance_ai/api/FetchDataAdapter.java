package com.fazil.grievance_ai.api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fazil.grievance_ai.R;
import com.fazil.grievance_ai.utilities.CustomPrompt;

import java.util.ArrayList;

public class FetchDataAdapter extends RecyclerView.Adapter<FetchDataAdapter.ViewHolder> {

    // Initialize Variable
    private ArrayList<FetchDataModal> fetchDataModalArrayList;
    private Context context;

    // Create Constructor
    public FetchDataAdapter(Context context, ArrayList<FetchDataModal> fetchDataModalArrayList){
        this.context = context;
        this.fetchDataModalArrayList = fetchDataModalArrayList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        // Initialize View
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewHolder holder, int position) {

        // Initialize Fetch Data Modal
        FetchDataModal fetchDataModal = fetchDataModalArrayList.get(position);

        // Set the data on the views.
        holder.itemID.setText(String.valueOf(fetchDataModal.getItemID()));
        holder.itemTitle.setText(fetchDataModal.getItemTitle());
        holder.itemDescription.setText(fetchDataModal.getItemDescription());
        holder.itemDate.setText(fetchDataModal.getItemDate());

        // * Set the OnTouch Listeners.
        // holder.buttonDeleteProject.setOnTouchListener((view, motionEvent) -> new ButtonScaleAnimation(context).animateButton(view, motionEvent));

    }

    @Override
    public int getItemCount() {
        return fetchDataModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // * To Change the Background of the Layouts.
        LinearLayout layoutBackground;
        TextView itemID, itemTitle, itemDescription, itemDate;
        Button buttonViewProject, buttonUnlockProject, buttonDeleteProject;
        ImageView itemIcon;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            // Initialize Views
            itemID = itemView.findViewById(R.id.item_id);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemDate = itemView.findViewById(R.id.item_date);

            itemIcon = itemView.findViewById(R.id.item_icon);

            buttonViewProject = itemView.findViewById(R.id.button_view_project);
            buttonUnlockProject = itemView.findViewById(R.id.button_unlock_project);
            buttonDeleteProject = itemView.findViewById(R.id.button_delete_project);

            layoutBackground = itemView.findViewById(R.id.layout_background);
        }
    }
}
