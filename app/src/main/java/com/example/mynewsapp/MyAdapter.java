package com.example.mynewsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynewsapp.Model.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {

    Context context;
    List<Articles> articlesList;

    public MyAdapter(Context context, List<Articles> articlesList) {
        this.context = context;
        this.articlesList = articlesList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
      final Articles artic = articlesList.get(position);
      holder.bind(artic);
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitle,itemSource,itemDate;
        ImageView imageView;
        CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.item_title);
            itemSource = itemView.findViewById(R.id.item_source);
            itemDate = itemView.findViewById(R.id.item_time);
            imageView = itemView.findViewById(R.id.item_image);
            cardView = itemView.findViewById(R.id.item_card_view);
        }

        public void bind(final Articles a){
            itemTitle.setText(a.getTitle());
            itemSource.setText(a.getSource().getName());
            itemDate.setText("\u2022" +dateTime(a.getPublishedAt()));

            String imageUrl = a.getUrlToImage();
            //url detailed
            String url = a.getUrl();

            Picasso.with(context).load(imageUrl).into(imageView);
          //create click to detailed
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,DetailedActivity.class);
                    //pass extras to DetailedActivity
                    intent.putExtra("title",a.getTitle());
                    intent.putExtra("source",a.getSource().getName());
                    intent.putExtra("time",dateTime(a.getPublishedAt()));
                    intent.putExtra("description",a.getDescription());
                    intent.putExtra("imageUrl",a.getUrlToImage());
                    intent.putExtra("url",a.getUrl());
                    context.startActivity(intent);
                    //next go to DetailedActivity
                }
            });
        }
    }

    public String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return time;
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
