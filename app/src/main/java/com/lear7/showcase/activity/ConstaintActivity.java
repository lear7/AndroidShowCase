package com.lear7.showcase.activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lear7.showcase.R;

import butterknife.BindView;

public class ConstaintActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;

    static final String imageUrl = "https://cdn.pixabay.com/photo/2019/09/22/12/37/grapes-4495944_1280.jpg";

    @Override
    public int getLayoutId() {
        return R.layout.activity_constraint;
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(this).load(imageUrl).into(imageView);
    }
}
