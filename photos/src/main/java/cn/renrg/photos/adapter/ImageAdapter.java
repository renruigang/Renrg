package cn.renrg.photos.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import cn.renrg.photos.R;
import cn.renrg.photos.listener.OnPictureSelectedListener;
import cn.renrg.photos.util.ImageSelect;
import cn.renrg.photos.util.ViewHolder;

public class ImageAdapter extends CommonAdapter<String> {

    private OnPictureSelectedListener onPictureSelectedListener;

    public ImageAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    public void setOnPictureSelectedListener(OnPictureSelectedListener onPictureSelectedListener) {
        this.onPictureSelectedListener = onPictureSelectedListener;
    }

    @Override
    public void convert(final ViewHolder helper, final String item) {
        //设置no_pic
        helper.setImageResource(R.id.item_picture, R.mipmap.default_picture);
        //设置图片
        helper.setImageByUrl(R.id.item_picture, item);

        final ImageView mImageView = helper.getView(R.id.item_picture);
        final CheckBox mSelect = helper.getView(R.id.item_selected);
        /**
         * 已经选择过的图片，显示出选择过的效果
         */
        if (ImageSelect.mSelectedImage.contains(item)) {
            mSelect.setChecked(true);
            mImageView.setColorFilter(Color.parseColor("#4C000000"));
        } else {
            mSelect.setChecked(false);
            mImageView.setColorFilter(null);
        }
        //设置ImageView的点击事件
        mSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed() && onPictureSelectedListener != null) {
                    onPictureSelectedListener.onPictureSelected(buttonView, mImageView, item, isChecked);
                }
            }
        });
    }
}
