package com.lear7.showcase.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.adapter.CommonAdapter
import com.lear7.showcase.adapter.MyAdapter
import com.lear7.showcase.constants.Routers.Act_RecyclerView
import kotlinx.android.synthetic.main.activity_recycle_view.*
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import org.jetbrains.anko.toast

@Route(path = Act_RecyclerView)
class RecycleViewActivity : BaseActivity(), MyAdapter.ItemClick {
    var data = ArrayList<String>()
    var heights = ArrayList<Int>()
    lateinit var myAdapter: MyAdapter

    override fun onItemClick(position: Int) {
        toast("Click item $position")
    }

    override fun onItemLongClick(position: Int): Boolean {
        data.removeAt(position)
        myAdapter.notifyItemRemoved(position)
        toast("Item $position removed")
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_recycle_view
    }

    override fun initView() {
        super.initView()
        textView_title.setOnClickListener {
            textView_title.text = "New Title"
            toast("Title is updated")
        }


        for (i in 1..40) {
            data.add("Title $i")
            heights.add((200 + Math.random() * 500).toInt())
        }

        var layoutManager = LinearLayoutManager(RecycleViewActivity@ this, RecyclerView.VERTICAL, false)
        var gridViewManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycleView1.layoutManager = gridViewManager

//        myAdapter = MyAdapter(data, heights)
//        myAdapter.clickListener = this
//        recycleView1.adapter = myAdapter

        var adapter = CommonAdapter(R.layout.item_recyclerview, data,
                { view, item ->
                    view.item_recycler_title.text = item
                })
        recycleView1.adapter = adapter

    }

}
