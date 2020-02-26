package com.lear7.showcase.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.lear7.showcase.R;
import com.lear7.showcase.routing.Routers;
import com.lear7.showcase.network.Urls;
import com.lear7.showcase.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Routers.Act_DownloadTest)
public class DownloadTestActivity extends BaseActivity {

    @BindView(R.id.btn_down)
    Button btnDown;

    @BindView(R.id.btn_clean)
    Button btnClean;

    @BindView(R.id.btn_big)
    Button btnBig;

    @BindView(R.id.image_big)
    ImageView imageView;

    @BindView(R.id.image_big_2)
    SimpleDraweeView imageView2;


    @OnClick({R.id.btn_down, R.id.btn_clean, R.id.btn_big})
    public void onClick(View view) {
        if (view == btnDown) {
            download();
        } else if (view == btnClean) {
            cleanCache();
        } else if (view == btnBig) {
            Intent i = new Intent(this, FullScreenActivity.class);
            startActivity(i);
        }
    }


    private void cleanCache() {
        Glide.get(DownloadTestActivity.this).clearMemory();
        new HandlerThread("CleanCache") {
            @Override
            public void run() {
                Glide.get(DownloadTestActivity.this).clearDiskCache();
            }
        }.start();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();

    }

    private void download() {
        Glide.with(this).load(Urls.BIG_IMAGE).into(imageView);
        Uri uri = Uri.parse(Urls.BIG_IMAGE);
        imageView2.setImageURI(uri);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_download_test;
    }

}
