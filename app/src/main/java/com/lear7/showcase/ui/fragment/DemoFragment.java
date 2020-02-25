package com.lear7.showcase.ui.fragment;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lear7.showcase.R;
import com.lear7.showcase.ui.base.CommonAdapter;
import com.lear7.showcase.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import kotlin.Unit;

public class DemoFragment extends BaseFragment {

    @BindView(R.id.list_fragment_recycler)
    RecyclerView recyclerView;

    private List<String> datas;

    @Override
    public int getLayoutId() {
        return R.layout.list_fragment;
    }

    @Override
    protected void initView() {
        datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("Item " + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CommonAdapter<String>(R.layout.item_recyclerview, datas, (view, data, position) -> {
            ((TextView) view.findViewById(R.id.item_recycler_title)).setText(data);
            return Unit.INSTANCE;
        }));
    }

}
