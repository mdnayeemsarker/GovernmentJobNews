package com.jobapps.governmentjobnews.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.material.tabs.TabLayout;
import com.jobapps.governmentjobnews.Adapter.NewsPageAdapter;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.R;

import java.util.Collections;
import java.util.Objects;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        ViewPager viewPager = findViewById(R.id.viewPagerId);
        NewsPageAdapter newsPageAdapter = new NewsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(newsPageAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ApiConfig.checkConnection(this);
        ApiConfig.autoNetCheck(this);

        ApiConfig.BannerAds(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}