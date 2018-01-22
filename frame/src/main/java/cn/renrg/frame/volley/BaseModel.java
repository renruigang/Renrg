package cn.renrg.frame.volley;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by renruigang on 2015/8/25.
 */
public class BaseModel {

    //0-没有数据，1-成功，-9-登录过期，-1错误
    private String status;
    private JsonElement data;
    public String msg;

    public BaseModel(JsonObject jsonObject, String key) {
        status = jsonObject.get("status").toString().replace("\"", "");
        msg = jsonObject.get("msg").toString().replace("\"", "");
        data = jsonObject.get(key);
    }

    public BaseModel(JsonObject jsonObject) {
        status = jsonObject.get("status").toString().replace("\"", "");
        msg = jsonObject.get("msg").toString().replace("\"", "");
    }

    public boolean isSuccess() {
        return "1".equals(status);
    }

    public boolean isNoData() {
        return "0".equals(status);
    }

    public boolean isError() {
        return !"1".equals(status) && !"0".equals(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
