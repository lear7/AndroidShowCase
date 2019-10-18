package com.lear7.showcase.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.mvp.base.BaseMvpActivity;
import com.lear7.showcase.mvp.wrap.WrapPresenter;
import com.lear7.showcase.mvp.wrap.WrapView;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Routers.Act_MvpWrap)
public class MvpWrapActivity extends BaseMvpActivity<WrapView, WrapPresenter> implements WrapView {

    @BindView(R.id.btn_succeed)
    Button btnSucceed;

    @BindView(R.id.btn_failed)
    Button btnFailed;

    @BindView(R.id.btn_complete)
    Button btnComplete;


    @Override
    public int getLayoutId() {
        return R.layout.activity_mvp_wrap;
    }


    @Override
    protected WrapPresenter createPresenter() {
        return new WrapPresenter();
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
