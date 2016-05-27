package com.example.hacks_000.newspaper.util;


import com.example.hacks_000.newspaper.model.NewsObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by hacks_000 on 11/22/2015.
 */

public class HandleXML {
    private ArrayList<NewsObject> listObject;
    private String title = "a";
    private String description = "a";
    private String link = "a";
    private String pubDate = "a";
    private String summaryImg = "a";

    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = false;

    public HandleXML(String url){
        this.urlString = url;
    }

    public ArrayList<NewsObject> getListObject() {
        return listObject;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {

        int event;
        String text = "a";
        listObject = new ArrayList<NewsObject>();
        try {
            event = myParser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();
                if(null == name) {
                    name = "a";
                }

                switch (event){
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("title")) {
                            title = text;
                        } else if (name.equals("link")) {
                            link = text;
                            listObject.add(new NewsObject(title, description, link, pubDate, summaryImg));
                        } else if (name.equals("description")) {
                            description = text;
                        } else if (name.equals("pubDate")) {
                            pubDate = text;
                        } else if (name.equals("summaryImg")) {
                            summaryImg = text;
                        }
                        break;

                }
                event = myParser.next();
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }
        listObject.remove(0);
        listObject.remove(0);
        getDescriptionAndImg();
        editTitle();
        parsingComplete = true;
    }

    private void getDescriptionAndImg() {
        String s = new String();
        for(int i = 0; i < listObject.size(); i++) {
            s = listObject.get(i).getDescription();
            Document doc = Jsoup.parse(s);
            doc.normalise();
            Elements e = doc.select("img[src]");
            listObject.get(i).setSummaryImg(e.attr("src"));
            listObject.get(i).setDescription(doc.text());
        }
    }

    private void editTitle() {
        for(int i = 0; i < listObject.size(); i++) {
            String titleString = listObject.get(i).getTitle();
            for(int j = 0; j < (titleString.length() - 4); j++) {
                String smallString = titleString.substring(j, j + 5);
                if(smallString.equals("&#34;")){
                    titleString = titleString.substring(0, j) + "\"" + titleString.substring(j + 5);
                }
            }
            listObject.get(i).setTitle(titleString);
        }
    }


    public void fetchXML(){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                }

                catch (Exception e) {
                }
            }
        });
        thread.start();
    }
}
