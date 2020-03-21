package com.lear7.showcase.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.component.paging.CustomAdapter
import com.lear7.showcase.component.paging.CustomPageDataSourceFactory
import com.lear7.showcase.component.paging.DataRepository
import com.lear7.showcase.routing.Routers
import kotlinx.android.synthetic.main.activity_paging.*

@Route(path = Routers.Act_Paging)
class PagingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)

        val adapter = CustomAdapter()
        user_list_view.adapter = adapter

        val data = LivePagedListBuilder(CustomPageDataSourceFactory(DataRepository()), PagedList.Config.Builder()
                .setPageSize(20)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .build()).build()

        data.observe(this, Observer {
            adapter.submitList(it)
        })

    }
}
