package cn.renrg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipAdapter extends BaseAdapter {

    private Context context;
    private List<Vip> data;

    public VipAdapter(Context context, List<Vip> data) {
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_vip, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.vipItemName.setText(data.get(position).name);
        viewholder.vipItemMobile.setText(data.get(position).mobile);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.vip_item_name)
        TextView vipItemName;
        @BindView(R.id.vip_item_mobile)
        TextView vipItemMobile;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
