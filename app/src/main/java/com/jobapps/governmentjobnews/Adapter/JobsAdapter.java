package com.jobapps.governmentjobnews.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.JobsModel;
import com.jobapps.governmentjobnews.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    final Activity activity;
    private final ArrayList<JobsModel> jobsModelArrayList;
    private final Session session;
    private final String favorite;

    public JobsAdapter(Activity activity, ArrayList<JobsModel> jobsModelArrayList, String favorite) {
        this.activity = activity;
        this.jobsModelArrayList = jobsModelArrayList;
        session = new Session(activity);
        this.favorite = favorite;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lyt_jobs, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.jobsTitle.setText("পদের নাম: " + jobsModelArrayList.get(position).getTitle());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(jobsModelArrayList.get(position).getExpiry_date());
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            assert date != null;
            String dateTime = dateFormat.format(date);
            holder.jobsExpiryDate.setText(activity.getString(R.string.expiryDate) + ": " + dateTime);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("time", e.getMessage());
        }

        if (favorite.equals("addFavorite") || favorite.equals("applied")) {
            holder.jobsFavorite.setImageResource(R.drawable.ic_baseline_favorite_add);
        } else if (favorite.equals("removeFavorite")) {
            holder.jobsFavorite.setImageResource(R.drawable.ic_baseline_remove_circle_outline);
        }

        if (jobsModelArrayList.get(position).getCompany_name().isEmpty()) {
            holder.companyName.setVisibility(View.GONE);
        } else {
            holder.companyName.setVisibility(View.VISIBLE);
            holder.companyName.setText(jobsModelArrayList.get(position).getCompany_name());
        }

        holder.jobsFavorite.setOnClickListener(view -> {

            if (ApiConfig.isConnected(activity)) {
                if (session.getBoolean(Constant.IS_LOGIN)) {
                    String url = null;
                    if (favorite.equals("addFavorite")) {
                        url = Constant.FAVORITE_ADD_URL + jobsModelArrayList.get(position).getSlug();
                    } else if (favorite.equals("removeFavorite")) {
                        url = Constant.FAVORITE_REMOVE_URL + jobsModelArrayList.get(position).getSlug();
                    }
                    Map<String, String> params = new HashMap<>();
                    params.put(Constant.AUTHORIZATION, Constant.BEARER + session.getData(Constant.TOKEN));
                    ApiConfig.RequestToVolley((result, response) -> {
                        if (result) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString(Constant.STATUS).equals(Constant.SUCCESS)) {
                                    Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("eFavorite", e.getMessage());
                            }
                        }
                        Log.d("rFavorite", response);
                    }, activity, url, params, true);

                } else {
                    ApiConfig.transferActivity(activity, "details", jobsModelArrayList.get(position).getSlug());
                }
            } else {
                ApiConfig.isConnectedAlert(activity);
            }

        });
        holder.rootJobsCardView.setOnClickListener(view -> {
            if (ApiConfig.isConnected(activity)) {
                if (session.getBoolean(Constant.IS_LOGIN)) {
                    ApiConfig.transferActivity(activity, "job_details", jobsModelArrayList.get(position).getSlug());
                } else {
                    ApiConfig.transferActivity(activity, "details", jobsModelArrayList.get(position).getSlug());
                }
            } else {
                ApiConfig.isConnectedAlert(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobsModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView jobsFavorite;
        private final TextView jobsTitle;
        private final TextView jobsExpiryDate;
        private final TextView companyName;
        private final CardView rootJobsCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobsFavorite = itemView.findViewById(R.id.jobsFavoriteId);
            jobsTitle = itemView.findViewById(R.id.jobsTitleId);
            jobsExpiryDate = itemView.findViewById(R.id.jobsExpiryDateId);
            companyName = itemView.findViewById(R.id.companyNameId);
            rootJobsCardView = itemView.findViewById(R.id.rootJobsCardViewId);

        }
    }

}
