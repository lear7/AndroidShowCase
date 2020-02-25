package com.lear7.showcase.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lear7.showcase.R.layout
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_recyclerview.*
import java.util.*

class TitleAdapter(private val mData: ArrayList<String>, private val mHeights: ArrayList<Int>) : Adapter<ViewHolder>() {

    interface ItemClick {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int): Boolean
    }

    lateinit var clickListener: ItemClick;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //将我们自定义的item布局R.layout.item_one转换为View

        val view: View = LayoutInflater.from(parent.context)
                .inflate(layout.item_recyclerview, parent, false)
        //将view传递给我们自定义的ViewHolder
        //返回这个MyHolder实体
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var vh = holder as MyViewHolder
        vh.item_recycler_title.text = mData[position]

        var lp = vh.item_recycler_title.layoutParams
        lp.height = mHeights[position]
        vh.item_recycler_title.layoutParams = lp

        if (clickListener != null) {
            vh.item_recycler_title.setOnClickListener {
                clickListener.onItemClick(position)
            }
            vh.item_recycler_title.setOnLongClickListener { clickListener.onItemLongClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(override val containerView: View) : ViewHolder(containerView), LayoutContainer {}

}