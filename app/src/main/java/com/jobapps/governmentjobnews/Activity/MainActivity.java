package com.jobapps.governmentjobnews.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.R;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Activity activity;

    private Session session;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        activity = this;
        session = new Session(activity);

//        TextView marqueeTxtView = findViewById(R.id.marqueeTxtViewId);
//        marqueeTxtView.setSelected(true);

        LinearLayout govJobLinearLayout = findViewById(R.id.govJobCardViewId);
        LinearLayout privateJobLinearLayout = findViewById(R.id.privateJobCardViewId);
        LinearLayout hotJobLinearLayout = findViewById(R.id.hotJobCardViewId);
        LinearLayout lastLinearLayout = findViewById(R.id.lastLinearLayoutId);
        CardView ageCardView = findViewById(R.id.ageCardViewId);

        ageCardView.setOnClickListener(view -> ApiConfig.openAgeCalculatorDialog(activity));

        govJobLinearLayout.setOnClickListener(view -> startActivity(new Intent(activity, GovJobActivity.class)));
        privateJobLinearLayout.setOnClickListener(view -> startActivity(new Intent(activity, JobsActivity.class).putExtra(Constant.ABMN, "1")));
        hotJobLinearLayout.setOnClickListener(view -> startActivity(new Intent(activity, JobsActivity.class).putExtra(Constant.ABMN, "2")));
        lastLinearLayout.setOnClickListener(view -> startActivity(new Intent(activity, JobShortListActivity.class)));

        ApiConfig.checkConnection(activity);
        if (!session.getBoolean(Constant.IS_ALERT)) {
            session.setData(Constant.IS_RATING, "1");
        }
        if (session.getData(Constant.IS_RATING).equals("2")) {
            ApiConfig.forceRating(activity, session);
        }

        findViewById(R.id.newsCardViewId).setOnClickListener(v -> ApiConfig.transferActivity(activity, "news", ""));
        findViewById(R.id.cGpaCalculatorCardViewId).setOnClickListener(v -> ApiConfig.transferActivity(activity, "cgpa", ""));
        findViewById(R.id.courseCV).setOnClickListener(v -> startActivity(new Intent(activity, CourseActivity.class)));
        findViewById(R.id.companyRegCV).setOnClickListener(v -> ApiConfig.openAnotherApp(activity, "web", "https://www.governmentjobnews.com.bd/login"));

        ApiConfig.autoNetCheck(activity);

        ApiConfig.BannerAds(activity);

        ApiConfig.RequestToVolley((result, response) -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject setting = data.getJSONObject("setting");
                String popup = setting.getString("popup");

                if (popup.equals("on")) {
                    String popup_img = setting.getString("popup_img");
                    Log.d("company", "" + session.getBoolean("reg"));
                    if (session.getBoolean("reg")){
                        makePopUp(popup_img);
                        session.setBoolean("reg", false);
                        Log.d("company", popup + ", " + popup_img);
                    }
                }

            } catch (Exception e) {
                Log.d("company", e.getMessage());
            }
        }, Request.Method.GET, activity, Constant.API_PATH + "setting", new HashMap<>(), false);
    }

    private void makePopUp(String img_url) {
//        click to go to regi page
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View customLayout = activity.getLayoutInflater().inflate(R.layout.lyt_popup, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        CardView bannerCloseCV = customLayout.findViewById(R.id.bannerCloseCV);
        bannerCloseCV.setOnClickListener(v -> dialog.dismiss());

        ImageView bannerIV = customLayout.findViewById(R.id.bannerIV);
        Glide.with(activity).load(img_url).into(bannerIV);
        bannerIV.setOnClickListener(v -> {
            dialog.dismiss();
            startActivity(new Intent(activity, CourseActivity.class));
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        ApiConfig.navigationItem(activity, id, drawer);
        return true;
    }
}