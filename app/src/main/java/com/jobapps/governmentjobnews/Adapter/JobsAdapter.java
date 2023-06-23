package com.jobapps.governmentjobnews.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

import com.jobapps.governmentjobnews.Activity.JobDetailsActivity;
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


    public JobsAdapter(Activity activity, ArrayList<JobsModel> jobsModelArrayList) {
        this.activity = activity;
        this.jobsModelArrayList = jobsModelArrayList;
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
        JobsModel model = jobsModelArrayList.get(position);
        holder.jobsTitle.setText("পদের নাম: " + model.getName());
        holder.companyName.setText(model.getCompany_name());

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = formatter.parse(model.getEnd_date());
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            assert date != null;
            String dateTime = dateFormat.format(date);
            holder.jobsExpiryDate.setText(activity.getString(R.string.expiryDate) + ": " + dateTime);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("time", e.getMessage());
        }

        holder.rootJobsCardView.setOnClickListener(v -> {
            Session session = new Session(activity);
            session.setData("name", model.getName());
            session.setData("companyName", model.getCompany_name());
            session.setData("startDate", model.getStart_date());
            session.setData("endDate", model.getEnd_date());
            session.setData("description", model.getDescription());
            session.setData("applyUrl", model.getApply_url());
            ApiConfig.loadInterstitial(activity);
            activity.startActivity(new Intent(activity, JobDetailsActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return jobsModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView jobsTitle;
        private final TextView jobsExpiryDate;
        private final TextView companyName;
        private final CardView rootJobsCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            jobsTitle = itemView.findViewById(R.id.jobsTitleId);
            jobsExpiryDate = itemView.findViewById(R.id.jobsExpiryDateId);
            companyName = itemView.findViewById(R.id.companyNameId);
            rootJobsCardView = itemView.findViewById(R.id.rootJobsCardViewId);

        }
    }

}
