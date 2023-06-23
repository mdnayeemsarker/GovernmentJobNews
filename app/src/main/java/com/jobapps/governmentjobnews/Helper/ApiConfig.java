package com.jobapps.governmentjobnews.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.jobapps.governmentjobnews.Activity.CgpaActivity;
import com.jobapps.governmentjobnews.Activity.JobDetailsActivity;
import com.jobapps.governmentjobnews.Activity.JobShortListActivity;
import com.jobapps.governmentjobnews.Activity.JobsActivity;
import com.jobapps.governmentjobnews.Activity.MainActivity;
import com.jobapps.governmentjobnews.Activity.NewsActivity;
import com.jobapps.governmentjobnews.Activity.WebActivity;
import com.jobapps.governmentjobnews.R;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ApiConfig extends Application {

    public Activity activity;

    public ApiConfig(Activity activity) {
        this.activity = activity;
    }

    public static Boolean isConnected(final Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiNetwork != null && wifiNetwork.isConnected()) || (mobileNetwork != null && mobileNetwork.isConnected());
    }

    public static void isConnectedAlert(Activity activity) {
        if (!isConnected(activity)) {
            Session session = new Session(activity);
            session.setBoolean(Constant.IS_ALERT_NET, true);

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            final View customLayout = activity.getLayoutInflater().inflate(R.layout.lyt_alert_net_connection, null);
            builder.setView(customLayout);
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
            ImageView closeImageView = customLayout.findViewById(R.id.closeImageViewId);

            closeImageView.setOnClickListener(view -> {
                dialog.dismiss();
                session.setBoolean(Constant.IS_ALERT_NET, false);
                checkConnection(activity);
                if (isConnected(activity))
                    loadInterstitial(activity);
            });
        }
    }

    public static void checkConnection(Activity activity) {
        Session session = new Session(activity);
        if (isConnected(activity)) {
            if (checkVpnConnection(activity)) {
                Toast.makeText(activity, "" + session.getBoolean(Constant.IS_ALERT_VPN), Toast.LENGTH_SHORT).show();
                if (!session.getBoolean(Constant.IS_ALERT_VPN)) {
                    ApiConfig.vpnAlert(activity);
                }
            }
        } else {
            if (!session.getBoolean(Constant.IS_ALERT_NET)) {
                isConnectedAlert(activity);
            }
        }
    }

    public static void autoNetCheck(Activity activity) {
        new Handler().postDelayed(() -> {
            checkConnection(activity);
            autoNetCheckHelper(activity);
        }, 1000);
    }

    public static void autoNetCheckHelper(Activity activity) {
        new Handler().postDelayed(() -> autoNetCheck(activity), 2000);
    }

    public static boolean checkVpnConnection(Activity activity) {
        //this method doesn't work below API 21
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);
        return caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
    }

    public static void vpnAlert(Activity activity) {
        if (checkVpnConnection(activity)) {
            Session session = new Session(activity);
            session.setBoolean(Constant.IS_ALERT_VPN, true);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(activity.getString(R.string.alert));
            final View customLayout = activity.getLayoutInflater().inflate(R.layout.lyt_vpn, null);
            builder.setView(customLayout);

            builder.setPositiveButton(("ঠিক আছে"), (dialogInterface, i) -> {
                session.setBoolean(Constant.IS_ALERT_VPN, false);
                dialogInterface.dismiss();
                checkConnection(activity);
            }).setNegativeButton(activity.getString(R.string.no), (dialogInterface, i) -> {
                dialogInterface.dismiss();
                activity.finish();
            });
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public static void RequestToVolley(final VolleyCallback callback, int method, final Activity activity, final String url, final Map<String, String> params, final boolean isProgress) {

        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();

        if (isProgress)
            progressDisplay.showProgress();
        StringRequest stringRequest = new StringRequest(method, url, response -> {
            if (ApiConfig.isConnected(activity))
                callback.onSuccess(true, response);
            if (isProgress)
                progressDisplay.hideProgress();
        }, error -> {
            if (isProgress)
                progressDisplay.hideProgress();
            if (ApiConfig.isConnected(activity))
                callback.onSuccess(false, "");
            String message = VolleyErrorMessage(error);
            if (!message.equals(""))
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
            Log.d("volleyMessage", message);
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                params.put(Constant.ACCEPT, Constant.APPLICATIONJSON);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    public static void RequestToVolley(final VolleyCallback callback, Activity activity, String url, Map<String, String> params, Map<String, String> fileParams, boolean isProgress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();
        if (isProgress)
            progressDisplay.showProgress();
        RequestQueue queue = Volley.newRequestQueue(activity);

        VolleyMultiPartRequest multipartRequest = new VolleyMultiPartRequest(url,
                response -> {
                    callback.onSuccess(true, response);
                    if (isProgress)
                        progressDisplay.hideProgress();
                },
                error -> {
                    callback.onSuccess(false, error.getMessage());
                    if (isProgress)
                        progressDisplay.hideProgress();
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params1 = new HashMap<>();
                params1.put(Constant.ACCEPT, Constant.APPLICATIONJSON);
                Log.d("apiConfig", "getHeaders" + params1 + ", " + url);
                return params1;
            }

            @Override
            public Map<String, String> getDefaultParams() {
                return params;
            }

            @Override
            public Map<String, String> getFileParams() {
                Log.d("srParams", params.toString() + ", " + url + ", " + getHeaders() + ", " + fileParams.toString());
                return fileParams;
            }

        };
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(multipartRequest);
    }

    public static String VolleyErrorMessage(VolleyError error) {
        String message = "";
        try {
            if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!";
            } else if (error instanceof AuthFailureError) {
                message = "Your Login session will expired, Please Login Again";
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!";
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else
                message = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void transferActivity(Activity activity, String data, String send) {
        checkConnection(activity);
        Intent intent;
        switch (data) {
            case "back":
                intent = new Intent(activity, MainActivity.class);
                break;
            case "job_list":
                intent = new Intent(activity, JobsActivity.class);
                intent.putExtra(Constant.ABMN, send);
                break;
            case "short_list":
                intent = new Intent(activity, JobShortListActivity.class);
                break;
            case "news":
                intent = new Intent(activity, NewsActivity.class);
                break;
            case "cgpa":
                intent = new Intent(activity, CgpaActivity.class);
                break;
            case "job_details":
                intent = new Intent(activity, JobDetailsActivity.class);
                intent.putExtra(Constant.ABMN, send);
                loadInterstitial(activity);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + data);
        }
        activity.startActivity(intent);
    }

    public static void transferWebActivity(Activity activity, String data, String send) {
        checkConnection(activity);
        Intent myIntent = new Intent(activity, WebActivity.class);
        switch (data) {
            case "privacy":
                myIntent.putExtra(Constant.ABMN, Constant.PRIVACY_US_URL);
                break;
            case "about":
                myIntent.putExtra(Constant.ABMN, Constant.ABOUT_US_URL);
                break;
            case "trams_con":
                myIntent.putExtra(Constant.ABMN, Constant.TERMS_CON_URL);
                break;
            case "forget":
                myIntent.putExtra(Constant.ABMN, Constant.FORGET_URL);
                break;
            case "application_helper":
                myIntent.putExtra(Constant.ABMN, Constant.APPLICATION_HELPER_URL);
                break;
            case "apply_link":
            case "result":
                myIntent.putExtra(Constant.ABMN, send);
                break;
        }
        activity.startActivity(myIntent);
    }

    public static void shareApp(Activity activity) {
        String shareBody = "হাই বন্ধু, আমি (" + activity.getString(R.string.app_name) + ") এই অ্যাপ্লিকেশনটি ব্যবহার করছি, তুমি ও এটি ব্যবহার করতে পারো, লিংক-: https://play.google.com/store/apps/details?id="
                + activity.getPackageName();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sendIntent.setType("text/plain");
        Intent.createChooser(sendIntent, "Share via");
        activity.startActivity(sendIntent);
    }

    public static void rateMe(Activity activity) {
        checkConnection(activity);
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getPackageName())));
        } catch (android.content.ActivityNotFoundException e) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + activity.getPackageName() + "&hl=en")));
        }
    }

    public static void forceRating(Activity activity, Session session) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View customLayout = activity.getLayoutInflater().inflate(R.layout.lyt_force_rating, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        customLayout.findViewById(R.id.customUpdateCheckerBtnId).setOnClickListener(view -> {
            dialog.dismiss();
            session.setData(Constant.IS_RATING, "3");
            rateMe(activity);
        });
    }

    public static Age calculateAge(Date birthDate, Date eventDate) {
        int years;
        int months;
        int days;

        Calendar birthDay = Calendar.getInstance();
        Calendar eventDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        eventDay.setTimeInMillis(eventDate.getTime());

        years = eventDay.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = eventDay.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (eventDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && eventDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        //Calculate the days
        if (eventDay.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = eventDay.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (eventDay.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = eventDay.get(Calendar.DAY_OF_MONTH);
            eventDay.add(Calendar.MONTH, -1);
            days = eventDay.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        //Create new Age object
        return new Age(days, months, years);
    }

    public static class Age {

        private final int days;
        private final int months;
        private final int years;

        public Age(int days, int months, int years) {
            this.days = days;
            this.months = months;
            this.years = years;
        }

        public int getDays() {
            return days;
        }

        public int getMonths() {
            return months;
        }

        public int getYears() {
            return years;
        }

        @NonNull
        @Override
        public String toString() {
            return years + " Years, " + months + " Months, " + days + " Days";
        }
    }

    @SuppressLint("SetTextI18n")
    public static void openAgeCalculatorDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        @SuppressLint("InflateParams") final View customLayout = activity.getLayoutInflater().inflate(R.layout.lyt_age_calculator, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        ImageView closeImageView = customLayout.findViewById(R.id.closeImageViewId);

        EditText bDateInput = customLayout.findViewById(R.id.bDateId);
        EditText bMonthInput = customLayout.findViewById(R.id.bMonthId);
        EditText bYearInput = customLayout.findViewById(R.id.bYearId);

        EditText dDateInput = customLayout.findViewById(R.id.dDateId);
        EditText dMonthInput = customLayout.findViewById(R.id.dMonthId);
        EditText dYearInput = customLayout.findViewById(R.id.dYearId);

        Button calculateAgeButton = customLayout.findViewById(R.id.calculateAgeButtonId);
        TextView showAgeTextView = customLayout.findViewById(R.id.showAgeTextViewId);

        closeImageView.setOnClickListener(view -> {
            dialog.dismiss();
            if (ApiConfig.isConnected(activity)) {
                loadInterstitial(activity);
            }
        });

        calculateAgeButton.setOnClickListener(view -> {

            if (ApiConfig.isConnected(activity)) {
                loadInterstitial(activity);
            }

            String eDate = bDateInput.getText().toString();
            String eMonth = bMonthInput.getText().toString();
            String eYear = bYearInput.getText().toString();
            String dDate = dDateInput.getText().toString();
            String dMonth = dMonthInput.getText().toString();
            String dYear = dYearInput.getText().toString();

            if (eDate.isEmpty()) {
                bDateInput.requestFocus();
                bDateInput.setError("Field is Mandatory");
            } else if (eMonth.isEmpty()) {
                bMonthInput.requestFocus();
                bMonthInput.setError("Field is Mandatory");
            } else if (eYear.isEmpty()) {
                bYearInput.requestFocus();
                bYearInput.setError("Field is Mandatory");
            } else if (dDate.isEmpty()) {
                dDateInput.requestFocus();
                dDateInput.setError("Field is Mandatory");
            } else if (dMonth.isEmpty()) {
                dMonthInput.requestFocus();
                dMonthInput.setError("Field is Mandatory");
            } else if (dYear.isEmpty()) {
                dYearInput.requestFocus();
                dYearInput.setError("Field is Mandatory");
            } else {

                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = null;
                Date eventDate = null;
                try {
                    birthDate = sdf.parse(dDate + "/" + dMonth + "/" + dYear);
                    eventDate = sdf.parse(eDate + "/" + eMonth + "/" + eYear);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                assert birthDate != null;
                assert eventDate != null;
                Age age = calculateAge(birthDate, eventDate);

                showAgeTextView.setText("বিজ্ঞপ্তিতে উল্লেখিত তারিখে আপনার বয়সঃ \nদিন - " + age.getDays() + ", মাস - " + age.getMonths() + ", বছর - " + age.getYears());

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public static void navigationItem(Activity activity, int id, DrawerLayout drawer) {
        switch (id) {
            case R.id.nav_rating:
                drawer.closeDrawer(GravityCompat.START);
                ApiConfig.rateMe(activity);
                break;
            case R.id.nav_share:
                ApiConfig.shareApp(activity);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_about:
                ApiConfig.transferWebActivity(activity, "about", "");
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_policy:
                ApiConfig.transferWebActivity(activity, "privacy", "");
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_terms_con:
                ApiConfig.transferWebActivity(activity, "trams_con", "");
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_application_helper:
                drawer.closeDrawer(GravityCompat.START);
                ApiConfig.transferWebActivity(activity, "application_helper", "");
                break;
        }
    }

    public static void BannerAds(Activity activity) {
        MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Collections.singletonList("ABCDEF012345")).build());
        AdView adView = activity.findViewById(R.id.adView);
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(activity, initializationStatus -> {
        });
        @SuppressLint("VisibleForTests")
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public static void loadInterstitial(Activity activity) {

        @SuppressLint("VisibleForTests")
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, activity.getString(R.string.ADMOB_INTERSTITIAL_UNIT_ID), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                Log.d("interstitial", "onAdLoaded");
                interstitialAd.show(activity);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }
        });
    }

    public static void hideKeyboard(Activity activity, View root) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(root.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isEmail(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                + "((([0-1]?\\d{1,2}|25[0-5]|2[0-4]\\d)\\.([0-1]?"
                + "\\d{1,2}|25[0-5]|2[0-4]\\d)\\."
                + "([0-1]?\\d{1,2}|25[0-5]|2[0-4]\\d)\\.([0-1]?"
                + "\\d{1,2}|25[0-5]|2[0-4]\\d))|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isURL(CharSequence input) {
        Pattern URL_PATTERN = Patterns.WEB_URL;
        boolean isURL = URL_PATTERN.matcher(input).matches();
        if (!isURL) {
            String urlString = input + "";
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    new URL(urlString);
                    isURL = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return isURL;
    }

    public static void openAnotherApp(Activity activity, String type, String data) {
        Intent intent = null;
        switch (type) {
            case "phone":
                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + data));
                break;
            case "email":
                intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + data));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Job Apply");
                intent.putExtra(Intent.EXTRA_TEXT, "India Job App");
                break;
            case "web":
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
                break;
        }
        activity.startActivity(intent);
    }

}
