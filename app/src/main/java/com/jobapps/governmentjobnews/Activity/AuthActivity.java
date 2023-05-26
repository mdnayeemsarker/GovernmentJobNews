package com.jobapps.governmentjobnews.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.R;
import com.jobapps.governmentjobnews.ui.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private EditText edtLoginEmail;
    private EditText edtLoginPassword;

    private EditText edtSignUpPassword;
    private EditText edtSignUpEmail;

    public Activity activity;
    public Session session;

    private ScrollView lytLogin;
    private ScrollView lytSignup;
    private String getIntentData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name) + " Sign In");
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        activity = this;
        session = new Session(activity);

        getIntentData = getIntent().getStringExtra(Constant.ABMN);

        lytLogin = findViewById(R.id.lytLogin);
        lytSignup = findViewById(R.id.lytSignup);

        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);

        edtLoginPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pass, 0, R.drawable.ic_show, 0);
        edtSignUpPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pass, 0, R.drawable.ic_show, 0);

        Utils.setHideShowPassword(edtLoginPassword);
        Utils.setHideShowPassword(edtSignUpPassword);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignup = findViewById(R.id.btnSignup);

        Button btnSignUpSignup = findViewById(R.id.btnSignUpSignup);
        Button btnSignUpLogin = findViewById(R.id.btnSignUpLogin);

        ApiConfig.checkConnection(activity);

        findViewById(R.id.forgetPassword).setOnClickListener(v -> ApiConfig.transferWebActivity(activity, "forget", ""));
        btnLogin.setOnClickListener(view -> {
            ApiConfig.checkConnection(activity);
            ApiConfig.hideKeyboard(activity, view);
            loginCheck();
        });
        btnSignup.setOnClickListener(view -> {
            ApiConfig.checkConnection(activity);
            loginLayout(view, Constant.ABMN_REGISTER);
        });

        btnSignUpLogin.setOnClickListener(view -> {
            ApiConfig.checkConnection(activity);
            loginLayout(view, Constant.ABMN_LOGIN);
        });
        btnSignUpSignup.setOnClickListener(view -> {
            ApiConfig.checkConnection(activity);
            ApiConfig.hideKeyboard(activity, view);
            signupCheck();
        });
        ApiConfig.autoNetCheck(activity);
    }

    private void signupCheck() {

        String email = edtSignUpEmail.getText().toString();
        String password = edtSignUpPassword.getText().toString();

//        if (first_name.isEmpty()) {
//            edtFirstName.requestFocus();
//            edtFirstName.setError("First Name field are mandatory.!");
//        } else if (last_name.isEmpty()) {
//            edtLastName.requestFocus();
//            edtLastName.setError("Last Name field are mandatory.!");
//        } else
        if (email.isEmpty()) {
            edtSignUpEmail.requestFocus();
            edtSignUpEmail.setError("Email field are mandatory.!");
        } else if (password.isEmpty()) {
            edtSignUpPassword.requestFocus();
            edtSignUpPassword.setError("Password field are mandatory.!");
        } else {
            signup(email, password);
        }
    }

    private void signup(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.FIRST_NAME, "first_name");
        params.put(Constant.MIDDLE_NAME, " Middle_Name ");
        params.put(Constant.LAST_NAME, "last_name");
        params.put(Constant.EMAIL, email);
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(Constant.STATUS).equals(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Session session = new Session(activity);
                        session.setBoolean(Constant.IS_LOGIN, true);
                        session.setData(Constant.TOKEN, jsonObject.getString(Constant.TOKEN));
                        transfer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("eRegister", e.getMessage());
                }
            }
            Log.d("rRegister", response);
        }, activity, Constant.AUTH_REGISTER_URL, params, true);
    }

    private void loginCheck() {
        String email = edtLoginEmail.getText().toString();
        String password = edtLoginPassword.getText().toString();

        if (email.isEmpty()) {
            edtLoginEmail.requestFocus();
            edtLoginEmail.setError("Mobile field are mandatory.!");
        } else if (password.isEmpty()) {
            edtLoginPassword.requestFocus();
            edtLoginPassword.setError("Password field are mandatory.!");
        } else {
            login(email, password);
        }
    }

    private void login(String email, String password) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.EMAIL, email);
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString(Constant.STATUS).equals(Constant.SUCCESS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Session session = new Session(activity);
                        session.setBoolean(Constant.IS_LOGIN, true);
                        session.setData(Constant.TOKEN, jsonObject.getString(Constant.TOKEN));
                        transfer();
                    } else if (jsonObject.getString(Constant.STATUS).equals(Constant.ERROR)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("eLogin", e.getMessage());
                }
            }
            Log.d("rLogin", response);
        }, activity, Constant.AUTH_LOGIN_URL, params, true);
    }

    private void transfer() {
        switch (getIntentData) {
            case "":
                startActivity(new Intent(activity, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "study":
                startActivity(new Intent(activity, BlogActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "live":
                startActivity(new Intent(activity, WebActivity.class).putExtra(Constant.ABMN, "https://hjhjkmnjkjlikjhjbn.blogspot.com/p/live-class.html").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "video":
                startActivity(new Intent(activity, WebActivity.class).putExtra(Constant.ABMN, "https://hjhjkmnjkjlikjhjbn.blogspot.com").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "exam_result":
                startActivity(new Intent(activity, NoticeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "short_list":
                startActivity(new Intent(activity, JobShortListActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "favorite":
            case "applied":
//                ApiConfig.transferActivity(activity, "favorite_applied_list", getIntentData);
                startActivity(new Intent(activity, FavoriteJobActivity.class).putExtra(Constant.ABMN, getIntentData).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            default:
                startActivity(new Intent(activity, JobDetailsActivity.class).putExtra(Constant.ABMN, getIntentData).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }

    private void loginLayout(View view, String data) {

        ApiConfig.hideKeyboard(activity, view);
        if (data.equals(Constant.ABMN_LOGIN)) {
            lytLogin.setVisibility(View.VISIBLE);
            lytSignup.setVisibility(View.GONE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.app_name) + " Sign In");
            }
        } else if (data.equals(Constant.ABMN_REGISTER)) {
            lytLogin.setVisibility(View.GONE);
            lytSignup.setVisibility(View.VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.app_name) + " Sign Up");
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}