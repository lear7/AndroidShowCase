package com.lear7.showcase.component.room

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
import kotlinx.android.synthetic.main.activity_room_demo.*


@Route(path = Routers.Act_RoomDemo)
class RoomActivity : BaseActivity() {

    private lateinit var viewModel: RoomViewModel
    private lateinit var adapter: GenericAdapter<User>

    override fun getLayoutId(): Int {
        return R.layout.activity_room_demo
    }

    override fun initView() {
        super.initView()

        viewModel = ViewModelProvider(this).get(RoomViewModel::class.java)
        btn_add_user.setOnClickListener {
            addUser()
        }

        adapter = GenericAdapter(R.layout.item_recyclerview) { view: View, data: User?, position: Int? ->
            (view.findViewById<View>(R.id.item_recycler_title) as TextView).text = data?.firstName + " " + data?.lastName
        }
        user_list_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        user_list_view.adapter = adapter

        viewModel.getUserList().observe(this, androidx.lifecycle.Observer {
            adapter.updateList(it)
        })
    }

    fun addUser() {
        var firstName = input_first_name.text.toString()
        var lastName = input_last_name.text.toString()
        if (firstName.isEmpty() || lastName.isEmpty()) {
            showError(R.string.error_empty, room_root_view)
        } else {
            viewModel.addUser(User(firstName = firstName, lastName = lastName))
            input_first_name.setText("")
            input_last_name.setText("")
        }

    }

    fun showError(@StringRes errorMessage: Int, rootView: View) =
            snackbar(errorMessage, rootView)

    fun snackbar(@StringRes message: Int, rootView: View) = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()

}
