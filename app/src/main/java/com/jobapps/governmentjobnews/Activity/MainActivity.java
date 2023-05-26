package com.jobapps.governmentjobnews.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        LinearLayout allJobLinearLayout = findViewById(R.id.allJobCardViewId);
        LinearLayout govJobLinearLayout = findViewById(R.id.govJobCardViewId);
        LinearLayout privateJobLinearLayout = findViewById(R.id.privateJobCardViewId);
        LinearLayout hotJobLinearLayout = findViewById(R.id.hotJobCardViewId);
        LinearLayout examResultLinearLayout = findViewById(R.id.examResultCardViewId);
        LinearLayout lastLinearLayout = findViewById(R.id.lastLinearLayoutId);
        LinearLayout studyLinearLayout = findViewById(R.id.studyLinearLayoutId);

        CardView logoutCardView = findViewById(R.id.logoutCardViewId);
        CardView favoriteCardView = findViewById(R.id.favoriteCardViewId);
        CardView ageCardView = findViewById(R.id.ageCardViewId);

        ageCardView.setOnClickListener(view -> ApiConfig.openAgeCalculatorDialog(activity));

        allJobLinearLayout.setOnClickListener(view -> gotoDetails(""));
        govJobLinearLayout.setOnClickListener(view -> gotoDetails("1"));
        privateJobLinearLayout.setOnClickListener(view -> gotoDetails("2"));
        hotJobLinearLayout.setOnClickListener(view -> gotoDetails("3"));

        studyLinearLayout.setOnClickListener(view -> checkLogin("study", "study"));
        examResultLinearLayout.setOnClickListener(view -> checkLogin("exam_result", "exam_result"));
        favoriteCardView.setOnClickListener(view -> checkLogin("favorite_list", "favorite"));
        lastLinearLayout.setOnClickListener(view -> checkLogin("short_list", "short_list"));

        ApiConfig.checkConnection(activity);
        if (!session.getBoolean(Constant.IS_ALERT)) {
            session.setData(Constant.IS_RATING, "1");
        }
        if (session.getData(Constant.IS_RATING).equals("2")) {
            ApiConfig.forceRating(activity, session);
        }
        findViewById(R.id.videoClassCardViewId).setOnClickListener(v -> {
            if (session.getBoolean(Constant.IS_LOGIN)){
                ApiConfig.transferWebActivity(activity, "result", "https://hjhjkmnjkjlikjhjbn.blogspot.com");
            }else {
                checkLogin("video", "video");
            }
        });
        findViewById(R.id.liveVideoCardViewId).setOnClickListener(v -> {
            if (session.getBoolean(Constant.IS_LOGIN)){
                ApiConfig.transferWebActivity(activity, "result", "https://hjhjkmnjkjlikjhjbn.blogspot.com/p/live-class.html");
            }else {
                checkLogin("live", "live");
            }
        });
        findViewById(R.id.resultLinearLayoutId).setOnClickListener(v -> ApiConfig.customResult(activity));
        findViewById(R.id.newsCardViewId).setOnClickListener(v -> ApiConfig.transferActivity(activity, "news", ""));
        findViewById(R.id.cGpaCalculatorCardViewId).setOnClickListener(v -> ApiConfig.transferActivity(activity, "cgpa", ""));

        ApiConfig.autoNetCheck(activity);

        ApiConfig.BannerAds(activity);

        if (session.getBoolean(Constant.IS_LOGIN)) {
            logoutCardView.setVisibility(View.VISIBLE);
            logoutCardView.setOnClickListener(view -> session.logoutUserConfirmation(activity));
            getToken();
        }

//        String channelName = getString(R.string.default_notification_channel_name);
//        String channelId = getString(R.string.default_notification_channel_id);
//        String channelId = "fcm_default_channel";
//        String channelName = "JOBappBD";
//        NotificationManager notificationManager = getSystemService(NotificationManager.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
//        }
    }

    private void checkLogin(String date, String send) {
        ApiConfig.checkConnection(activity);
        if (session.getBoolean(Constant.IS_LOGIN)) {
            ApiConfig.transferActivity(activity, date, send);
        } else {
            ApiConfig.transferActivity(activity, "login", send);
        }
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            String token = task.getResult();
            Log.d("token", token);
            ApiConfig.checkConnection(activity);
            setToken(token);
        });
    }

    private void setToken(String token) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.AUTHORIZATION, Constant.BEARER + session.getData(Constant.TOKEN));
        params.put(Constant.FCM_TOKEN, token);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Token", jsonObject.getString(Constant.MESSAGE));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("eToken", e.getMessage());
                }
            }
            Log.d("rToken", response);
        }, activity, Constant.FCM_UPDATE_URL, params, false);
    }

    private void gotoDetails(String send) {
        if (ApiConfig.isConnected(activity)) {
            ApiConfig.transferActivity(activity, "job_list", send);
        } else {
            ApiConfig.isConnectedAlert(activity);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        ApiConfig.navigationItem(activity, id, drawer);
        return true;
    }
}