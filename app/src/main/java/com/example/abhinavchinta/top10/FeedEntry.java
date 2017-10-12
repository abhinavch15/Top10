package com.example.abhinavchinta.top10;

import android.graphics.Bitmap;

/**
 * Created by Abhinav Chinta on 9/13/2017.
 */

public class FeedEntry {
    private String name=null;
    private String artist=null;
    private String releaseDate=null;
    private String summary;
    private String ImageURL;
    private Bitmap bitmap;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releasedate) {
        this.releaseDate = releasedate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImageURL() {
        return ImageURL;

    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    //@Override
    //public String toString() {
     //   return   name + '\n' +
     //           "Developed by: " + artist + '\n' +
      //          "Release date: " + releaseDate + '\n' +
       //         //"ImageURL: " + ImageURL + '\n' ;
        //        "Summary: \n" + summary + '\n';
    //}
}
