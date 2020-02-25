package com.lear7.showcase.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.ui.base.CommonAdapter
import com.lear7.showcase.common.Routers
import com.lear7.showcase.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.item_recyclerview.view.*

@Route(path = Routers.Act_Material)
class MaterialActivity : BaseActivity() {

    var data = ArrayList<String>()
    var heights = ArrayList<Int>()
    lateinit var adapter: CommonAdapter<String>

    override fun getLayoutId(): Int {
        return R.layout.activity_material;
    }

    override fun initView() {
        super.initView()
        setToolbar()
        setRecyclerView();

    }

    fun setToolbar() {
        setSupportActionBar(app_toolbar)
    }

    fun setRecyclerView() {
        for (i in 1..50) {
            data.add("Title $i")
            heights.add((200 + Math.random() * 500).toInt())
        }

        var layoutManager = LinearLayoutManager(RecycleViewActivity@ this, RecyclerView.VERTICAL, false)
        var gridViewManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycleView1.layoutManager = gridViewManager


        adapter = CommonAdapter(R.layout.item_recyclerview, data) { view, item, position ->
            view.item_recycler_title.text = item

            // set height
            var lp = view.item_recycler_title.layoutParams
            lp.height = heights[position]
            view.item_recycler_title.layoutParams = lp
        }
        recycleView1.adapter = adapter
    }

}
