package com.lear7.showcase.component.paging

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class CustomDataSourceFactory(val repository: DataRepository) : DataSource.Factory<Int, DataBean>() {
    override fun create(): DataSource<Int, DataBean> {
        return CustomDataSource(repository)
    }
}

class CustomDataSource(val repository: DataRepository) : PageKeyedDataSource<Int, DataBean>(), AnkoLogger {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DataBean>) {
        info("loadInitial size : ${params.requestedLoadSize} ")
        val data = repository.loadData(params.requestedLoadSize)
        callback.onResult(data, null, 2)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataBean>) {
        info("loadAfter size : ${params.requestedLoadSize}  page:${params.key}")
        val data = repository.loadPageData(params.key, params.requestedLoadSize)
        data?.let {
            callback.onResult(data, params.key + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataBean>) {
        info("loadBefore size : ${params.requestedLoadSize}  page:${params.key}")
        val data = repository.loadPageData(params.key, params.requestedLoadSize)
        data?.let {
            callback.onResult(data, params.key - 1)
        }
    }
}