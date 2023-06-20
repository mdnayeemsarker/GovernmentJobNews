package com.jobapps.governmentjobnews.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.canhub.cropper.CropImage;
import com.jobapps.governmentjobnews.Helper.ApiConfig;
import com.jobapps.governmentjobnews.Helper.Constant;
import com.jobapps.governmentjobnews.Helper.FilePath;
import com.jobapps.governmentjobnews.Helper.Session;
import com.jobapps.governmentjobnews.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class CourseRegActivity extends AppCompatActivity {

    private Activity activity;
    private String getCourseId;
    private int tempCode = 0;
    private ImageView proofIV, photoIV;
    private EditText nameET, phoneET, emailET, trxET;
    private TextView instructionTV;
    private Boolean isProfile = false, isProof = false, isCode = false;
    private HashMap<String, String> fileUpMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_reg);

        define();
        work();

    }

    private void define() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_back_button);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        activity = this;

        getCourseId = getIntent().getStringExtra("course_id");

        instructionTV = findViewById(R.id.instructionTV);

        nameET = findViewById(R.id.nameET);
        phoneET = findViewById(R.id.phoneET);
        emailET = findViewById(R.id.emailET);
        trxET = findViewById(R.id.trxET);

        photoIV = findViewById(R.id.photoIV);
        proofIV = findViewById(R.id.proofIV);

        fileUpMap = new HashMap<>();

        getCompanyData();

    }

    private void work() {

        photoIV.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                imgRequest(110);
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
            }
        });
        proofIV.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                imgRequest(111);
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
            }
        });

        findViewById(R.id.submitBtn).setOnClickListener(v -> checkSubmit());

    }


    private void getCompanyData() {
        ApiConfig.RequestToVolley((result, response) -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject setting = data.getJSONObject("setting");
                String payment_number = setting.getString("payment_number");

                instructionTV.setText(payment_number);

            } catch (Exception e) {
                Log.d("company", e.getMessage());
            }
        }, Request.Method.GET, activity,  Constant.API_PATH + "setting", new HashMap<>(), false);
    }

    private void checkSubmit() {

        Session session = new Session(activity);
        String name = nameET.getText().toString();
        String phone = phoneET.getText().toString();
        String email = emailET.getText().toString();
        String trx = trxET.getText().toString();

        if (TextUtils.isEmpty(name)) {
            setEdtError(nameET, "Name is mandatory.!");
        } else if (TextUtils.isEmpty(phone)) {
            setEdtError(phoneET, "Phone is mandatory.!");
        } else if (TextUtils.isEmpty(email)) {
            setEdtError(emailET, "Gmail is mandatory.!");
        } else if (!isProfile) {
            Toast.makeText(activity, "Please select an profile image, thank you.!", Toast.LENGTH_SHORT).show();
        }else if (!isProof) {
            Toast.makeText(activity, "Please select an payment proof image, thank you.!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(trx)) {
            setEdtError(trxET, "Complain details is mandatory.!");
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("email", email);
            params.put("phone", phone);
            params.put("trx_id", trx);

            ApiConfig.RequestToVolley((result, response) -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("status");
                    String messageOnline = jsonObject.getString("message");

                    if (status) {
                        Toast.makeText(this, messageOnline, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity, MainActivity.class));
                        session.setBoolean("reg", false);
                    }

                } catch (Exception e) {
                    Log.d("complain", e.getMessage());
                }
            }, activity, Constant.API_PATH + "enroll/" + getCourseId, params, fileUpMap, true);

        }

    }

    private void setEdtError(EditText editText, String data) {
        editText.requestFocus();
        editText.setError(data);
    }

    private void imgRequest(int i) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        if (requestCode == 110 || requestCode == 210 || requestCode == 310) {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "We need your Media permission to select an image from gallery", Toast.LENGTH_LONG).show();
        } else {
            imgRequest(requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!isCode) {
            tempCode = requestCode;
            isCode = true;
        }

        if (resultCode == RESULT_OK) {
            Uri imageUri;
            if (requestCode == 110 || requestCode == 111) {
                assert data != null;
                imageUri = data.getData();
                FilePath.crop(activity, imageUri);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (tempCode == 110) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    assert result != null;
                    FilePath.setFilePathWithImage(activity, result, "photo", fileUpMap, photoIV);
                    isProfile = true;
                    isCode = false;
                }else if (tempCode == 111) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    assert result != null;
                    FilePath.setFilePathWithImage(activity, result, "payment_proof", fileUpMap, proofIV);
                    isCode = false;
                    isProof = true;
                }
            }
        }
    }

}