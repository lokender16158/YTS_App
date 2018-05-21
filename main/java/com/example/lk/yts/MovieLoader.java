package com.example.lk.yts;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

/**
 * Created by LK on 3/12/2018.
 */

 /*
    MovieLoader class

     */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    public String mUrl;
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl=url;
    }
    protected void onStartLoading() {
        Log.v("TAG","Inside On start Loading");
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        return QueryUtils.extractMovies(mUrl);
    }
}