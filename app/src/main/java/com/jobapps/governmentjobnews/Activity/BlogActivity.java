package com.jobapps.governmentjobnews.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.jobapps.governmentjobnews.Adapter.BlogAdapter;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.Model.BlogModel;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlogActivity extends AppCompatActivity {

    public Activity activity;
    public Session session;

    private RecyclerView recyclerView;

    private BlogAdapter blogAdapter;
    private ArrayList<BlogModel> blogModelArrayList;

    private String current_url;

    private ImageButton nextJobsDetailsButton, previousJobsDetailsButton;
    private Button firstJobsDetailsButton, middleJobsDetailsButton, lastJobsDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

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
        nextJobsDetailsButton = findViewById(R.id.nextJobsDetailsButtonId);
        previousJobsDetailsButton = findViewById(R.id.previousJobsDetailsButtonId);

        firstJobsDetailsButton = findViewById(R.id.firstJobsDetailsButtonId);
        middleJobsDetailsButton = findViewById(R.id.middleJobsDetailsButtonId);
        lastJobsDetailsButton = findViewById(R.id.lastJobsDetailsButtonId);

        ApiConfig.checkConnection(activity);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setStackFromEnd(false);
        layoutManager.setReverseLayout(false);
        recyclerView.setLayoutManager(layoutManager);

        blogModelArrayList = new ArrayList<>();

        getData("");
        ApiConfig.autoNetCheck(activity);
    }

    private void getData(String url) {

        if (url.isEmpty()) {
            url = Constant.BLOG_LIST_URL;
        }

        current_url = url;

        Map<String, String> params = new HashMap<>();
        params.put(Constant.AUTHORIZATION, Constant.BEARER + session.getData(Constant.TOKEN));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(Constant.STATUS).equals(Constant.SUCCESS)) {
                        JSONObject blogs = jsonObject.getJSONObject(Constant.BLOGS);

                        String first_page_url = blogs.getString("first_page_url");
                        String last_page_url = blogs.getString("last_page_url");
                        String next_page_url = blogs.getString("next_page_url");
                        String prev_page_url = blogs.getString("prev_page_url");

                        middleJobsDetailsButton.setVisibility(View.INVISIBLE);

                        if (!next_page_url.equals("null")) {
                            blogModelArrayList.clear();
                            blogModelArrayList = new ArrayList<>();
                            nextJobsDetailsButton.setVisibility(View.VISIBLE);
                            lastJobsDetailsButton.setVisibility(View.VISIBLE);
                            nextJobsDetailsButton.setOnClickListener(view -> getData(next_page_url));
                            lastJobsDetailsButton.setOnClickListener(view -> getData(last_page_url));
                        } else {
                            nextJobsDetailsButton.setVisibility(View.GONE);
                            lastJobsDetailsButton.setVisibility(View.GONE);
                        }

                        if (!prev_page_url.equals("null")) {
                            blogModelArrayList.clear();
                            blogModelArrayList = new ArrayList<>();
                            previousJobsDetailsButton.setVisibility(View.VISIBLE);
                            firstJobsDetailsButton.setVisibility(View.VISIBLE);
                            previousJobsDetailsButton.setOnClickListener(view -> getData(prev_page_url));
                            firstJobsDetailsButton.setOnClickListener(view -> getData(first_page_url));
                        } else {
                            previousJobsDetailsButton.setVisibility(View.GONE);
                            firstJobsDetailsButton.setVisibility(View.GONE);
                        }

                        JSONArray data_array = blogs.getJSONArray(Constant.DATA);
                        for (int i = 0; i < data_array.length(); i++) {

                            JSONObject data = data_array.getJSONObject(i);

                            String id = data.getString(Constant.ID);
                            String slug = data.getString(Constant.SLUG);
                            String title = data.getString(Constant.TITLE);
                            String description = data.getString(Constant.DESCRIPTION);
                            String status = data.getString(Constant.STATUS);
                            String images = data.getString(Constant.IMAGES);

                            String created_at = data.getString(Constant.CREATED_AT);
                            String updated_at = data.getString(Constant.UPDATED_AT);

                            BlogModel blogModel = new BlogModel(id, title, "blog", images, description, status, slug, created_at, updated_at);
                            blogModelArrayList.add(blogModel);
                        }
                        blogAdapter = new BlogAdapter(activity, blogModelArrayList);
                        recyclerView.setAdapter(blogAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("eBlog", e.getMessage());
                }
            }
            Log.d("rBlog", response);
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
                    blogModelArrayList.clear();
                    blogModelArrayList = new ArrayList<>();
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