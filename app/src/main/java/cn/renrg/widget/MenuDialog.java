package cn.renrg.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cn.renrg.R;
import cn.renrg.frame.widget.UnScrollListView;

/**
 * Created by Administrator on 2016/7/4.
 */
public class MenuDialog extends Dialog {

    private Context context;
    private TextView cancel;
    private UnScrollListView menuList;

    public MenuDialog(Context context) {
        super(context, R.style.menu_dialog_style);
        this.context = context;
        setContentView(R.layout.layout_menu_dialog);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.menu_dialog_anim_style);
        cancel = (TextView) findViewById(R.id.dialog_menu_cancel);
        menuList = (UnScrollListView) findViewById(R.id.dialog_menu_list);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setMenuItems(String[] menuItems, final OnMenuItemClickedListener listener) {
        menuList.setAdapter(new ArrayAdapter(context, R.layout.layout_menu_dialog_item, menuItems));
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.MenuItemClicked(position);
                dismiss();
            }
        });
    }

    public interface OnMenuItemClickedListener {
        void MenuItemClicked(int position);
    }

}
