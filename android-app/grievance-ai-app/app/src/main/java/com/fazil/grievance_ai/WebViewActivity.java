package com.fazil.grievance_ai;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.fazil.grievance_ai.utilities.ButtonScaleAnimation;
import com.fazil.grievance_ai.utilities.CustomActionBar;
import com.fazil.grievance_ai.utilities.NetworkConnection;
import com.fazil.grievance_ai.utilities.TinyDB;

import java.util.Objects;

public class WebViewActivity extends AppCompatActivity{

    // * Set the Title of the Activity.
    String ACTIVITY_TITLE = "html Editor";
    TextView textViewActivityTitle;
    ImageButton actionBarButton;

    TinyDB tinyDB;
    // * TinyDB Instances.
    String settingsAppTheme = "settings_app_theme";

    private WebView webView;
    String URL = "", HTML = "";
    // Flag to load HTML inside WebView instead of URL.
    boolean webViewLoadHTML = false;

    @SuppressLint("SetJavaScriptEnabled")
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
                setTheme(R.style.DarkTheme);
                break;
        }

        setContentView(R.layout.activity_web_view);

        // * Remove the Dark Mode.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // * Custom ActionBar.
        ActionBar actionBar = this.getSupportActionBar();
        Window window = this.getWindow();
        new CustomActionBar(this).setCustomActionBar(actionBar, window);

        ACTIVITY_TITLE = getIntent().getStringExtra("title");

        // * Set the Title of the Activity.
        textViewActivityTitle = findViewById(R.id.textview_activity_title);
        textViewActivityTitle.setText(ACTIVITY_TITLE);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String isSubscribed = sharedPreferences.getString("subscribed_or_not","0");

        // * Set the Action Bar Button.
        actionBarButton = findViewById(R.id.action_bar_button);
        actionBarButton.setVisibility(View.GONE);

        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new webClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progress) {
                if(progress == 100){
                    // Hide the loading.
                }
                super.onProgressChanged(view, progress);
            }
        });
        // To enable AJAX content.
        webView.getSettings().setDomStorageEnabled(true);
        URL = "http://192.168.18.20:5000/grievance/get.php?userid=1";
        webView.loadUrl(URL);
    }

    public class webClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            super.onPageStarted(view,url,favicon);
        }
        @Override
        public void onPageFinished(WebView view, String url) {}
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            Uri uri = request.getUrl();
            String url = uri.toString();
            view.loadUrl(url);
            return true;
        }
        // For Error Page
        @Override
        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
            if(!new NetworkConnection(WebViewActivity.this).isNetworkConnected()){
                Toast.makeText(getApplicationContext(), "No internet connection.", Toast.LENGTH_LONG).show();
                webView.loadUrl("file:///android_asset/html_editor_error.html");
            }
        }
        // For Error Page
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.cancel();
        }
    }

    @Override
    public void onBackPressed(){
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}