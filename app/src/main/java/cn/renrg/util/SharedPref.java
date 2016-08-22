package cn.renrg.util;

import android.content.Context;
import android.content.SharedPreferences;

import cn.renrg.comm.Constants;

/**
 * Created by Hello on 2015/8/18.
 */
public class SharedPref {
    private SharedPreferences preferences;

    public SharedPref(Context context) {
        preferences = context.getSharedPreferences(Constants.MY_APP, Context.MODE_PRIVATE);
    }

    public void setInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public void setString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void setBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

}
