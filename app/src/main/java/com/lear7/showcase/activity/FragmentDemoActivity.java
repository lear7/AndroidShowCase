package com.lear7.showcase.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.fragment.BaseFragment;
import com.lear7.showcase.fragment.FragmentTest1;
import com.lear7.showcase.widget.SecButton;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Routers.Act_Fragment)
public class FragmentDemoActivity extends BaseActivity implements FragmentTest1.BtnCallBack {

    @BindView(R.id.fragment_btn1)
    SecButton btn1;
    @BindView(R.id.fragment_btn2)
    Button btn2;
    @BindView(R.id.fragment_edittext)
    EditText editText;

    @OnClick({R.id.fragment_btn1, R.id.fragment_btn2})
    public void onWidgetClick(View view) {
        if (view == btn1) {
            showFragment1();
        } else if (view == btn2) {

        }
    }

    @Override
    protected void initView() {
        super.initView();
        btn1.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                // 频闭辅助功能的点击
                return true;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fragment_demo;
    }

    private void showFragment1() {
        Bundle bundle = new Bundle();
        bundle.putString("message", editText.getText().toString());

        BaseFragment fragment1 = new FragmentTest1();
        loadFragment(R.id.fragment_container1, fragment1, bundle);
    }

    private void loadFragment(@IdRes int containerId, Fragment fragment, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(containerId, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBtnClick(String message) {
        if (!TextUtils.isEmpty(message)) {
            showToast(message);
        }
    }
}
