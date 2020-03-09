package com.lear7.showcase.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lear7.showcase.R
import kotlinx.android.extensions.LayoutContainer


class GenericAdapter<T>(@LayoutRes private val layoutId: Int, private var datas: MutableList<T?> = mutableListOf(), private val binding: (View, T, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ItemClick {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int): Boolean
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }

//    lateinit var datas: MutableList<T?>

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    private var isLoading = false
    private val visibleThreshold = 5

    private var loadMoreListener: LoadMoreListener? = null
    private var recyclerView: RecyclerView? = null
        set(value) {
            field = value
            field?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading && field!!.layoutManager is LinearLayoutManager) {
                        val linearLayoutManager = field!!.layoutManager as LinearLayoutManager
                        val totalItemCount = linearLayoutManager.itemCount;
                        val lastVisibleItem = linearLayoutManager
                                .findLastVisibleItemPosition();
                        if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            loadMoreListener?.let {
                                datas.add(null)
                                // insert the last second one but not the last one
                                this@GenericAdapter.notifyItemInserted(datas.size - 1)
                                it.onLoadMore()
                            }
                            isLoading = true
                        }
                    }
                }
            })
        }

    fun setEnableLoadingMore(recyclerView: RecyclerView, listener: LoadMoreListener) {
        this.recyclerView = recyclerView
        this.loadMoreListener = listener
    }

    fun setLoading() {
        // remove the loading item
        if (datas == null || datas.size == 0)
            return
        datas.removeAt(datas.size - 1)
        // get the data size
        val scrollPosition = datas.size
        // notify remove action
        this.notifyItemRemoved(scrollPosition)
    }

    fun setLoaded() {
        isLoading = false
    }

    override fun getItemViewType(position: Int): Int {
        return if (datas[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            ItemViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            LoadingViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ItemViewHolder) {
            datas[position]?.let { binding(viewHolder.containerView, it, position) }
        } else {
            // TODO ProgressBar would be displayed
        }
    }

    internal class ItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    }

    internal class LoadingViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    }

    fun updateList(list: List<T?>) {
        if (list.isNotEmpty()) {
            datas.clear()
            datas.addAll(list)
            notifyDataSetChanged()
        }
    }

}