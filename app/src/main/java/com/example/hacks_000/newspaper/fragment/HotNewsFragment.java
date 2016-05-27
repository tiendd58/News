package com.example.hacks_000.newspaper.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hacks_000.newspaper.R;
import com.example.hacks_000.newspaper.adapter.NewsAdapter;
import com.example.hacks_000.newspaper.model.NewsObject;
import com.example.hacks_000.newspaper.util.HandleXML;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotNewsFragment extends Fragment {

    private String finalUrl="http://www.24h.com.vn/upload/rss/bongda.rss";
    private HandleXML obj;
    ArrayList<NewsObject> listObject;

    public HotNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hot_news, container, false);

        // get the url which suitable with the tab
        getUrl();
        // load content' rss
        obj = new HandleXML(finalUrl);
        obj.fetchXML();
        while (!obj.parsingComplete);
        listObject = obj.getListObject();

        //use recycleview to show the news list
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.news_list);
        rv.setLayoutManager(llm);
        NewsAdapter adapter = new NewsAdapter(getActivity(), listObject);
        rv.setAdapter(adapter);

        return rootView;
    }

    private void getUrl() {
        Bundle bundle = this.getArguments();
        int type = bundle.getInt("type");
        switch (type) {
            case 0:
                finalUrl = "http://www.24h.com.vn/upload/rss/tintuctrongngay.rss";
                break;
            case 1:
                finalUrl="http://www.24h.com.vn/upload/rss/bongda.rss";
                break;
            case 2:
                finalUrl = "http://www.24h.com.vn/upload/rss/anninhhinhsu.rss";
                break;
            case 3:
                finalUrl = "http://www.24h.com.vn/upload/rss/thoitrang.rss";
                break;
        }
    }


}
