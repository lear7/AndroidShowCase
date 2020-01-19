package com.lear7.showcase.activity;

import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lear7.showcase.events.BaseEvent;
import com.lear7.showcase.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 屏幕常亮

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }

        ButterKnife.bind(this);
        initView();
    }

    protected void goTo(String url) {
        ARouter.getInstance().build(url).navigation();
    }

    public abstract @LayoutRes
    int getLayoutId();

    protected void initView() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(BaseEvent event) {

    }

    private Toast mToast;

    protected void showToast(final String text) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
            // Show Toast Message Above Soft Keyboard，不生效
            View root = findViewById(android.R.id.content);
            int yOffset = Math.max(0, root.getHeight() - mToast.getYOffset());
            mToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, yOffset);
        }
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            runOnUiThread(() -> {
                mToast.setText(text);
                mToast.show();
            });
        } else {
            mToast.setText(text);
            mToast.show();
        }
    }

    /**
     * Fragment的添加
     *
     * @param manager     Fragment管理器
     * @param aClass      相应的Fragment对象的getClass
     * @param containerId 容器的id
     * @param args        需要传值的话可将bundle填入  不需要传值就填null
     */
    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {

        String tag = aClass.getName();
        Timber.i("%s add fragment %s", tag, aClass.getSimpleName());

        Fragment fragment = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务

        if (fragment == null) {// 没有添加
            try {
                fragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例
                BaseFragment baseFragment = (BaseFragment) fragment; // 强转成我们base fragment

                // 设置 fragment 进入，退出， 弹进，弹出的动画
//                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());
                transaction.add(containerId, fragment, tag);

                if (baseFragment.isNeedToAddBackStack()) { // 判断是否需要加入回退栈
                    transaction.addToBackStack(tag); // 加入回退栈时制定一个tag，以便在找到指定的事务
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment, tag);
            }
        }

        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
        }
    }

    /**
     * 除当前 fragment 以外的所有 fragment 进行隐藏
     *
     * @param manager
     * @param transaction
     * @param currentFragment
     */
    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {
        List<Fragment> fragments = manager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }
}

