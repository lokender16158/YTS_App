package com.example.lk.yts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LK on 3/12/2018.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(@NonNull Context context,List<Movie> object) {
        super(context,0,object);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Movie movie=getItem(position);

        TextView title=listItemView.findViewById(R.id.movie_title);
        title.setText(movie.getmTitle());

        TextView DateView=listItemView.findViewById(R.id.date);
        //Log.v("Tag",Integer.toString(movie.getmDate()));
        DateView.setText("Year: "+Integer.toString(movie.getmDate()));

        TextView quality=listItemView.findViewById(R.id.quality);
        quality.setText("Quality: "+movie.getmQuality());

        TextView size=listItemView.findViewById(R.id.size);
        size.setText("Size: "+movie.getmSize());

        TextView rating=listItemView.findViewById(R.id.rating);
        rating.setText("Rating: "+Double.toString(movie.getmRating()));

        ImageView imageView=listItemView.findViewById(R.id.thumbnail);
        //Log.v("Image: ",movie.getmImageURL());
        Picasso.get().load(movie.getmImageURL()).into(imageView);

        return listItemView;
    }
}
