package cn.renrg.frame.volley;

import com.google.gson.JsonElement;

public class BaseModel {
    private String status;
    private JsonElement result;

    public JsonElement getData() {
        return result;
    }

    public String describe;

    public String getErrorCode() {
        return status;
    }

    public String getErrorMsg() {
        return describe;
    }


    /**
     *
     */
    public boolean isSuccess() {
        if ("0".equals(status)) {
            return true;
        }
        return false;
    }

    public boolean isNoData() {
        if ("2".equals(status)) {
            return true;
        }
        return false;
    }

    public boolean isOutToken() {
        if ("F00005".equals(status)) {
            return true;
        }
        return false;
    }

}
