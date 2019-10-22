package com.lear7.showcase.activity;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.dagger.DaggerDemoComponent;
import com.lear7.showcase.dagger.DemoUser;

import javax.inject.Inject;

import butterknife.BindView;

@Route(path = Routers.Act_DaggerDemo)
public class DemoDaggerActivity extends BaseActivity {

    @Inject
    DemoUser mUser;

    @BindView(R.id.text_hint)
    TextView textHint;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dagger_demo;
    }

    @Override
    protected void initView() {
        super.initView();

        // 注入
        DaggerDemoComponent.builder().build().inject(this);
        textHint.setText("Info retrieved through injection: " + mUser.getFirstName() + " " + mUser.getLastName());
    }
}
