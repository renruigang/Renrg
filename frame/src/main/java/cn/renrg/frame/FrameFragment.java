package cn.renrg.frame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.HashMap;

import cn.renrg.frame.volley.FileParams;
import cn.renrg.frame.volley.ResponseCallBack;

public abstract class FrameFragment extends Fragment {

    public void getData(String url, HashMap<String, String> params, ResponseCallBack callBack) {
        ((FrameActivity) getActivity()).getData(url, params, callBack);
    }

    public void postData(String url, HashMap<String, String> params, ResponseCallBack callBack) {
        ((FrameActivity) getActivity()).postData(url, params, callBack);
    }

    public void getData(String url, HashMap<String, String> params, boolean isCache, ResponseCallBack callBack) {
        ((FrameActivity) getActivity()).getData(url, params, isCache, callBack);
    }

    public void postData(String url, HashMap<String, String> params, boolean isCache, ResponseCallBack callBack) {
        ((FrameActivity) getActivity()).postData(url, params, isCache, callBack);
    }

    public void postFileData(String url, FileParams params, ResponseCallBack callBack) {
        ((FrameActivity) getActivity()).postFileData(url, params, callBack);
    }

    public void showToast(String info) {
        Toast.makeText(getActivity(), info, Toast.LENGTH_SHORT).show();
    }

    protected void goToActivity(Class activity) {
        startActivity(new Intent(getActivity(), activity));
    }

    protected void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void goToActivityForResult(Class activity, int code) {
        startActivityForResult(new Intent(getActivity(), activity), code);
    }

    protected void goToActivityForResult(Class activity, Bundle bundle, int code) {
        Intent intent = new Intent(getActivity(), activity);
        intent.putExtras(bundle);
        startActivityForResult(intent, code);
    }

}