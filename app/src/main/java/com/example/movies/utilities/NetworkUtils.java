package com.example.movies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class NetworkUtils {

    private final static String API_KEY_PARAM = "api_key";
    private final static String API_KEY = "3ac700fefd9a3f0f4240afe7140d2176";
    private static final String DB_URL =
            "https://api.themoviedb.org/3/movie";

    public static URL buildUrl(String sort_by) {
        Uri builtUri = Uri.parse(DB_URL).buildUpon().
                appendEncodedPath(sort_by).
                appendQueryParameter(API_KEY_PARAM, API_KEY).build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}