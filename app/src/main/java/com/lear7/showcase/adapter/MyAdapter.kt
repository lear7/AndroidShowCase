package com.lear7.showcase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lear7.showcase.R.id
import com.lear7.showcase.R.layout
import java.util.*

class MyAdapter(private val mData: ArrayList<String>, private val mHeights: ArrayList<Int>) : Adapter<MyAdapter.MyViewHolder>() {

    interface ItemClick {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int): Boolean
    }

    lateinit var clickListener: ItemClick;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //将我们自定义的item布局R.layout.item_one转换为View

        val view: View = LayoutInflater.from(parent.context)
                .inflate(layout.item_recyclerview, parent, false)
        //将view传递给我们自定义的ViewHolder
        //返回这个MyHolder实体
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mText.text = mData[position]

        var lp = holder.mText.layoutParams
        lp.height = mHeights[position]
        holder.mText.layoutParams = lp

        if (clickListener != null) {
            holder.mText.setOnClickListener {
                clickListener.onItemClick(position)
            }
            holder.mText.setOnLongClickListener { clickListener.onItemLongClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : ViewHolder(itemView) {
        internal var mText: TextView = itemView.findViewById(id.item_recycler_title)
    }

}