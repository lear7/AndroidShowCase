package com.lear7.showcase.component.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lear7.showcase.R

class CustomAdapter : PagedListAdapter<DataBean, CustomViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
            CustomViewHolder(parent)

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataBean>() {
            override fun areItemsTheSame(oldConcert: DataBean,
                                         newConcert: DataBean): Boolean =
                    oldConcert.id == newConcert.id

            override fun areContentsTheSame(oldConcert: DataBean,
                                            newConcert: DataBean): Boolean =
                    oldConcert == newConcert
        }
    }

}


class CustomViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_listview, parent, false)) {
    private val nameView = itemView.findViewById<TextView>(R.id.item_title)
    var item: DataBean? = null

    fun bindTo(item: DataBean?) {
        this.item = item
        nameView.text = item?.name
    }
}