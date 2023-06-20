package com.jobapps.governmentjobnews.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JobDetailsActivity extends AppCompatActivity {

    public Activity activity;
    public Session session;

    private TextView jobsTitleTV, jobsExpiryDateTV, jobsCompanyNameTV;
    private WebView jobDescriptionWV;

    private Button jobsApplyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

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
        jobDescriptionWV = findViewById(R.id.jobDescriptionWVId);
        jobsExpiryDateTV = findViewById(R.id.jobsExpiryDateTVId);
        jobsCompanyNameTV = findViewById(R.id.companyNameId);
        jobsApplyButton = findViewById(R.id.jobsApplyButtonId);

        ApiConfig.checkConnection(activity);

        ApiConfig.autoNetCheck(activity);

        ApiConfig.BannerAds(activity);

        setJobData();
    }

    @SuppressLint("SetTextI18n")
    private void setJobData() {

        jobsApplyButton.setOnClickListener(v -> {
            if (ApiConfig.isEmail(session.getData("applyUrl"))) {
                ApiConfig.openAnotherApp(activity, "email", session.getData("applyUrl"));
            } else if (ApiConfig.isURL(session.getData("applyUrl"))) {
                ApiConfig.openAnotherApp(activity, "web", session.getData("applyUrl"));
            } else {
                Session session = new Session(activity);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Attention please");
                builder.setMessage("Address: \"" + session.getData("applyUrl") + "\"");
                builder.setPositiveButton(("Copy"), (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("jobappbd", session.getData("applyUrl"));
                    clipboard.setPrimaryClip(clip);
                }).setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        jobsTitleTV.setText("পদের নাম: " + session.getData("name"));
        jobsCompanyNameTV.setText(session.getData("companyName"));
        String text = "<html><head>"
                + "</head>"
                + "<body>"
                + session.getData("description")
                + "</body></html>";
        jobDescriptionWV.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = formatter.parse(session.getData("endDate"));
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            assert date != null;
            String dateTime = dateFormat.format(date);
            jobsExpiryDateTV.setText(activity.getString(R.string.expiryDate) + ": " + dateTime);

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