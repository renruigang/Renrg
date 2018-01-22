package cn.renrg.frame.volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by renruigang on 2015/8/25.
 */
public abstract class ResponseCallBack<T> {

    public Response.ErrorListener errorListener;
    public Response.Listener<JsonObject> listener;

    public ResponseCallBack(final Context context) {
        //默认返回Json标准格式status，data,msg三个字段，解析data数据
        this(context, "data");
    }

    public ResponseCallBack(final Context context, boolean illegal) {
        listener = new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                BaseModel baseModel = new BaseModel(response);
                if (baseModel.isSuccess()) {//成功
                    onSuccessResponse((T) new Gson().fromJson(response, getGenericType(0)),
                            baseModel.getStatus(), baseModel.getMsg());
                } else if (baseModel.isNoData()) {//没有数据
                    onNodataResponse(baseModel.getStatus(), baseModel.getMsg());
                } else {
                    onFailResponse(baseModel.getStatus(), baseModel.getMsg());
                }
            }
        };
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onFailResponse("V", VolleyErrorHelper.getMessage(error, context));
            }
        };
    }

    public ResponseCallBack(final Context context, final String key) {
        listener = new Response.Listener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response) {
                BaseModel baseModel = new BaseModel(response, key);
                if (baseModel.isSuccess()) {//成功
                    onSuccessResponse((T) new Gson().fromJson(baseModel.getData(), getGenericType(0)),
                            baseModel.getStatus(), baseModel.getMsg());
                } else if (baseModel.isNoData()) {//没有数据
                    onNodataResponse(baseModel.getStatus(), baseModel.getMsg());
                } else {
                    onFailResponse(baseModel.getStatus(), baseModel.getMsg());
                }
            }
        };
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onFailResponse("V", VolleyErrorHelper.getMessage(error, context));
            }
        };
    }

    /**
     */
    public abstract void onSuccessResponse(T d, String code, String msg);

    /**
     */
    public void onNodataResponse(String code, String msg) {
    }

    /**
     */
    public abstract void onFailResponse(String code, String msg);

    //
    public Type getGenericType(int index) {
        Type genType = getClass().getGenericSuperclass();//返回本类的父类
        if (!(genType instanceof ParameterizedType)) { //ParameterizedType 表示参数化类型
            return genType;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); //【4】 得到泛型里的class类型对象。
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
//        LogInfo.e("类型:"+ params[index].toString());
//        if (!(params[index] instanceof Class)) {
//            return Object.class;
//        }
        return params[index];
    }

}
