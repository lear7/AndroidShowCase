package com.lear7.showcase.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.common.Routers;
import com.lear7.showcase.learn.mvpdagger.base.BaseDaggerActivity;
import com.lear7.showcase.learn.mvpdagger.iview.DaggerView;
import com.lear7.showcase.learn.mvpdagger.presenter.DaggerPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Routers.Act_MvpDagger)
public class MvpDaggerActivity extends BaseDaggerActivity<DaggerView, DaggerPresenter> implements DaggerView {

    @BindView(R.id.btn_show_message)
    Button btnShowMsg;

    @Inject
    DaggerPresenter mPresenter;

    @OnClick({R.id.btn_show_message})
    public void onClick(View view) {
        if (view == btnShowMsg) {
            mPresenter.showToast();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp_dagger;
    }

    @Override
    protected DaggerPresenter createPresenter() {
        return mPresenter;
    }

    @Override
    protected void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
