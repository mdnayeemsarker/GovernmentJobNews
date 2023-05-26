package com.jobapps.governmentjobnews.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.R;

import java.util.Objects;

public class BlogDetailsActivity extends AppCompatActivity {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }

        TextView blogTitleTV = findViewById(R.id.blogTitleTVId);
        WebView blogDescriptionWV = findViewById(R.id.blogDescriptionWVId);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButtonId);

        ApiConfig.checkConnection(activity);

        String id, title, type, images, description, status, slug, created_at, updated_at;

        id = getIntent().getStringExtra(Constant.ID);
        title = getIntent().getStringExtra(Constant.TITLE);
        type = getIntent().getStringExtra(Constant.ABMN_TYPE);
        images = getIntent().getStringExtra(Constant.IMAGES);
        description = getIntent().getStringExtra(Constant.DESCRIPTION);
        status = getIntent().getStringExtra(Constant.STATUS);
        slug = getIntent().getStringExtra(Constant.SLUG);
        created_at = getIntent().getStringExtra(Constant.CREATED_AT);
        updated_at = getIntent().getStringExtra(Constant.UPDATED_AT);

        Log.d("blog", id + "\n" + title + "\n" + description + "\n" + status + "\n" + slug + "\n" + created_at + "\n" + updated_at);

        blogTitleTV.setText(title);
        String text = "<html><head>"
                + "</head>"
                + "<body>"
                + description
                + "</body></html>";
        blogDescriptionWV.loadDataWithBaseURL(null, text, "text/html", "utf-8", null);

        if (images.equals("null")) {
            floatingActionButton.setVisibility(View.GONE);
        } else {
            floatingActionButton.setVisibility(View.VISIBLE);
            floatingActionButton.setOnClickListener(v -> ApiConfig.transferActivity(activity, type, images));
        }

        ApiConfig.BannerAds(activity);
        ApiConfig.autoNetCheck(activity);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}