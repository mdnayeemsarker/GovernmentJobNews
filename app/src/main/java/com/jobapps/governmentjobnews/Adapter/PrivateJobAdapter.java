package com.jobapps.governmentjobnews.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jobapps.governmentjobnews.Activity.JobDetailsActivity;
import com.jobapps.governmentjobnews.Activity.PrivateDetailsActivity;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.PrivateJobModel;
import com.jobapps.governmentjobnews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrivateJobAdapter extends RecyclerView.Adapter<PrivateJobAdapter.ViewHolder> {

    final Activity activity;
    private final ArrayList<PrivateJobModel> jobsModelArrayList;


    public PrivateJobAdapter(Activity activity, ArrayList<PrivateJobModel> jobsModelArrayList) {
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
        PrivateJobModel model = jobsModelArrayList.get(position);
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
            session.setData("id", model.getId());
            session.setData("user_id", model.getUser_id());
            session.setData("name", model.getName());
            session.setData("company_name", model.getCompany_name());
            session.setData("vacancy", model.getVacancy());
            session.setData("salary", model.getSalary());
            session.setData("context", model.getContext());
            session.setData("responsibility", model.getResponsibility());
            session.setData("employment_status", model.getEmployment_status());
            session.setData("education", model.getEducation());
            session.setData("experience", model.getExperience());
            session.setData("additional_requirement", model.getAdditional_requirement());
            session.setData("location", model.getLocation());
            session.setData("category", model.getCategory());
            session.setData("gender", model.getGender());
            session.setData("start_date", model.getStart_date());
            session.setData("end_date", model.getEnd_date());
            session.setData("apply_url", model.getApply_url());
            session.setData("created_at", model.getCreated_at());
            session.setData("updated_at", model.getUpdated_at());
            ApiConfig.loadInterstitial(activity);
            activity.startActivity(new Intent(activity, PrivateDetailsActivity.class));
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
