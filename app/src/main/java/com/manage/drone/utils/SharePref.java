package com.manage.drone.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class SharePref {
    private Context context;
    private SharedPreferences preferences;
    private static final String NAME_PREFS="tuongtacnguoimay";
    public static final String NAME_SWITCH="switch_send_data";
    public static final String NAME_FIRST_OPEN="first_open";
    public static final String NOT_LOGIN ="is_login";
    public static final String NAME_HEIGHT="height";
    public static final String NAME_SPEED="speed";

    public SharePref(Context context) {
        this.context = context;
        preferences=context.getSharedPreferences(NAME_PREFS,Context.MODE_PRIVATE);
    }

    public void putBoolean(String key,boolean value){
        preferences.edit().putBoolean(key,value).apply();
    }
    public boolean getBooleanValue(String key){
        return preferences.getBoolean(key,true);
    }


}
