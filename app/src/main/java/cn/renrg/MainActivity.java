package cn.renrg;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.renrg.frame.FrameActivity;
import cn.renrg.photos.PhotosActivity;
import cn.renrg.photos.util.ImageSelect;
import cn.renrg.widget.MenuDialog;

public class MainActivity extends FrameActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_image)
    ImageView mainImage;

    @Override
    protected void setLayoutView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Glide.with(this).load(R.mipmap.space).into(mainImage);
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new:
                goToActivity(PhotosActivity.class);
                break;
            case R.id.menu_pop:
                showMenuDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showToast("选择" + ImageSelect.mSelectedImage.size() + "张图片");
        ImageSelect.mSelectedImage.clear();
    }

    @Override
    protected void addViewListener() {

    }

    private void showMenuDialog() {
        MenuDialog dialog = new MenuDialog(this);
        dialog.setMenuItems(getResources().getStringArray(R.array.menu_array), new MenuDialog.OnMenuItemClickedListener() {
            @Override
            public void MenuItemClicked(int position) {
                showToast("item" + position);
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
