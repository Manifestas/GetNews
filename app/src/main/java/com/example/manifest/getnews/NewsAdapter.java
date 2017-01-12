package com.example.manifest.getnews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by tv.unimol on 12.01.2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //check if there is an existing list item view(called convertView) that we can reuse,
        //otherwise, if convertView is null, then inflate a new list item layout
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_news, parent, false);
        }
        //find news at the given position in the list of news
        News currentNews = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.news_title);
        titleView.setText(currentNews.getTitle());
        TextView dateView = (TextView) listItemView.findViewById(R.id.news_date);
        dateView.setText(currentNews.getDate());
        TextView sectionView = (TextView) listItemView.findViewById(R.id.section_name);
        sectionView.setText(currentNews.getSection());

        return listItemView;
    }
}
