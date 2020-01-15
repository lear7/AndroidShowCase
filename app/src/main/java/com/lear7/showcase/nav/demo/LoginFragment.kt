package com.lear7.showcase.nav.demo

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.lear7.showcase.R
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

    private lateinit var mAccountEd: EditText
    private lateinit var mLoginBtn: Button

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nav_login, container, false)
        mAccountEd = view.findViewById(R.id.et_account)
        mLoginBtn = view.findViewById(R.id.btn_sign_in)
        mLoginBtn.setOnClickListener({ login() })

        return view
    }

    fun login() {
        GlobalScope.launch {
            val account = mAccountEd.text.toString()
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

        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(mAccountEd.text.toString())
        findNavController().navigate(action)
    }
}