package com.example.hacks_000.newspaper.adapter;

/**
 * Created by duyti_000 on 12/5/2015.
 */
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class readURL {
    private String text="";
    private String[] tokens;
    private Document doc= null;

    //funtion substring content to string[] to read faster
    public String[] getResponseFromUrl(String url) {
        getContent(url);
        tokens=text.split(",");
        return tokens;
    }
    //funtion get content text from URL
    public String getContent(String url) {
        try {
            doc = Jsoup.connect(url).get();
            Elements p = doc.getElementsByTag("p");
            for (int i = 6; i < p.size()-5; i++) {
                text += "\n"+ p.get(i).text();
            }
            return text;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

}