package com.lear7.showcase.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.adapter.MyAdapter
import com.lear7.showcase.constants.Routers.Act_RecyclerView
import kotlinx.android.synthetic.main.activity_recycle_view.*
import org.jetbrains.anko.toast

@Route(path = Act_RecyclerView)
class RecycleViewActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_recycle_view;
    }


    override fun initView() {
        super.initView()
        textView_title.setOnClickListener {
            textView_title.text = "New Title"
            toast("Title is updated")
        }


        var data = ArrayList<String>()
        var heights = ArrayList<Int>()
        for (i in 1..20) {
            data.add("Title $i")
            heights.add((100 + Math.random() * 300).toInt())
        }

        var myAdapter = MyAdapter(data, heights)
        var layoutManager = LinearLayoutManager(RecycleViewActivity@ this, RecyclerView.VERTICAL, false)
        var gridViewManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        recycleView1.layoutManager = gridViewManager
        recycleView1.adapter = myAdapter
    }

}
