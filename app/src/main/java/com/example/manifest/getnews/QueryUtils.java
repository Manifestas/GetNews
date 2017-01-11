package com.example.manifest.getnews;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by manifest on 12.01.17.
 */

public final class QueryUtils {

    private static final String G_REQUEST_URL = "https://content.guardianapis.com/search";
    private static final String MY_PRIVATE_KEY = "32efa893-2c94-4214-a9da-33f78593921a";
    private static final String TAG = QueryUtils.class.getSimpleName();

    private static URL createUrl() {
        Uri baseUri = Uri.parse(G_REQUEST_URL);
        Uri.Builder builder = baseUri.buildUpon();
        builder.appendQueryParameter("api-key", MY_PRIVATE_KEY);
        builder.appendQueryParameter("format", "json");
        builder.appendQueryParameter("page-size", "20");
        builder.appendQueryParameter("page", "1");
        builder.appendQueryParameter("order-by", "newest");
        builder.appendQueryParameter("q", "russia");

        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Problem building the URL ", e);
        }
        return url;

    }
}
