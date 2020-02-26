package com.lear7.showcase.component.objectbox

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.snackbar.Snackbar
import com.lear7.showcase.R
import com.lear7.showcase.routing.Routers
import com.lear7.showcase.ui.base.BaseActivity
import com.lear7.showcase.ui.base.GenericAdapter
import kotlinx.android.synthetic.main.activity_object_box.*
import kotlinx.android.synthetic.main.activity_room_demo.btn_add_user
import kotlinx.android.synthetic.main.activity_room_demo.room_root_view
import kotlinx.android.synthetic.main.activity_room_demo.user_list_view

@Route(path = Routers.Act_ObjectBoxDemo)
class ObjectBoxActivity : BaseActivity() {

    private lateinit var viewModel: ObjectBoxViewModel
    private lateinit var adapter: GenericAdapter<User>

    override fun getLayoutId() = R.layout.activity_object_box

    override fun initView() {
        super.initView()

        viewModel = ViewModelProvider(this).get(ObjectBoxViewModel::class.java)
        btn_add_user.setOnClickListener {
            addUser()
        }

        adapter = GenericAdapter(R.layout.item_recyclerview) { view: View, data: User?, position: Int? ->
            (view.findViewById<View>(R.id.item_recycler_title) as TextView).text = data?.name
        }
        user_list_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        user_list_view.adapter = adapter

        viewModel.getUserList().observe(this, androidx.lifecycle.Observer {
            adapter.updateList(it)
        })
    }

    fun addUser() {
        var firstName = input_name.text.toString()
        if (firstName.isEmpty()) {
            showError(R.string.error_empty, room_root_view)
        } else {
            viewModel.addUser(User(name = firstName))
            input_name.setText("")
        }

    }

    fun showError(@StringRes errorMessage: Int, rootView: View) =
        snackbar(errorMessage, rootView)

    fun snackbar(@StringRes message: Int, rootView: View) = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()

}
