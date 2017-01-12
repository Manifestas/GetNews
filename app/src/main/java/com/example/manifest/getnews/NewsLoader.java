package com.example.manifest.getnews;

/**
 * Created by tv.unimol on 12.01.2017.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/** Loads a list of news by using an AsyncTask to perform the network request*/
public class NewsLoader extends AsyncTaskLoader<List<News>> {

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        List<News> news = QueryUtils.fetchNewsData();
        return news;
    }
}
