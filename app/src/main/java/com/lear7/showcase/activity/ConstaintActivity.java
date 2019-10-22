package com.lear7.showcase.activity;

import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.constants.Urls;

import butterknife.BindView;


@Route(path = Routers.Act_Constaint)
public class ConstaintActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_constraint;
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(this).load(Urls.SMALL_IMAGE).into(imageView);
    }
}
