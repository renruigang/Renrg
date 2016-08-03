package cn.renrg.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.renrg.frame.volley.FileParams;
import cn.renrg.frame.volley.FileRequest;
import cn.renrg.frame.volley.GsonRequest;
import cn.renrg.frame.volley.ResponseCallBack;

public abstract class FrameActivity extends AppCompatActivity {

    private boolean cancelAble = true; //finish时请求是否可以取消
    private boolean loggable = false; //是否打印 response log

    public void setCancelAble(boolean cancelAble) {
        this.cancelAble = cancelAble;
    }

    public void setLoggable(boolean loggable) {
        this.loggable = loggable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameApplication.getInstance().addStackActivity(this);
        setLayoutView();
        initView();
        requestData();
        addViewListener();
    }

    /**
     * 初始化页面
     */
    protected abstract void setLayoutView();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 数据初始化
     */
    protected abstract void requestData();

    /**
     * 设置监听事件
     */
    protected abstract void addViewListener();

    protected void goToActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    protected void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void goToActivityForResult(Class activity, int code) {
        startActivityForResult(new Intent(this, activity), code);
    }

    protected void goToActivityForResult(Class activity, Bundle bundle, int code) {
        Intent intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivityForResult(intent, code);
    }

    public String getData(String url, HashMap<String, String> params, ResponseCallBack callBack) {
        params = params != null ? params : new HashMap<String, String>();
        final String api_url = url + "?" + getParamsToUrl(params);
        GsonRequest gsonRequest = new GsonRequest(api_url, callBack);
        gsonRequest.setShouldCache(false);
        addToRequestQueue(gsonRequest);
        return gsonRequest.getCacheKey();
    }

    public String getData(String url, HashMap<String, String> params, boolean isCache, ResponseCallBack callBack) {
        params = params != null ? params : new HashMap<String, String>();
        final String api_url = url + "?" + getParamsToUrl(params);
        GsonRequest gsonRequest = new GsonRequest(api_url, callBack);
        gsonRequest.setShouldCache(isCache);
        addToRequestQueue(gsonRequest);
        return gsonRequest.getCacheKey();
    }

    public String postData(String url, HashMap<String, String> params, ResponseCallBack callBack) {
        params = params != null ? params : new HashMap<String, String>();
        GsonRequest gsonRequest = new GsonRequest(url, params, callBack);
        gsonRequest.setShouldCache(false);
        addToRequestQueue(gsonRequest);
        return gsonRequest.getUrl();
    }

    public String postData(String url, HashMap<String, String> params, boolean isCache, ResponseCallBack callBack) {
        params = params != null ? params : new HashMap<String, String>();
        GsonRequest gsonRequest = new GsonRequest(url, params, callBack);
        gsonRequest.setShouldCache(isCache);
        addToRequestQueue(gsonRequest);
        return gsonRequest.getCacheKey();
    }

    public void postFileData(String url, FileParams params, ResponseCallBack callBack) {
        FileRequest gsonRequest = new FileRequest(url, params, callBack);
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(180 * 1000, 1, 1.0f));
        addToRequestQueue(gsonRequest);
    }

    protected <T> void addToRequestQueue(Request<T> req) {
        req.setTag(getLocalClassName());
        if (req instanceof GsonRequest) {
            ((GsonRequest) req).setLoggable(loggable);
        }
        FrameApplication.getInstance().getRequestQueue().add(req);
    }

    protected String getParamsToUrl(Map<String, String> keyMaps) {
        StringBuilder result = new StringBuilder();
        if (keyMaps == null) {
            return "";
        }
        for (ConcurrentHashMap.Entry<String, String> entry : keyMaps.entrySet()) {
            if (result.length() > 0) {
                result.append("&");
            }
            if (TextUtils.isEmpty(entry.getValue())) {
                continue;
            }
            result.append(entry.getKey());
            result.append("=");
            try {
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                result.append(entry.getValue());
            }
        }
        return result.toString();
    }

    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cancelAble) {
            cancelAllRequest();
        }
    }

    protected void cancelAllRequest() {
        FrameApplication.getInstance().getRequestQueue().cancelAll(getLocalClassName());
    }

}

