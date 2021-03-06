package cn.renrg.frame.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * cn.wzh.widget.UnScrollGridView
 * 
 * @author rulang1
 * 
 */
public class UnScrollGridView extends GridView {

	public UnScrollGridView(Context context) {
		super(context);
	}

	public UnScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public UnScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
