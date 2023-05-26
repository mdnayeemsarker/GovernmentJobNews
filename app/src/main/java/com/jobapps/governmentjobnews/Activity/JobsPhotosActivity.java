package com.jobapps.governmentjobnews.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.impl.utils.ForceStopRunnable;

import com.bumptech.glide.Glide;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;

import java.io.File;
import java.util.Objects;

public class JobsPhotosActivity extends AppCompatActivity {

    private Activity activity;
    private ImageView jobsPhotoIV, previousJobsPhotoIV, nextJobsPhotoIV;
    private Button middleJobsButton;
    private String imagesType, imagesString;
    private int photoPath = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_photos);

        activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        ApiConfig.BannerAds(activity);

        jobsPhotoIV = findViewById(R.id.jobsPhotoIVId);
        previousJobsPhotoIV = findViewById(R.id.previousJobsPhotoIVId);
        nextJobsPhotoIV = findViewById(R.id.nextJobsPhotoIVId);
        middleJobsButton = findViewById(R.id.middleJobsButtonId);

        imagesString = getIntent().getStringExtra(Constant.ABMN);
        imagesType = getIntent().getStringExtra(Constant.ABMN_TYPE);

        setImage(photoPath, imagesString);

        setPhotoVisibleGone(photoPath);

        ApiConfig.checkConnection(activity);

        nextJobsPhotoIV.setOnClickListener(view -> {
            ApiConfig.checkConnection(activity);
            photoPath++;
            setImage(photoPath, imagesString);
            previousJobsPhotoIV.setVisibility(View.VISIBLE);
            setPhotoVisibleGone(photoPath);
        });
        previousJobsPhotoIV.setOnClickListener(view -> {
            ApiConfig.checkConnection(activity);
            photoPath--;
            setImage(photoPath, imagesString);
            nextJobsPhotoIV.setVisibility(View.VISIBLE);
            setPhotoVisibleGone(photoPath);
        });

        ApiConfig.autoNetCheck(activity);
    }

    private void setPhotoVisibleGone(int path) {
        if (path == 0) {
            previousJobsPhotoIV.setVisibility(View.INVISIBLE);
            nextJobsPhotoIV.setVisibility(View.VISIBLE);
        }
    }

    private void setImage(int photoPath, String imagesString) {

        try {
            JSONArray imagesArray;
            imagesArray = new JSONArray(imagesString);

            int localPath = photoPath + 1;

            if (imagesArray.length() == localPath) {
                nextJobsPhotoIV.setVisibility(View.INVISIBLE);
            }

            setPhotoVisibleGone(photoPath);
            String strPhotoPath = imagesArray.getString(photoPath);

            String strPhotoUrl = "";

            switch (imagesType) {
                case "photo":
                    strPhotoUrl = Constant.PHOTOS_PATH_URL + strPhotoPath;
                    break;
                case "blog":
                    strPhotoUrl = Constant.BLOGS_PATH_URL + strPhotoPath;
                    break;
                case "notice":
                    strPhotoUrl = Constant.NOTICES_PATH_URL + strPhotoPath;
                    break;
            }

            Glide.with(activity).load(strPhotoUrl).placeholder(R.drawable.app_logo).error(R.drawable.ic_baseline_error).into(jobsPhotoIV);

            String finalStrPhotoUrl = strPhotoUrl;
            middleJobsButton.setOnClickListener(v -> downloadThroughManager(finalStrPhotoUrl, activity));
//            downloadThroughManager(strPhotoUrl, activity);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("imageUrlException", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reload_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.reload_jobsId) {
            setImage(photoPath, imagesString);
        }
        return super.onOptionsItemSelected(item);
    }

    public static void downloadThroughManager(String imageUrl, Activity activity) {

        File path = new File(imageUrl);
        String fileName = path.getName();
        final DownloadManager downloadManager = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(imageUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(fileName);
        request.setDescription(fileName);
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        downloadManager.enqueue(request);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        @SuppressLint("RestrictedApi") final ForceStopRunnable.BroadcastReceiver receiver = new ForceStopRunnable.BroadcastReceiver() {
            public void onReceive(@NonNull Context context, Intent intent) {
                assert intent != null;
                long downloadReference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.i("GenerateTurePDfAsync", "Download completed");

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadReference);

                Cursor cur = downloadManager.query(query);

                if (cur.moveToFirst()) {
                    int columnIndex = cur.getColumnIndex(DownloadManager.COLUMN_STATUS);

                    if (DownloadManager.STATUS_SUCCESSFUL == cur.getInt(columnIndex)) {

                        Toast.makeText(activity, "File has been downloaded successfully.", Toast.LENGTH_SHORT).show();
                        ApiConfig.loadInterstitial(activity);

                    } else if (DownloadManager.STATUS_FAILED == cur.getInt(columnIndex)) {
                        int columnReason = cur.getColumnIndex(DownloadManager.COLUMN_REASON);
                        int reason = cur.getInt(columnReason);
                        switch (reason) {

                            case DownloadManager.ERROR_FILE_ERROR:
                                Toast.makeText(activity, "Download Failed.File is corrupt.", Toast.LENGTH_LONG).show();
                                break;
                            case DownloadManager.ERROR_HTTP_DATA_ERROR:
                                Toast.makeText(activity, "Download Failed.Http Error Found.", Toast.LENGTH_LONG).show();
                                break;
                            case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                                Toast.makeText(activity, "Download Failed due to insufficient space in internal storage", Toast.LENGTH_LONG).show();
                                break;

                            case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                                Toast.makeText(activity, "Download Failed. Http Code Error Found.", Toast.LENGTH_LONG).show();
                                break;
                            case DownloadManager.ERROR_UNKNOWN:
                                Toast.makeText(activity, "Download Failed.", Toast.LENGTH_LONG).show();
                                break;
                            case DownloadManager.ERROR_CANNOT_RESUME:
                                Toast.makeText(activity, "ERROR_CANNOT_RESUME", Toast.LENGTH_LONG).show();
                                break;
                            case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                                Toast.makeText(activity, "ERROR_TOO_MANY_REDIRECTS", Toast.LENGTH_LONG).show();
                                break;
                            case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                                Toast.makeText(activity, "ERROR_DEVICE_NOT_FOUND", Toast.LENGTH_LONG).show();
                                break;

                        }
                    }
                }
            }

        };
        activity.registerReceiver(receiver, filter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}