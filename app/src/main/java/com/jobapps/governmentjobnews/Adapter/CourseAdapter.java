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

import com.jobapps.governmentjobnews.Activity.CourseRegActivity;
import com.jobapps.governmentjobnews.Activity.JobDetailsActivity;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.CourseModel;
import com.jobapps.governmentjobnews.Model.JobsModel;
import com.jobapps.governmentjobnews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    final Activity activity;
    private final ArrayList<CourseModel> jobsModelArrayList;


    public CourseAdapter(Activity activity, ArrayList<CourseModel> jobsModelArrayList) {
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
        CourseModel model = jobsModelArrayList.get(position);
        holder.jobsTitle.setText("Course Name: " + model.getName());
        holder.companyName.setText("Course Duration: " + model.getDuration());
        holder.jobsExpiryDate.setText("Course Fee: " + model.getFee());

        holder.rootJobsCardView.setOnClickListener(v -> activity.startActivity(new Intent(activity, CourseRegActivity.class).putExtra("course_id", model.getId())));
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