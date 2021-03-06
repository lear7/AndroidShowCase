package com.lear7.showcase.component.navigation.demo

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lear7.showcase.R
import kotlinx.android.synthetic.main.fragment_nav_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.toast

/**
@author Lear
@description
@date 2019/12/30 14:39
 */
class LoginFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nav_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_sign_in.setOnClickListener({ login() })

    }

    fun login() {
        GlobalScope.launch {
            val account = et_account.text.toString()
            if (TextUtils.isEmpty(account)) {
                runOnUiThread {
                    toast("请输入账号和密码")
                }
            } else {
                delay(1000)
                loginSucceed()
            }
        }
    }

    fun loginSucceed() {
        runOnUiThread {
            toast("登录成功")
        }

        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(et_account.text.toString())
        findNavController().navigate(action)
    }
}