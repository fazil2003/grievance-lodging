package com.fazil.grievance_ai.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

// A Class for checking the network connection.
public class NetworkConnection {

    Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }

    // For Checking the Network Connection
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
