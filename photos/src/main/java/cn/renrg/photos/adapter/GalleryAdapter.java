package cn.renrg.photos.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.renrg.photos.widget.PhotoView;

/**
 * Created by Administrator on 2016/7/22.
 */
public class GalleryAdapter extends PagerAdapter {

    private Context context;
    private List<String> data;
    private View[] listViews;
    private int width, height;

    public GalleryAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        listViews = new View[data.size()];
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        width = dm2.widthPixels;
        height = dm2.heightPixels;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (listViews[position] != null) {
            container.removeView(listViews[position]);
            listViews[position] = null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (listViews[position] == null) {
            listViews[position] = getImageView(data.get(position));
        }
        container.addView(listViews[position]);
        return listViews[position];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private View getImageView(String path) {
        PhotoView img = new PhotoView(context);
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(convertToBitmap(path, width, height));
        img.setTag(path);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return img;
    }

    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }
}
