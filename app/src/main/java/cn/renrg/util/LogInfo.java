package cn.renrg.util;

import android.util.Log;

import cn.renrg.BuildConfig;
import cn.renrg.comm.Constants;


public class LogInfo {

    private static boolean loggable = BuildConfig.DEBUG;
    private final static String tag = Constants.MY_APP;

    public static void e(String msg) {
        if (loggable) {
            Log.e(tag, "" + msg);
        }
    }
}
