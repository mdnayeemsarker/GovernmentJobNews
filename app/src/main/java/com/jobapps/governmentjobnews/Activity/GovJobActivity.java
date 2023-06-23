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

import com.android.volley.Request;
import com.jobapps.governmentjobnews.Adapter.JobsAdapter;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.JobsModel;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GovJobActivity extends AppCompatActivity {

    public Activity activity;
    public Session session;

    private RecyclerView recyclerView;
    private ArrayList<JobsModel> jobsModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gov_job);

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

        ApiConfig.BannerAds(activity);

        jobsModelArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setStackFromEnd(false);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);

        getData();

    }

    private void getData() {

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject(Constant.DATA);
                    JSONArray jsonArray = data.getJSONArray("posts");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        int id = post.getInt("id");
                        String name = post.getString("name");
                        String company_name = post.getString("company_name");
                        String start_date = post.getString("start_date");
                        String end_date = post.getString("end_date");
                        String apply_url = post.getString("apply_url");
                        String description = post.getString("description");
                        String created_at = post.getString("created_at");
                        String updated_at = post.getString("updated_at");

                        JobsModel jobsModel = new JobsModel("" + id, name, company_name, start_date, end_date, apply_url, description, created_at, updated_at);
                        jobsModelArrayList.add(jobsModel);
                    }
                    JobsAdapter jobsAdapter = new JobsAdapter(activity, jobsModelArrayList);
                    recyclerView.setAdapter(jobsAdapter);
                } catch (Exception e) {
                    Log.d("govjob", e.getMessage());
                }
            }
        }, Request.Method.GET, activity, Constant.API_PATH + "gov-jobs", new HashMap<>(), true);

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.jobs_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reload_jobsId) {
            if (ApiConfig.isConnected(activity)) {
                getData();
            } else {
                ApiConfig.isConnectedAlert(activity);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}