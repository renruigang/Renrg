package cn.renrg.frame;

import android.app.Activity;
import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Stack;

/**
 * Created by Hello on 2015/8/18.
 */
public class FrameApplication extends Application {

    private static FrameApplication frameApplication;
    private Stack<Activity> activityStack = new Stack<>();
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        frameApplication = this;
    }

    public void exitApplication() {
        clearStackActivity();
        System.exit(0);
    }

    public static FrameApplication getInstance() {
        return frameApplication;
    }

    public void addStackActivity(Activity context) {
        activityStack.add(context);
    }

    public void clearStackActivity() {
        for (Activity context : activityStack) {
            context.finish();
        }
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (this) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley
                            .newRequestQueue(getApplicationContext());
                }
            }
        }
        return mRequestQueue;
    }
}
