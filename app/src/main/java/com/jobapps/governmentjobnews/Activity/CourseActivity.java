package com.jobapps.governmentjobnews.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.jobapps.governmentjobnews.Adapter.CourseAdapter;
import com.jobapps.governmentjobnews.Adapter.JobsAdapter;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.CourseModel;
import com.jobapps.governmentjobnews.Model.JobsModel;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CourseActivity extends AppCompatActivity {

    public Activity activity;
    public Session session;

    private RecyclerView recyclerView;
    private ArrayList<CourseModel> courseModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

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

        courseModelArrayList = new ArrayList<>();
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
                    JSONArray jsonArray = data.getJSONArray("courses");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject course = jsonArray.getJSONObject(i);
                        int id = course.getInt("id");
                        String name = course.getString("name");
                        String duration = course.getString("duration");
                        String fee = course.getString("fee");
                        String created_at = course.getString("created_at");
                        String updated_at = course.getString("updated_at");

                        CourseModel courseModel = new CourseModel("" + id, name, duration, fee, created_at, updated_at);
                        courseModelArrayList.add(courseModel);
                    }
                    CourseAdapter courseAdapter = new CourseAdapter(activity, courseModelArrayList);
                    recyclerView.setAdapter(courseAdapter);
                } catch (Exception e) {
                    Log.d("govjob", e.getMessage());
                }
            }
        }, Request.Method.GET, activity, Constant.API_PATH + "course", new HashMap<>(), true);

    }
}