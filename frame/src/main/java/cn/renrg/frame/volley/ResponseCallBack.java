package cn.renrg.frame.volley;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 */
public abstract class ResponseCallBack<T> {

    public Response.ErrorListener errorListener;
    public Response.Listener<BaseModel> listener;

    public ResponseCallBack(final Context context) {
        listener = new Response.Listener<BaseModel>() {
            @Override
            public void onResponse(BaseModel response) {
                if (response.isSuccess()) {//成功
                    onSuccessResponse((T) new Gson().fromJson(response.getData(), getGenericType(0)), response.getErrorCode(), response.getErrorMsg());
                } else {//失败
                    onFailResponse(response.getErrorCode(), response.getErrorMsg());
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
