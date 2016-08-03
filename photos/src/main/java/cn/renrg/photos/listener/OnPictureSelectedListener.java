package cn.renrg.photos.listener;

import android.widget.CompoundButton;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/21.
 */
public interface OnPictureSelectedListener {
    void onPictureSelected(CompoundButton buttonView, ImageView mImageView, String path, boolean checked);
}
