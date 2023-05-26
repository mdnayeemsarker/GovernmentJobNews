package com.jobapps.governmentjobnews.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.jobapps.governmentjobnews.Adapter.JobsAdapter;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.JobsModel;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JobShortListActivity extends AppCompatActivity {

    public Activity activity;
    public Session session;

    private RecyclerView recyclerView;

    private JobsAdapter jobsAdapter;
    private ArrayList<JobsModel> jobsModelArrayList;

    private String current_url;
    private int current_pageInt;

    private ImageButton nextJobsButton, previousJobsButton;
    private Button firstJobsButton, middleJobsButton, lastJobsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_short_list);
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

        current_url = "";

        ApiConfig.BannerAds(activity);

        recyclerView = findViewById(R.id.recyclerViewId);
        nextJobsButton = findViewById(R.id.nextJobsDetailsButtonId);
        previousJobsButton = findViewById(R.id.previousJobsDetailsButtonId);

        firstJobsButton = findViewById(R.id.firstJobsDetailsButtonId);
        middleJobsButton = findViewById(R.id.middleJobsDetailsButtonId);
        lastJobsButton = findViewById(R.id.lastJobsDetailsButtonId);

        ApiConfig.checkConnection(activity);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setStackFromEnd(false);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);

        jobsModelArrayList = new ArrayList<>();

        getData("");

        ApiConfig.autoNetCheck(activity);
    }

    private void getData(String url) {

        if (url.isEmpty()) {
            url = Constant.SHORT_BY_DATE_URL;
        }
        current_url = url;

        Map<String, String> params = new HashMap<>();
        params.put(Constant.AUTHORIZATION, Constant.BEARER + session.getData(Constant.TOKEN));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(Constant.STATUS).equals(Constant.SUCCESS)) {

                        JSONObject jobs = jsonObject.getJSONObject(Constant.JOBS);

//                      Start pagination
                        String current_page = jobs.getString("current_page");
                        String last_page = jobs.getString("last_page");
                        int last_pageInt = Integer.parseInt(last_page);
                        if (current_page.equals("1")) {
                            nextJobsButton.setVisibility(View.VISIBLE);
                            lastJobsButton.setVisibility(View.VISIBLE);
                            previousJobsButton.setVisibility(View.GONE);
                            firstJobsButton.setVisibility(View.GONE);
                        }
                        middleJobsButton.setVisibility(View.INVISIBLE);
                        nextJobsButton.setOnClickListener(view -> {
                            previousJobsButton.setVisibility(View.VISIBLE);
                            firstJobsButton.setVisibility(View.VISIBLE);
                            jobsModelArrayList.clear();
                            jobsModelArrayList = new ArrayList<>();
                            current_pageInt = Integer.parseInt(current_page);
                            current_pageInt = current_pageInt + 1;
                            getData(Constant.SHORT_BY_DATE_URL + "?page=" + current_pageInt + "&");
                        });
                        lastJobsButton.setOnClickListener(view -> getData(Constant.SHORT_BY_DATE_URL + "?page=" + current_pageInt + "&"));
                        if (last_pageInt == current_pageInt) {
                            nextJobsButton.setVisibility(View.GONE);
                            lastJobsButton.setVisibility(View.GONE);
                        }
                        previousJobsButton.setOnClickListener(view -> {
                            jobsModelArrayList.clear();
                            jobsModelArrayList = new ArrayList<>();
                            current_pageInt = Integer.parseInt(current_page);
                            current_pageInt = current_pageInt - 1;
                            getData(Constant.SHORT_BY_DATE_URL + "&" + "page=" + current_pageInt);
                        });
                        firstJobsButton.setOnClickListener(view -> getData(Constant.SHORT_BY_DATE_URL + "?page=" + current_pageInt + "&"));
//                        End pagination

                        JSONArray data_array = jobs.getJSONArray(Constant.DATA);
                        for (int i = 0; i < data_array.length(); i++) {

                            JSONObject data = data_array.getJSONObject(i);

                            String id = data.getString(Constant.ID);
                            String title = data.getString(Constant.TITLE);
                            String company_name = data.getString(Constant.COMPANY_NAME);
                            String description = data.getString(Constant.DESCRIPTION);
                            String career_level_id = data.getString(Constant.CAREER_LEVEL_ID);
                            String expiry_date = data.getString(Constant.EXPIRY_DATE);
                            String is_active = data.getString(Constant.IS_ACTIVE);
                            String slug = data.getString(Constant.SLUG);
                            String created_at = data.getString(Constant.CREATED_AT);
                            String updated_at = data.getString(Constant.UPDATED_AT);

                            JobsModel jobsModel = new JobsModel(id, title, company_name, description, career_level_id, expiry_date, is_active, slug, created_at, updated_at);
                            jobsModelArrayList.add(jobsModel);
                        }
                        jobsAdapter = new JobsAdapter(activity, jobsModelArrayList, "addFavorite");
                        recyclerView.setAdapter(jobsAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("esJobs", e.getMessage());
                }
            }
            Log.d("rsJobs", response);
        }, activity, url, params, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jobs_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.reload_jobsId) {
            if (ApiConfig.isConnected(activity)) {
                if (!current_url.isEmpty()) {
                    jobsModelArrayList.clear();
                    jobsModelArrayList = new ArrayList<>();
                    getData(current_url);
                }
            } else {
                ApiConfig.isConnectedAlert(activity);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}