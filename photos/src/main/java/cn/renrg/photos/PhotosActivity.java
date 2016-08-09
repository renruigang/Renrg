package cn.renrg.photos;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.renrg.photos.adapter.ImageAdapter;
import cn.renrg.photos.bean.ImageFloder;
import cn.renrg.photos.listener.OnPictureSelectedListener;
import cn.renrg.photos.util.ImageSelect;
import cn.renrg.photos.widget.ListImageDirPopupWindow;

public class PhotosActivity extends AppCompatActivity implements ListImageDirPopupWindow.OnImageDirSelected {

    private final int REQUEST_PREVIEW = 100;
    private ProgressDialog mProgressDialog;
    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();
    /**
     * 扫描拿到所有的图片文件夹
     */
    private ArrayList<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();
    private ArrayList<String> mImagePaths = new ArrayList<>();

    private GridView mGirdView;
    private ImageAdapter mAdapter;
    private ImageView toolbarBack;
    private Button toolbarSure;
    private TextView mChooseDir;
    private TextView preview;
    private View cover;
    private RelativeLayout mBottomLy;
    private int mScreenHeight;
    private ListImageDirPopupWindow mListImageDirPopupWindow;
    private ImageFloder allImages;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw
            initListDirPopupWindw();
        }
    };

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImageFloders.size() == 0) {
            Toast.makeText(getApplicationContext(), "未发现图片资源", Toast.LENGTH_SHORT).show();
            return;
        }
        allImages.setCount(mPicsSize);
        allImages.setDir("all");
        mImageFloders.add(0, allImages);
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mImagePaths.clear();
        mImagePaths.addAll(allImages.getImgPaths());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_list_floder, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                cover.setVisibility(View.GONE);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;

        initView();
        getImages();
        initEvent();
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        allImages = new ImageFloder();
        allImages.setName("所有图片");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PhotosActivity.this.getContentResolver();
                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                        addImagePaths(imageFloder);
                    }
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    imageFloder.setCount(picSize);
                    mPicsSize += picSize;
                    mImageFloders.add(imageFloder);
                }
                mCursor.close();
                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;
                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);
            }
        }).start();
    }

    /**
     * 初始化View
     */
    private void initView() {
        toolbarBack = (ImageView) findViewById(R.id.toolbar_iv_back);
        toolbarSure = (Button) findViewById(R.id.toolbar_btn_sure);
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        preview = (TextView) findViewById(R.id.id_total_count);
        mBottomLy = (RelativeLayout) findViewById(R.id.bottom_layout);
        cover = findViewById(R.id.home_view_cover);
        mAdapter = new ImageAdapter(getApplicationContext(), mImagePaths, R.layout.item_grid_image);
        mGirdView.setAdapter(mAdapter);
    }

    private void initEvent() {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mChooseDir) {
                    showFloderSelected();
                } else if (v == toolbarSure) {
                    finish();
                } else if (v == toolbarBack) {
                    onBackPressed();
                } else if (v == preview) {
                    if (ImageSelect.mSelectedImage.size() > 0) {
                        goToPreview(new ArrayList<String>(ImageSelect.mSelectedImage));
                    }
                }
            }
        };
        mChooseDir.setOnClickListener(onClickListener);
        toolbarSure.setOnClickListener(onClickListener);
        toolbarBack.setOnClickListener(onClickListener);
        preview.setOnClickListener(onClickListener);
        mGirdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToPreview(mImagePaths);
            }
        });
        mAdapter.setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected() {
                refreshButton();
            }
        });
    }

    private void goToPreview(ArrayList<String> data) {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putStringArrayListExtra("path", data);
        startActivityForResult(intent, REQUEST_PREVIEW);
    }

    private void showFloderSelected() {
        if (mListImageDirPopupWindow.isShowing()) {
            mListImageDirPopupWindow.dismiss();
            // 设置背景颜色变暗
            cover.setVisibility(View.GONE);
        } else {
            mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
            mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);
            // 设置背景颜色变暗
            cover.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshButton();
    }

    private void refreshButton() {
        if (ImageSelect.mSelectedImage.size() == 0) {
            toolbarSure.setEnabled(false);
            preview.setEnabled(false);
            toolbarSure.setText("完成");
            preview.setText("预览");
        } else {
            toolbarSure.setEnabled(true);
            preview.setEnabled(true);
            toolbarSure.setText("完成(" + ImageSelect.mSelectedImage.size() + "/" + ImageSelect.MAX_SIZE + ")");
            preview.setText("预览(" + ImageSelect.mSelectedImage.size() + ")");
        }
    }

    @Override
    public void selected(ImageFloder floder) {
        cover.setVisibility(View.GONE);
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mImagePaths.clear();
        mImagePaths.addAll(floder.getImgPaths());
        mAdapter.notifyDataSetChanged();
    }

    public void addImagePaths(ImageFloder floder) {
        File mImgDir = new File(floder.getDir());
        List<String> mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg"))
                    return true;
                return false;
            }
        }));
        ArrayList<String> imagePaths = new ArrayList<>();
        for (String image : mImgs) {
            imagePaths.add(floder.getDir() + File.separator + image);
        }
        floder.setImgPaths(imagePaths);
        allImages.addImgPaths(imagePaths);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_PREVIEW) {
            if (data.getBooleanExtra("finish", false)) {
                finish();
            }
        }
    }
}
