package com.lifecodes.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.lifecodes.newsapp.models.Article;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Article> articlesList;
    private Context context;

    public MainAdapter(ArrayList<Article> articlesList, Context context) {
        this.articlesList = articlesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articlesList.get(position);
        holder.titleTv.setText(article.getTitle());
        holder.descriptionTv.setText(article.getDescription());
        Glide.with(context).load(article.getUrlToImage()).into(holder.thumbnail);

        holder.newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("url", article.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView titleTv, descriptionTv;
        MaterialCardView newsCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.thumbnailIv);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            newsCard = itemView.findViewById(R.id.newsCard);
        }
    }
}
