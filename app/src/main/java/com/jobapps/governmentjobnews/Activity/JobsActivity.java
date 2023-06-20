package com.jobapps.governmentjobnews.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.jobapps.governmentjobnews.Adapter.PrivateJobAdapter;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.PrivateJobModel;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class JobsActivity extends AppCompatActivity {

    public Activity activity;
    public Session session;

    private RecyclerView recyclerView;

    private ArrayList<PrivateJobModel> jobsModelArrayList;

    private String type;

//    private ImageButton nextJobsButton, previousJobsButton;
//    private Button firstJobsButton, middleJobsButton, lastJobsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        activity = this;
        session = new Session(activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        type = getIntent().getStringExtra(Constant.ABMN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        ApiConfig.BannerAds(activity);

        recyclerView = findViewById(R.id.recyclerViewId);
//        nextJobsButton = findViewById(R.id.nextJobsDetailsButtonId);
//
//        previousJobsButton = findViewById(R.id.previousJobsDetailsButtonId);
//        firstJobsButton = findViewById(R.id.firstJobsDetailsButtonId);
//        middleJobsButton = findViewById(R.id.middleJobsDetailsButtonId);
//        lastJobsButton = findViewById(R.id.lastJobsDetailsButtonId);

        ApiConfig.checkConnection(activity);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setStackFromEnd(false);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);

        jobsModelArrayList = new ArrayList<>();

        getPrivateData();
        ApiConfig.autoNetCheck(activity);
    }

    private void getPrivateData() {
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject(Constant.DATA);
                    JSONArray jsonArray = data.getJSONArray("posts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

//                      Start pagination
//                        String current_page = jobs.getString("current_page");
//                        String last_page = jobs.getString("last_page");
//                        int last_pageInt = Integer.parseInt(last_page);
//                        if (current_page.equals("1")) {
//                            nextJobsButton.setVisibility(View.VISIBLE);
//                            lastJobsButton.setVisibility(View.VISIBLE);
//                            previousJobsButton.setVisibility(View.GONE);
//                            firstJobsButton.setVisibility(View.GONE);
//                        }
//                        middleJobsButton.setVisibility(View.INVISIBLE);
//                        nextJobsButton.setOnClickListener(view -> {
//                            previousJobsButton.setVisibility(View.VISIBLE);
//                            firstJobsButton.setVisibility(View.VISIBLE);
//                            jobsModelArrayList.clear();
//                            jobsModelArrayList = new ArrayList<>();
//                            current_pageInt = Integer.parseInt(current_page);
//                            current_pageInt = current_pageInt + 1;
//                            getData(Constant.JOB_LIST_URL + "?category=" + type + "&" + "page=" + current_pageInt);
//                        });
//                        lastJobsButton.setOnClickListener(view -> getData(Constant.JOB_LIST_URL + "?category=" + type + "&" + "page=" + last_page));
//                        if (last_pageInt == current_pageInt) {
//                            nextJobsButton.setVisibility(View.GONE);
//                            lastJobsButton.setVisibility(View.GONE);
//                        }
//                        previousJobsButton.setOnClickListener(view -> {
//                            jobsModelArrayList.clear();
//                            jobsModelArrayList = new ArrayList<>();
//                            current_pageInt = Integer.parseInt(current_page);
//                            current_pageInt = current_pageInt - 1;
//                            getData(Constant.JOB_LIST_URL + "?category=" + type + "&" + "page=" + current_pageInt);
//                        });
//                        firstJobsButton.setOnClickListener(view -> getData(Constant.JOB_LIST_URL + "?category=" + type + "&" + "page=1"));
//                        End pagination
                        String id = post.getString(Constant.ID);
                        String user_id = post.getString("user_id");
                        String name = post.getString("name");
                        String company_name = post.getString("company_name");
                        String vacancy = post.getString("vacancy");
                        String salary = post.getString("salary");
                        String context = post.getString("context");
                        String responsibility = post.getString("responsibility");
                        String employment_status = post.getString("employment_status");
                        String education = post.getString("education");
                        String experience = post.getString("experience");
                        String additional_requirement = post.getString("additional_requirement");
                        String location = post.getString("location");
                        String category = post.getString("category");
                        String gender = post.getString("gender");
                        String start_date = post.getString("start_date");
                        String end_date = post.getString("end_date");
                        String apply_url = post.getString("apply_url");
                        String created_at = post.getString(Constant.CREATED_AT);
                        String updated_at = post.getString(Constant.UPDATED_AT);

                        PrivateJobModel privateJobModel = new PrivateJobModel(id, user_id, name, company_name, vacancy, salary, context, responsibility, employment_status, education,
                                experience, additional_requirement, location, category, gender, start_date, end_date, apply_url, created_at, updated_at);
                        jobsModelArrayList.add(privateJobModel);
                    }
                    PrivateJobAdapter privateJobAdapter = new PrivateJobAdapter(activity, jobsModelArrayList);
                    recyclerView.setAdapter(privateJobAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("eJobs", e.getMessage());
                }
            }
            Log.d("rJobs", response);
        }, Request.Method.GET, activity, Constant.API_PATH + "private-job/" + type, new HashMap<>(), true);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.jobs_menu, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.reload_jobsId) {
//            if (ApiConfig.isConnected(activity)) {
//                if (!current_url.isEmpty()) {
//                    jobsModelArrayList.clear();
//                    jobsModelArrayList = new ArrayList<>();
//                    getPrivateData();
//                }
//            } else {
//                ApiConfig.isConnectedAlert(activity);
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}