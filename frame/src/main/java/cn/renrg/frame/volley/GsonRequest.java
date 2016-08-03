package cn.renrg.frame.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hello on 2015/8/22.
 */
public class GsonRequest<T extends BaseModel> extends Request<T> {

    private final Listener<T> mListener;
    private Gson mGson;
    private Map<String, String> params;
    private final String TYPE_UTF8_CHARSET = "charset=UTF-8";
    private boolean loggable = false;

    public void setLoggable(boolean loggable) {
        this.loggable = loggable;
    }

    public GsonRequest(String url, Map<String, String> params, ResponseCallBack responseCallBack) {
        super(Method.POST, url, responseCallBack.errorListener);
        mGson = new Gson();
        this.params = removeNull(params);
        mListener = responseCallBack.listener;
    }

    @Override
    public String getCacheKey() {
        return getUrl() + params.toString();
    }

    public GsonRequest(String url, ResponseCallBack responseCallBack) {
        super(Method.GET, url, responseCallBack.errorListener);
        mGson = new Gson();
        mListener = responseCallBack.listener;
    }

    private Map<String, String> removeNull(Map<String, String> map) {
        if (map.containsValue(null)) {
            Set<Map.Entry<String, String>> mapEntries = params.entrySet();
            Iterator it = mapEntries.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getValue() == null) {
                    it.remove();
                    continue;
                }
            }
        }
        return map;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String type = response.headers.get("Content-Type");
            if (type == null) {
                type = TYPE_UTF8_CHARSET;
                response.headers.put("Content-Type", type);
            } else if (!type.contains("UTF-8")) {
                type += ";" + TYPE_UTF8_CHARSET;
                response.headers.put("Content-Type", type);
            }
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            if (loggable) {
                Log.e("Response", jsonString);
            }
            return Response.success(mGson.fromJson(jsonString, BaseModel.class), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}