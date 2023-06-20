package com.jobapps.governmentjobnews.Helper;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;

public class Session {

    public static final String PREFER_NAME = "abmnGovJob";
    final int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Activity activity;

    public Session(Activity activity) {
        try {
            this.activity = activity;
            pref = activity.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(String id, String val) {
        editor.putString(id, val);
        editor.commit();
    }

    public String getData(String id) {
        return pref.getString(id, "");
    }

    public void setBoolean(String id, boolean val) {
        editor.putBoolean(id, val);
        editor.commit();
    }

    public boolean getBoolean(String id) {
        return pref.getBoolean(id, false);
    }


//    public void IS_LOGGED_IN(Activity activity, String data) {
//        if (!pref.getBoolean(Constant.IS_LOGIN, false)) {
//            Intent i = new Intent(activity, AuthActivity.class);
//            i.putExtra(Constant.ABMN, data);
//            activity.startActivity(i);
//            activity.finish();
//        }
//        pref.getBoolean(Constant.IS_LOGIN, false);
//    }

//    public void saveJSONArray(JSONArray list, String key){
//        editor.putString(key, list.toString());
//        editor.apply();     // This line is IMPORTANT !!!
//        editor.commit();
//    }
//
//    public JSONArray getJSONArray(String key){
//        String json = pref.getString(key, null);
//        try {
//            return new JSONArray(json);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
