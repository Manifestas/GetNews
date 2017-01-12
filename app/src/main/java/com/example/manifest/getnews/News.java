package com.example.manifest.getnews;


/**
 * Created by tv.unimol on 12.01.2017.
 */

public class News {

    private String mTitle;
    private String mDate;
    private String mSection;
    private String mURL;

    public News(String title, String date, String section, String URL) {
        mTitle = title;
        mDate = date;
        mSection = section;
        mURL = URL;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getSection() {
        return mSection;
    }

    public String getURL() {
        return mURL;
    }
}
