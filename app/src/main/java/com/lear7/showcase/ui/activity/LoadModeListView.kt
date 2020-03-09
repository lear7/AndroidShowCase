package com.lear7.showcase.ui.activity

import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.routing.Routers
import com.lear7.showcase.ui.base.BaseActivity
import com.lear7.showcase.ui.base.GenericAdapter
import kotlinx.android.synthetic.main.activity_load_more_listview.*
import java.util.*

@Route(path = Routers.Act_AutoLoadListView)
class LoadModeListView : BaseActivity() {

    lateinit var adapter: GenericAdapter<String>
    val datas = ArrayList<String?>()

    override fun getLayoutId(): Int {
        return R.layout.activity_load_more_listview
    }

    var isLoading = false

    override fun initView() {
        super.initView()
        populateData()
        initAdapter()
    }

    private fun populateData() {
        var i = 0
        while (i < 10) {
            datas.add("Item $i")
            i++
        }
    }

    private fun initAdapter() {
        adapter = GenericAdapter(R.layout.item_recyclerview, datas) { view, data, _ ->
            (view.findViewById<View>(R.id.item_recycler_title) as TextView).text = data
        }
        adapter.setEnableLoadingMore(auto_listview, object : GenericAdapter.LoadMoreListener {
            override fun onLoadMore() {
                loadMore()
            }
        })
        //        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        auto_listview.adapter = adapter
    }

    private fun initScrollListener() {
        auto_listview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == datas.size - 1) { //bottom of list!
                        loadMore()
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() { // begin loading
//        datas.add(null)
//        // insert the last second one but not the last one
//        adapter.notifyItemInserted(datas.size - 1)

        val handler = Handler()
        handler.postDelayed({
            adapter.setLoading()
            val scrollPosition = datas.size

            // get current name
            var currentSize = scrollPosition
            val newSize = 10
            for (i in 0 until newSize) {
                datas.add("Item " + currentSize++)
            }
            // notify dataSet change
            adapter.notifyDataSetChanged()
            adapter.setLoaded()
        }, 2000)
    }

}