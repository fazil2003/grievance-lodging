package com.fazil.grievance_ai;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fazil.grievance_ai.R;
import com.fazil.grievance_ai.api.FetchDataAdapter;
import com.fazil.grievance_ai.api.FetchDataInterface;
import com.fazil.grievance_ai.api.FetchDataModal;
import com.fazil.grievance_ai.utilities.ButtonScaleAnimation;
import com.fazil.grievance_ai.utilities.Constants;
import com.fazil.grievance_ai.utilities.CustomActionBar;
import com.fazil.grievance_ai.utilities.NetworkConnection;
import com.fazil.grievance_ai.utilities.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GetAllGrievancesActivity extends AppCompatActivity {

    // Initialize Variable
    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<FetchDataModal> dataArrayList = new ArrayList<>();
    FetchDataAdapter adapter;

    // Initialize Query Parameters.
    int start = 0, limit = 100;
    String query = "", userID = "1";

    TextView textViewMaximumProjects, textViewNoInternetConnection;
    Button buttonAddProject;

    // * Set the Title of the Activity.
    String ACTIVITY_TITLE = "Grievances";
    TextView textViewActivityTitle;

    ImageButton actionBarButton;

    TinyDB tinyDB;
    // * TinyDB Instances.
    String settingsAppTheme = "settings_app_theme";

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

        setContentView(R.layout.activity_get_all_grievances);

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
        actionBarButton.setVisibility(View.GONE);

        // Assign variable
        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        // Initialize Adapter
        adapter = new FetchDataAdapter(GetAllGrievancesActivity.this, dataArrayList);

        // Set Layout Manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Set Adapter
        recyclerView.setAdapter(adapter);

        if(new NetworkConnection(this).isNetworkConnected()){
            // Create get data method
            getData(start, limit, userID);
        }
        else{
            progressBar.setVisibility(View.GONE);
            textViewNoInternetConnection.setVisibility(View.VISIBLE);
        }

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Check Condition
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    // When reach last item position
                    // Increase page size
                    start += limit;

                    // Show Progress bar
                    progressBar.setVisibility(View.VISIBLE);

                    // Call method
                    getData(start, limit, userID);

                    // Toast.makeText(MainActivity.this, String.valueOf(page), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // API Call to get the data.
    private void getData(int start, int limit, String userID) {

        // API -> v1
        String api = getResources().getString(R.string.api_v1);
        api = api + "grievance/";

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        // Create main interface
        FetchDataInterface fetchDataInterface = retrofit.create(FetchDataInterface.class);

        // Initialize Call
        Call<String> call = fetchDataInterface.STRING_CALL(start, limit, Integer.parseInt(userID));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Check condition
                if(response.isSuccessful() && response.body() != null){
                    // When response is successful and not empty
                    // Hide progress bar
                    progressBar.setVisibility(View.GONE);
                    try {
                        // Initialize JSON array
                        JSONArray jsonArray = new JSONArray(response.body());
                        // Parse Json array
                        parseResult(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {}
        });
    }

    // Process the API response.
    private void parseResult(JSONArray jsonArray) {
        // Use For Loop
        for(int i=0; i<jsonArray.length(); i++){
            try {
                // Initialize json object
                JSONObject object = jsonArray.getJSONObject(i);

                // Initialize Main Data
                FetchDataModal data = new FetchDataModal();

                // Setting the response data.
                data.setItemID(Integer.parseInt(object.getString("grievanceID")));
                data.setItemDate(object.getString("grievanceDate"));
                data.setItemTitle(object.getString("grievanceTitle"));
                data.setItemDescription(object.getString("grievanceDescription"));

                data.setGrievanceDepartmentOne(object.getString("grievanceDepartmentOne"));
                data.setGrievanceDepartmentTwo(object.getString("grievanceDepartmentTwo"));
                data.setGrievanceDepartmentThree(object.getString("grievanceDepartmentThree"));

                // Add data in array list
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Initialize Adapter
            adapter = new FetchDataAdapter(GetAllGrievancesActivity.this, dataArrayList);
            // Set adapter
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.intent_no_animation, R.anim.intent_exit_animation);
    }
}