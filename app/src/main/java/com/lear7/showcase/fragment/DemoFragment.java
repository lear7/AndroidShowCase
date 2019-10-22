package com.lear7.showcase.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lear7.showcase.R;
import com.lear7.showcase.adapter.CommonAdapter;

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
