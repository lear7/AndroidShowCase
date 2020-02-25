package com.lear7.showcase.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class GenericAdapter<T>(@LayoutRes private val layoutId: Int, private val binding: (View, T, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas = mutableListOf<T>()

    interface ItemClick {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int): Boolean
    }

    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(layoutId, parent, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ItemHolder
        binding(vh.containerView, datas[position], position)
    }

    internal class ItemHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    }

    fun updateList(list: List<T>) {
        if (list.isNotEmpty()) {
            datas.clear()
            datas.addAll(list)
            notifyDataSetChanged()
        }
    }

}