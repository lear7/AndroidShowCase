package com.lear7.showcase.ui.activity;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.lear7.showcase.common.Routers.Act_ListView;

@Route(path = Act_ListView)
public class ListScrollView extends BaseActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list_scroll_view;
    }

    @Override
    protected void initView() {
        super.initView();

        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("Item " + i);
        }

        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 50; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", datas.get(i));
            listitem.add(map);
        }

        SimpleAdapter myAdapter = new SimpleAdapter(getApplicationContext(), listitem, R.layout.item_listview, new String[]{"title"}, new int[]{R.id.item_title});
        listView.setAdapter(myAdapter);
    }
}
