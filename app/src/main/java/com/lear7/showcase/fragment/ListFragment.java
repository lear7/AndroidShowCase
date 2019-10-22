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

import kotlin.Unit;

public class ListFragment extends Fragment {

    private View view;
    private List<String> datas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("Item " + i);
        }

        view = inflater.inflate(R.layout.list_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.list_fragment_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CommonAdapter<String>(R.layout.item_recyclerview, datas, (view, data, position) -> {
            ((TextView) view.findViewById(R.id.item_recycler_title)).setText(data);
            return Unit.INSTANCE;
        }));
    }
}
