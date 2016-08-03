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

public class ProjectAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    public ProjectAdapter(Context context, List<String> data) {
        super();
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 10;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_project, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.projectItemBiding.setText("本期中标人：张三");
        viewholder.projectItemIndex.setText("期 数：15");
        viewholder.projectItemDate.setText("中标时间：2016-07-16");
        viewholder.projectItemSum.setText("总人数：45");
        viewholder.projectItemBegin.setText("创建时间：2016-01-16");
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.project_item_biding)
        TextView projectItemBiding;
        @BindView(R.id.project_item_index)
        TextView projectItemIndex;
        @BindView(R.id.project_item_date)
        TextView projectItemDate;
        @BindView(R.id.project_item_sum)
        TextView projectItemSum;
        @BindView(R.id.project_item_begin)
        TextView projectItemBegin;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
