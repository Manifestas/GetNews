package com.example.manifest.getnews;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by manifest on 12.01.17.
 */

public final class QueryUtils {

    private static final String G_REQUEST_URL = "https://content.guardianapis.com/search";
    private static final String MY_PRIVATE_KEY = "32efa893-2c94-4214-a9da-33f78593921a";
    private static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

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

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //if the URL is null, return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            //if the request was successful(response code 200),
            //then read the input stream and parse the response.
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving JSON results.", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)
            throws IOException {
        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }

    /** Return a list of News objects that has been built up from parsing a JSON response.*/
    private static ArrayList<News> extractNewsFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        ArrayList<News> newsArray = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(newsJSON);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); ++i) {
                JSONObject news = results.getJSONObject(i);
                String title = news.getString("webTitle");
                String date = news.getString("webPublicationDate");
                String section = news.getString("sectionName");
                String url = news.getString("webUrl");
                newsArray.add(new News(title, date, section, url));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the news JSON results", e);
        }
        return newsArray;
    }

    /** Query the Guardian dataset and return a list of News objects.*/
    public static ArrayList<News> fetchNewsData() {
        URL url = createUrl();
        String jsonResponse = null;
        //perform HTTP request to the URL and receive a JSON response back
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problem making the HTTP request.", e);
        }
        //extract relevant fields from the JSON response and create a list of News
        ArrayList<News> newsArray = extractNewsFromJson(jsonResponse);
        return newsArray;
    }
}
