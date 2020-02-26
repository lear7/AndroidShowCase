package com.lear7.showcase.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.routing.Routers;
import com.lear7.showcase.component.mvp.demo.MvpDemoPresenter;
import com.lear7.showcase.component.mvp.demo.MvpDemoView;
import com.lear7.showcase.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Routers.Act_MvpDemo)
public class MvpDemoActivity extends BaseActivity implements MvpDemoView {

    @BindView(R.id.btn_succeed)
    Button btnSucceed;

    @BindView(R.id.btn_failed)
    Button btnFailed;

    @BindView(R.id.btn_complete)
    Button btnComplete;

    private MvpDemoPresenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp_demo;
    }

    @Override
    protected void initView() {
        super.initView();
        if (mPresenter == null) {
            mPresenter = new MvpDemoPresenter();
        }
        if (mPresenter != null) {
            mPresenter.setView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @OnClick({R.id.btn_succeed, R.id.btn_failed, R.id.btn_complete})
    public void onClick(View view) {
        if (view == btnSucceed) {
            mPresenter.getData("normal");
        } else if (view == btnFailed) {
            mPresenter.getData("error");
        } else if (view == btnComplete) {
            mPresenter.getData("complete");
        }
    }


    @Override
    public void showData(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showComplete() {
        Toast.makeText(this, "Request Complete", Toast.LENGTH_SHORT).show();
    }
}
