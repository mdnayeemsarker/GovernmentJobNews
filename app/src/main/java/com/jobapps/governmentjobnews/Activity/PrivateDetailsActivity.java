package com.jobapps.governmentjobnews.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PrivateDetailsActivity extends AppCompatActivity {

    public Activity activity;
    public Session session;

    private TextView jobsTitleTV, jobsExpiryDateTV, jobsCompanyNameTV;
    private TextView vacancy;
    private TextView salary;
    private TextView context;
    private TextView responsibility;
    private TextView employment_status;
    private TextView education;
    private TextView experience;
    private TextView additional_requirement;
    private TextView location;
    private TextView category;
    private TextView gender;
    private TextView start_date;
    private Button jobsApplyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_details);

        activity = this;
        session = new Session(activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        jobsTitleTV = findViewById(R.id.jobsTitleTVId);
        jobsExpiryDateTV = findViewById(R.id.jobsExpiryDateTVId);
        jobsCompanyNameTV = findViewById(R.id.companyNameId);
        vacancy = findViewById(R.id.vacancyId);
        salary = findViewById(R.id.salaryId);
        context = findViewById(R.id.contextId);
        responsibility = findViewById(R.id.responsibilityId);
        employment_status = findViewById(R.id.employment_statusId);
        education = findViewById(R.id.educationId);
        experience = findViewById(R.id.experienceId);
        additional_requirement = findViewById(R.id.additional_requirementId);
        location = findViewById(R.id.locationId);
        category = findViewById(R.id.categoryId);
        gender = findViewById(R.id.genderId);
        start_date = findViewById(R.id.start_dateId);
        jobsApplyButton = findViewById(R.id.jobsApplyButtonId);

        ApiConfig.checkConnection(activity);

        ApiConfig.autoNetCheck(activity);

        ApiConfig.BannerAds(activity);

        setJobData();
    }@SuppressLint("SetTextI18n")
    private void setJobData() {

        jobsApplyButton.setOnClickListener(v -> {
            if (ApiConfig.isEmail(session.getData("apply_url"))) {
                ApiConfig.openAnotherApp(activity, "email", session.getData("apply_url"));
            } else if (ApiConfig.isURL(session.getData("applyUrl"))) {
                ApiConfig.openAnotherApp(activity, "web", session.getData("apply_url"));
            } else {
                Session session = new Session(activity);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Attention please");
                builder.setMessage("Address: \"" + session.getData("apply_url") + "\"");
                builder.setPositiveButton(("Copy"), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("jobappbd", session.getData("apply_url"));
                    clipboard.setPrimaryClip(clip);
                }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        jobsTitleTV.setText("পদের নাম: " + session.getData("name"));
        jobsCompanyNameTV.setText(session.getData("company_name"));
        vacancy.setText(session.getData("vacancy"));
        salary.setText(session.getData("salary"));
        context.setText(session.getData("context"));
        responsibility.setText(session.getData("responsibility"));
        employment_status.setText(session.getData("employment_status"));
        education.setText(session.getData("education"));
        experience.setText(session.getData("experience"));
        additional_requirement.setText(session.getData("additional_requirement"));
        location.setText(session.getData("location"));
        category.setText(session.getData("category"));
        gender.setText(session.getData("gender"));

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = formatter.parse(session.getData("end_date"));
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            assert date != null;
            String dateTime = dateFormat.format(date);
            jobsExpiryDateTV.setText(activity.getString(R.string.expiryDate) + ": " + dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("date", e.getMessage());
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date2 = formatter2.parse(session.getData("start_date"));
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            assert date2 != null;
            String dateTime2 = dateFormat.format(date2);
            start_date.setText(dateTime2);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("date", e.getMessage());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}