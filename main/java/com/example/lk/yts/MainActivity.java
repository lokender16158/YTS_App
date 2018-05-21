package com.example.lk.yts;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {
    private MovieAdapter mAdapter;
    private static final String TAG = "MyActivity";
    private static String requestURL="https://yts.am/api/v2/list_movies.json?sort_by=date_added&limit=50";
    private String statusCode="200";
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG,"Inside OnCreate Method");

        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);

        ListView listView=findViewById(R.id.list_view);
        //TextView mEmptyTextView=findViewById(R.id.empty_view);
        //mEmptyTextView.setText(statusCode);
        mAdapter=new MovieAdapter(this,new ArrayList<Movie>());
        listView.setAdapter(mAdapter);
        //listView.setEmptyView(mEmptyTextView);



        /*
        Implementing spinner
         */
        Spinner spinner = (Spinner) findViewById(R.id.sort_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapterSort = ArrayAdapter.createFromResource(this,
                R.array.movie_sort_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapterSort);


        final LoaderManager loaderManager=getLoaderManager();


        final String string="https://yts.am/api/v2/list_movies.json?sort_by=";
        final String string2="&limit=50";

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("Choice Selected: ", parent.getItemAtPosition(position).toString() + position);
                    switch (position) {
                        case 0:
                            requestURL = string + "date_added" + string2;
                            break;
                        case 1:
                            requestURL = string + "seeds" + string2;
                            break;
                        case 2:
                            requestURL = string + "peers" + string2;
                            break;
                        case 3:
                            requestURL = string + "year" + string2;
                            break;
                        case 4:
                            requestURL = string + "rating" + string2;
                            break;
                        case 5:
                            requestURL = string + "like_count" + string2;
                            break;
                        case 6:
                            requestURL = string + "download_count" + string2;
                            break;
                    }
                    loaderManager.restartLoader(1, null, MainActivity.this);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie currentMovie=mAdapter.getItem(position);
                Uri movieUri=Uri.parse(currentMovie.getmURL());
                Intent website=new Intent(Intent.ACTION_VIEW,movieUri);
                startActivity(website);
            }
        });

        final SearchView searchView=findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query=query.replace(" ","");
                requestURL="https://yts.am/api/v2/list_movies.json?query_term="+query;
                loaderManager.restartLoader(1,null,MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        loaderManager.initLoader(1,null,this);




    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        Log.v(TAG,"Inside OncreateLoader");
        Log.v("Request URL ",requestURL);
        return new MovieLoader(this,requestURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mAdapter.clear();
        mProgressBar.setVisibility(View.VISIBLE);


        if (movies!=null && !movies.isEmpty()){
        mAdapter.addAll(movies);

        }
        mProgressBar.setVisibility(View.INVISIBLE);

        Log.v(TAG,"Inside OnLoadFinished");

    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.clear();
    }


}
