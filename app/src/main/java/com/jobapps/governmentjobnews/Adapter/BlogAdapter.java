package com.jobapps.governmentjobnews.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jobapps.governmentjobnews.Activity.BlogDetailsActivity;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Model.BlogModel;
import com.jobapps.governmentjobnews.R;

import java.util.ArrayList;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {

    private final Activity activity;
    private final ArrayList<BlogModel> blogModelArrayList;

    public BlogAdapter(Activity activity, ArrayList<BlogModel> blogModelArrayList) {
        this.activity = activity;
        this.blogModelArrayList = blogModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lyt_blog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.blogItemTV.setText(blogModelArrayList.get(position).getTitle());

        holder.blogItemTV.setOnClickListener(v -> {

            if (ApiConfig.isConnected(activity)){
                Intent intent = new Intent(activity, BlogDetailsActivity.class);
                intent.putExtra(Constant.ID, blogModelArrayList.get(position).getId());
                intent.putExtra(Constant.TITLE, blogModelArrayList.get(position).getTitle());
                intent.putExtra(Constant.IMAGES, blogModelArrayList.get(position).getImages());
                intent.putExtra(Constant.ABMN_TYPE, blogModelArrayList.get(position).getType());
                intent.putExtra(Constant.DESCRIPTION, blogModelArrayList.get(position).getDescription());
                intent.putExtra(Constant.STATUS, blogModelArrayList.get(position).getStatus());
                intent.putExtra(Constant.SLUG, blogModelArrayList.get(position).getSlug());
                intent.putExtra(Constant.CREATED_AT, blogModelArrayList.get(position).getCreated_at());
                intent.putExtra(Constant.UPDATED_AT, blogModelArrayList.get(position).getUpdated_at());
                activity.startActivity(intent);
                activity.finish();
                ApiConfig.loadInterstitial(activity);
            }else {
                ApiConfig.isConnectedAlert(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView blogItemTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            blogItemTV = itemView.findViewById(R.id.blogItemTVId);
        }
    }

}
