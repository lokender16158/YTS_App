package com.example.lk.yts;

/**
 * Created by LK on 3/12/2018.
 */

public class Movie {
    private String mTitle;
    private int mDate;
    private double mRating;
    private String mSize;
    private String mQuality;
    private String mURL;
    private String mImageURL;

    public Movie(String mTitle, int mDate, double mRating, String mSize, String mQuality, String mURL,String mImageURL) {
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mRating = mRating;
        this.mSize = mSize;
        this.mQuality = mQuality;
        this.mURL = mURL;
        this.mImageURL=mImageURL;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmDate() {
        return mDate;
    }

    public double getmRating() {
        return mRating;
    }

    public String getmSize() {
        return mSize;
    }

    public String getmQuality() {
        return mQuality;
    }

    public String getmURL() {
        return mURL;
    }

    public String getmImageURL(){return mImageURL;}
}
