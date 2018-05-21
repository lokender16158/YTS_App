package com.example.lk.yts;

import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LK on 3/12/2018.
 */

public class QueryUtils {
    public  QueryUtils(){

    }

    public static List<Movie> extractMovies(String requestURL){
        // Create URL object
        URL url = createUrl(requestURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {

        }
        List<Movie> movies=extractFeaturesFromJson(jsonResponse);
        Log.v("Tag",jsonResponse);
        return movies;
    }

    public static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            // Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Response:  ", "Error response code: " + urlConnection.getResponseCode());




            }
        } catch (IOException e) {
            //Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        Log.v("Json Response",jsonResponse);
        return jsonResponse;
    }

    public static String getResponseMessage(URL url){
        try {
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            return urlConnection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Couldn't Get a Response";

    }



    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    @Nullable
    private static List<Movie> extractFeaturesFromJson(String movieJSON){
        if(TextUtils.isEmpty(movieJSON)){
            return null ;
        }
        try{
            ArrayList<Movie> movies=new ArrayList<>();
            JSONObject object=new JSONObject(movieJSON);
            JSONObject data=object.getJSONObject("data");
            JSONArray movie=data.getJSONArray("movies");
            for (int i=0; i<movie.length(); i++){
                int year=0;
                String title="Not Available";
                JSONObject jsonObject=movie.getJSONObject(i);
                title=jsonObject.getString("title");
                year=jsonObject.getInt("year");
                double rating=jsonObject.getDouble("rating");
                String ImageURL=jsonObject.getString("medium_cover_image");
                //Log.v("Tag", ImageURL);
                JSONArray torrents=jsonObject.getJSONArray("torrents");
                String quality="Not Available";
                String URL="Not Available";
                String size="Not Available";
                for (int j=0; j<torrents.length();j++){
                    JSONObject torrentDetail=torrents.getJSONObject(j);
                    if(torrentDetail.getString("quality").equals("720p") || torrentDetail.getString("quality").equals("1080p")){
                        quality=torrentDetail.getString("quality");
                        URL=torrentDetail.getString("url");
                        size=torrentDetail.getString("size");
                    }
                }
                movies.add(new Movie(title,year,rating,size,quality,URL,ImageURL));
            }
            return movies;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}

