package com.lear7.showcase.component.paging

import androidx.paging.DataSource

class CustomPageDataSourceFactory(val repository: DataRepository) : DataSource.Factory<Int, DataBean>() {
    override fun create(): DataSource<Int, DataBean> {
        return CustomPageDataSource(repository)
    }
}