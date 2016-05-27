package com.example.hacks_000.newspaper.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hacks_000.newspaper.R;
import com.example.hacks_000.newspaper.adapter.NewsAdapter;
import com.example.hacks_000.newspaper.model.NewsObject;
import com.example.hacks_000.newspaper.util.HandleXML;

import java.util.ArrayList;

public class FindActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int SAY_REQUESCODE = 1;
    ArrayList<NewsObject> mList = new ArrayList<NewsObject>();
    ArrayList<NewsObject> resultList = new ArrayList<NewsObject>();
    EditText editText;
    RecyclerView rv;
    NewsAdapter adapter;
    ImageButton micro;
    private Toolbar toolbar;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        // create custom action bar
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        downloadList();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv = (RecyclerView) findViewById(R.id.news_list);
        rv.setLayoutManager(llm);
        adapter = new NewsAdapter(this, resultList);
        rv.setAdapter(adapter);
        editText = (EditText) findViewById(R.id.txt);
        micro = (ImageButton) findViewById(R.id.micro);

        micro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSay();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    //setApdaterListView(mList);
                    rv.setAdapter(null);
                } else {
                    resultList = getList(s.toString());
                    adapter = new NewsAdapter(FindActivity.this, resultList);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //setApdaterListView(getList(editText.getText().toString()));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void downloadList() {
        HandleXML obj = new HandleXML("http://www.24h.com.vn/upload/rss/tintuctrongngay.rss");
        obj.fetchXML();
        while (!obj.parsingComplete) ;
        ArrayList<NewsObject> list1 = obj.getListObject();
        for (int i = 0; i < list1.size(); i++) {
            mList.add(list1.get(i));
        }


        HandleXML obj2 = new HandleXML("http://www.24h.com.vn/upload/rss/bongda.rss");
        obj2.fetchXML();
        while (!obj2.parsingComplete) ;
        ArrayList<NewsObject> list2 = obj2.getListObject();
        for (int i = 0; i < list2.size(); i++) {
            mList.add(list2.get(i));
        }

        HandleXML obj3 = new HandleXML("http://www.24h.com.vn/upload/rss/anninhhinhsu.rss");
        obj3.fetchXML();
        while (!obj3.parsingComplete) ;
        ArrayList<NewsObject> list3 = obj3.getListObject();
        for (int i = 0; i < list3.size(); i++) {
            mList.add(list3.get(i));
        }

        HandleXML obj4 = new HandleXML("http://www.24h.com.vn/upload/rss/thoitrang.rss");
        obj4.fetchXML();
        while (!obj4.parsingComplete) ;
        ArrayList<NewsObject> list4 = obj4.getListObject();
        for (int i = 0; i < list4.size(); i++) {
            mList.add(list4.get(i));
        }
    }

    public ArrayList<NewsObject> getList(String findMe) {
        ArrayList<NewsObject> findS = new ArrayList<NewsObject>();
        String searchMe = " ";

        for (int i = 0; i < mList.size(); i++) {
            searchMe = mList.get(i).getTitle();
            int searchMeLength = searchMe.length();
            int findMeLength = findMe.length();
            boolean check = false;

            for (int j = 0; j <= (searchMeLength - findMeLength); j++) {
                if (searchMe.regionMatches(j, findMe, 0, findMeLength)) {
                    findS.add(mList.get(i));
                    break;
                }
            }
        }
//        apdater.notifyDataSetChanged();
        return findS;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void startSay() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(intent, SAY_REQUESCODE);
        } catch (ActivityNotFoundException e) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.i(TAG, "onActivityResult");
        switch (requestCode) {
            case SAY_REQUESCODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String questionContent = text.get(0);
                    editText.setText(questionContent, TextView.BufferType.EDITABLE);
                }
                break;
            }
        }
    }
}
