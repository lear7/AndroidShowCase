package com.lear7.showcase.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.fragment.LifecycleFragment;
import com.lear7.showcase.lifecycle.observer.ActivityObserver;

import butterknife.BindView;

import static com.lear7.showcase.constants.Routers.Act_Lifecycle;

@Route(path = Act_Lifecycle)
public class LifecycleActivity extends BaseActivity {

    @BindView(R.id.life_framelayout)
    FrameLayout frameLayout;

    @BindView(R.id.btn_switch_fragment)
    Button btnSwitch;

    private LifecycleRegistry registry;
    private LifecycleFragment fragment;
    private boolean isAttach = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lifecycle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registry.setCurrentState(Lifecycle.State.CREATED);

        getLifecycle().addObserver(new ActivityObserver());

        btnSwitch.setOnClickListener((view) -> {
            FragmentManager fm = getSupportFragmentManager();
            if (!isAttach) {
                fragment = new LifecycleFragment();
                loadFragment(fragment);
            } else {
                removeFragment(fragment);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.life_framelayout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        isAttach = true;
    }

    private void removeFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commitAllowingStateLoss();
        isAttach = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registry.setCurrentState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        registry.setCurrentState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        // return super.getLifecycle();
        // 返回自定义的LifecycleOwner
        if (registry == null) {
            // 自定义一个LifecyclerOwner
            registry = new LifecycleRegistry(this);
        }
        return registry;
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
