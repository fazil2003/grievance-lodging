package com.fazil.grievance_ai.utilities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LocalFileOperations {

    Activity activity;

    public static int PICK_FILE = 1, SAVE_FILE = 2;

    public LocalFileOperations(Activity activity) {
        this.activity = activity;
    }

    // * Function to write the File in External Storage.
    public void writeFileExternalStorage(View view) {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        activity.startActivityForResult(Intent.createChooser(intent, "Choose directory"), SAVE_FILE);

    }

    // * Function to read the File in External Storage.
    public void readFileExternalStorage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // * 'text/plain' is for normal one, 'text/*' doesn't support opening of php files.
        intent.setType("*/*");
        activity.startActivityForResult(intent, PICK_FILE);
    }

    // * Function to read the contents from the selected file.
    public String readSelectedTextFile(Uri uri) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try
        {
            reader = new BufferedReader(new InputStreamReader(activity.getContentResolver().openInputStream(uri)));
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                builder.append(line).append("\r\n");
            }
            reader.close();
        }
        catch (IOException e) {e.printStackTrace();}
        return builder.toString();
    }

}
