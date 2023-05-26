package com.jobapps.governmentjobnews.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Model.NewsModel;
import com.jobapps.governmentjobnews.R;

import java.util.ArrayList;

public class NewsPaperAdapter extends RecyclerView.Adapter<NewsPaperAdapter.ViewHolder> {

    private final ArrayList<NewsModel> newsModelArrayList;
    private final Activity activity;

    public NewsPaperAdapter(ArrayList<NewsModel> newsModelArrayList, Activity activity) {
        this.newsModelArrayList = newsModelArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.newsPaperName.setText(newsModelArrayList.get(position).getName());
        Glide.with(activity).load(newsModelArrayList.get(position).getImage_url()).placeholder(R.drawable.app_logo).into(holder.newsPaperImage);
        holder.newsRootCardView.setOnClickListener(v -> {
            ApiConfig.loadInterstitial(activity);
            ApiConfig.transferWebActivity(activity, "result", newsModelArrayList.get(position).getNews_url());
        });
    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView newsPaperName;
        private final ImageView newsPaperImage;
        private final CardView newsRootCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            newsPaperName = itemView.findViewById(R.id.newsPaperNameId);
            newsPaperImage = itemView.findViewById(R.id.newsPaperImageId);
            newsRootCardView = itemView.findViewById(R.id.newsRootCardViewId);
        }
    }
}
