package com.lear7.showcase.activity

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.lear7.showcase.R
import com.lear7.showcase.adapter.CommonAdapter
import com.lear7.showcase.constants.Routers.Act_MaterialAlipay
import kotlinx.android.synthetic.main.activity_material_alipay.*
import kotlinx.android.synthetic.main.item_recyclerview.view.*
import kotlinx.android.synthetic.main.layout_life_pay.*
import kotlinx.android.synthetic.main.toolbar_expand.*
import org.jetbrains.anko.px2dip
import org.jetbrains.anko.toast
import timber.log.Timber

@Route(path = Act_MaterialAlipay)
class MaterialAlipayActivity : BaseActivity(), CommonAdapter.ItemClick, AppBarLayout.OnOffsetChangedListener {


    var data = ArrayList<String>()
    var heights = ArrayList<Int>()
    lateinit var myAdapter: CommonAdapter<String>
    private var mMaskColor: Int = 0

    override fun onItemClick(position: Int) {
        toast("Click item $position")
    }

    override fun onItemLongClick(position: Int): Boolean {
        data.removeAt(position)
        myAdapter.notifyItemRemoved(position)
        toast("Item $position removed")
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_material_alipay
    }


    override fun initView() {
        super.initView()

        setToolbar()
        setRecyclerView();

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val offset = Math.abs(verticalOffset)
        val total = appBarLayout.totalScrollRange
        Timber.d("total range: $total, offset: $offset")
        val alphaIn = (px2dip(offset) * 2).toInt()
        val alphaOut = if (200 - alphaIn < 0) 0 else 200 - alphaIn
        //计算淡入时候的遮罩透明度
        val maskColorIn = Color.argb(alphaIn, Color.red(mMaskColor),
                Color.green(mMaskColor), Color.blue(mMaskColor))
        //工具栏下方的频道布局要加速淡入或者淡出
        val maskColorInDouble = Color.argb(alphaIn * 2, Color.red(mMaskColor),
                Color.green(mMaskColor), Color.blue(mMaskColor))
        //计算淡出时候的遮罩透明度
        val maskColorOut = Color.argb(alphaOut * 3, Color.red(mMaskColor),
                Color.green(mMaskColor), Color.blue(mMaskColor))

        if (offset <= total * 0.45) { //偏移量小于一半，则显示展开时候的工具栏
            expand_toolbar_layout.visibility = View.VISIBLE
            collapse_toolbar_layout.visibility = View.GONE
            v_expand_mask.setBackgroundColor(maskColorInDouble)
        } else { //偏移量大于一半，则显示缩小时候的工具栏
            toast("hello")
            expand_toolbar_layout.visibility = View.GONE
            collapse_toolbar_layout.visibility = View.VISIBLE
//            v_collapse_mask.setBackgroundColor(maskColorOut)
        }
        v_pay_mask.setBackgroundColor(maskColorIn)

    }


    fun setToolbar() {
        mMaskColor = resources.getColor(R.color.colorPrimary)
        setSupportActionBar(app_toolbar)
        app_bar_layout.addOnOffsetChangedListener(this)
    }

    fun setRecyclerView() {
        for (i in 1..50) {
            data.add("Title $i")
            heights.add((200 + Math.random() * 500).toInt())
        }

        var layoutManager = LinearLayoutManager(RecycleViewActivity@ this, RecyclerView.VERTICAL, false)
        var gridViewManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        recycleView1.layoutManager = gridViewManager


        myAdapter = CommonAdapter(R.layout.item_recyclerview, data) { view, item, position ->
            view.item_recycler_title.text = item

            // set height
            var lp = view.item_recycler_title.layoutParams
            lp.height = heights[position]
            view.item_recycler_title.layoutParams = lp

            // set listener
            view.setOnClickListener {
                this.onItemClick(position)
            }
            view.setOnLongClickListener {
                this.onItemLongClick(position)
            }
        }
        recycleView1.adapter = myAdapter
    }

}
