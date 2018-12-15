package com.bootcamp.bootcampmovieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bootcamp.bootcampmovieapp.data.MovieItem;

import java.util.ArrayList;

public class MovieAdaptor extends RecyclerView.Adapter<MovieAdaptor.ViewHolder>{
    Context context;

    ArrayList<MovieItem> items = new ArrayList<MovieItem>();

    OnItemClickListener listener;


    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    public MovieAdaptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = (View) inflater.inflate(R.layout.moive_item, viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdaptor.ViewHolder viewHolder, int i) {
        MovieItem item = items.get(i);
        viewHolder.setItem(item);

        viewHolder.setOnItemClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(MovieItem item){
        items.add(item);
    }

    public void addItems(ArrayList<MovieItem> items){
        this.items = items;
    }

    public void removeAll(){
        this.items.clear();
    }

    public MovieItem getMovieItem(int position){
        return items.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView title;
        RatingBar ratingBar;
        TextView year;
        TextView director;
        TextView actors;

        OnItemClickListener listener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = (ImageView) itemView.findViewById(R.id.movieImage);
            title = (TextView) itemView.findViewById(R.id.title);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            year = (TextView) itemView.findViewById(R.id.year);
            director = (TextView) itemView.findViewById(R.id.director);
            actors = (TextView) itemView.findViewById(R.id.actors);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener !=null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });

        }
        public void setItem(MovieItem item){
            title.setText(Html.fromHtml(item.getTitle()));
            ratingBar.setRating((item.getUserRating()/2));
            year.setText(item.getPubDate());
            director.setText(Html.fromHtml(item.getDirector()));
            actors.setText(Html.fromHtml(item.getActor()));
            setImage(item.getImage(),movieImage);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        public void setImage(String url, ImageView view){
            ImageLoadTask task = new ImageLoadTask(url, view);
            task.execute();
        }
    }
}
