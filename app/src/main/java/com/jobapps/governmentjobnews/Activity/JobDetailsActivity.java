package com.jobapps.governmentjobnews.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button jobsPhotoButton;
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

        String getSlug = getIntent().getStringExtra(Constant.ABMN);
        if (getSupportActionBar() != null && !getSlug.isEmpty()) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        jobsTitleTV = findViewById(R.id.jobsTitleTVId);
        jobDescriptionWV = findViewById(R.id.jobDescriptionWVId);
        jobsExpiryDateTV = findViewById(R.id.jobsExpiryDateTVId);
        jobsCompanyNameTV = findViewById(R.id.companyNameId);
        jobsPhotoButton = findViewById(R.id.jobsPhotoButtonId);
        jobsApplyButton = findViewById(R.id.jobsApplyButtonId);

        ApiConfig.checkConnection(activity);
        if (!session.getBoolean(Constant.IS_LOGIN)) {
            ApiConfig.transferActivity(activity, "details", getSlug);
        }else {
            getData(getSlug);
        }

        ApiConfig.autoNetCheck(activity);

        ApiConfig.BannerAds(activity);
    }

    @SuppressLint("SetTextI18n")
    private void getData(String getSlug) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.AUTHORIZATION, Constant.BEARER + session.getData(Constant.TOKEN));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(Constant.STATUS).equals(Constant.SUCCESS)) {

                        JSONObject job = jsonObject.getJSONObject(Constant.JOB);

                        String id = job.getString(Constant.ID);
                        Log.d("ejId", id);

                        String title = job.getString(Constant.TITLE);
                        jobsTitleTV.setText("পদের নাম: " + title);

                        String company_name = job.getString(Constant.COMPANY_NAME);
                        if (company_name.isEmpty()){
                            jobsCompanyNameTV.setVisibility(View.GONE);
                        }else {
                            jobsCompanyNameTV.setVisibility(View.VISIBLE);
                            jobsCompanyNameTV.setText(company_name);
                        }

                        String description = job.getString(Constant.DESCRIPTION);

                        String text = "<html><head>"
                                + "</head>"
                                + "<body>"
                                + description
                                + "</body></html>";
                        jobDescriptionWV.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);

                        String expiry_date = job.getString(Constant.EXPIRY_DATE);
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = formatter.parse(expiry_date);
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                            assert date != null;
                            String dateTime = dateFormat.format(date);
                            jobsExpiryDateTV.setText(activity.getString(R.string.expiryDate) + ": " + dateTime);

                        } catch (ParseException e) {
                            e.printStackTrace();
                            Log.d("date", e.getMessage());
                        }

                        String images = job.getString(Constant.IMAGES);
                        if (!images.equals("[]")) {
                            jobsPhotoButton.setVisibility(View.VISIBLE);
                            jobsPhotoButton.setOnClickListener(view -> ApiConfig.transferActivity(activity, "photo", images));
                        }

                        String slug = job.getString(Constant.SLUG);
                        Log.d("ejSlug", slug);

                        String apply_url = job.getString(Constant.APPLY_URL);

                        jobsApplyButton.setOnClickListener(view -> {
                            ApiConfig.loadInterstitial(activity);
                            if (!apply_url.equals("null")) {
                                if (ApiConfig.isValidEmailId(apply_url)) {
                                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                                    intent.setData(Uri.parse("mailto:" + apply_url));
                                    startActivity(intent);
                                } else if (ApiConfig.checkURL(apply_url)) {
                                    ApiConfig.transferWebActivity(activity, "apply_link", apply_url);
                                }
                            } else {
                                Toast.makeText(activity, "Apply Option are currently can't work. Please contact with admin as soon as. Thank you Stay with us", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ejDetails", e.getMessage());
                }
            }
            Log.d("rjDetails", response);
        }, activity, Constant.JOB_DETAILS_URL + getSlug, params, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}