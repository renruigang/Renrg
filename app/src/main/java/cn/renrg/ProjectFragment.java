package cn.renrg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hello on 2015/8/27.
 */
public class ProjectFragment extends Fragment {

    public static final String PAGE = "page";
    @BindView(R.id.project_list)
    ListView projectList;
    private View rootView;
    private int value;
    private ProjectAdapter projectAdapter;

    public static ProjectFragment newInstance(int value) {
        Bundle args = new Bundle();
        args.putInt(PAGE, value);
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        value = getArguments().getInt(PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_project, container, false);
            ButterKnife.bind(this, rootView);
            initView();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        projectAdapter = new ProjectAdapter(getActivity(), new ArrayList<String>());
        projectList.setAdapter(projectAdapter);
    }
}
