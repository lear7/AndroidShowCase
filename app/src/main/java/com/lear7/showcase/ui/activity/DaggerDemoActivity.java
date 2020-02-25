package com.lear7.showcase.ui.activity;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.common.Routers;
import com.lear7.showcase.learn.dagger.DaggerTextViewComponent;
import com.lear7.showcase.learn.dagger.DemoUser;
import com.lear7.showcase.learn.dagger.TextViewModule;
import com.lear7.showcase.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = Routers.Act_DaggerDemo)
public class DaggerDemoActivity extends BaseActivity {

    @Inject
    DemoUser mUser;

    @Inject
    TextView mTextView;

    @BindView(R.id.text_hint)
    TextView textHint;

    @BindView(R.id.dagger_frame)
    FrameLayout frameLayout;

    @Inject
    TextView injectedTextView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dagger_demo;
    }

    @Override
    protected void initView() {
        super.initView();

        // 注入
        DaggerTextViewComponent.builder()
                .textViewModule(new TextViewModule(this))
                .build()
                .inject(this);

        // User可以可以直接使用了
        textHint.setText("Info retrieved through injection: " + mUser.getFirstName() + " " + mUser.getLastName());
        // TextView可以直接使用了
        mTextView.setText("A TextView through injection: " + mUser.getFirstName() + " " + mUser.getLastName());
        frameLayout.addView(mTextView);
    }
}
