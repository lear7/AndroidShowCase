package com.lear7.showcase.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.common.Routers;
import com.lear7.showcase.learn.mvp.demo2.MvpPresenter;
import com.lear7.showcase.learn.mvp.demo2.MvpView;
import com.lear7.showcase.learn.mvp.demo2.base.BaseMvpActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Routers.Act_MvpDemo2)
public class MvpDemo2Activity extends BaseMvpActivity<MvpView, MvpPresenter> implements MvpView {

    @BindView(R.id.btn_succeed)
    Button btnSucceed;

    @BindView(R.id.btn_failed)
    Button btnFailed;

    @BindView(R.id.btn_complete)
    Button btnComplete;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp_demo2;
    }


    @Override
    protected MvpPresenter createPresenter() {
        return new MvpPresenter();
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
