package com.bootcamp.bootcampmovieapp.data;

import java.util.Date;

public class MovieItem {
    public String title;
    public String link;
    public String image;
    public String pubDate;
    public String actor;
    public String director;
    public float userRating;

    public MovieItem(String title, String link, String image,String pubDate ,String actor, String director, float userRating) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.pubDate = pubDate;
        this.actor = actor;
        this.director = director;
        this.userRating = userRating;
    }
    public MovieItem(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", actor='" + actor + '\'' +
                ", userRating=" + userRating +
                '}';
    }
}
