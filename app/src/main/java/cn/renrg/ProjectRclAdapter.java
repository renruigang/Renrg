package cn.renrg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProjectRclAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<String> list;
    private OnItemClickListener listener;

    public ProjectRclAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.projectItemBiding.setText("本期中标人：张三");
            itemViewHolder.projectItemIndex.setText("期 数：15");
            itemViewHolder.projectItemDate.setText("中标时间：2016-07-16");
            itemViewHolder.projectItemSum.setText("总人数：45");
            itemViewHolder.projectItemBegin.setText("创建时间：2016-01-16");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
//                        listener.onItemClick(position, list.get(position));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
//        return null == list ? 0 : list.size();
        return 10;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Object object);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    static class ItemViewHolder extends ViewHolder {
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

        ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
