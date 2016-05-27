package com.example.hacks_000.newspaper.model;

/**
 * Created by hacks_000 on 11/23/2015.
 */
public class NewsObject {
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String summaryImg;

    public NewsObject() {
        this.title = "a";
        this.description = "a";
        this.link = "a";
        this.pubDate = "a";
        this.summaryImg = "a";
    }

    public NewsObject(String title, String description, String link, String pubDate, String summaryImg) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.summaryImg = summaryImg;
    }

    public NewsObject(NewsObject obj) {
        this.title = obj.title;
        this.description = obj.description;
        this.link = obj.link;
        this.pubDate = obj.pubDate;
        this.summaryImg = obj.summaryImg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setSummaryImg(String summaryImg) {
        this.summaryImg = summaryImg;
    }

    public String getSummaryImg() {
        return summaryImg;
    }
}
