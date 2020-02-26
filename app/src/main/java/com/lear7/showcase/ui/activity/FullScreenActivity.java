package com.lear7.showcase.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.util.LruCache;
import android.widget.ImageView;

import com.lear7.showcase.R;
import com.lear7.showcase.network.Urls;
import com.lear7.showcase.network.api.ApiService;
import com.lear7.showcase.network.helper.BaseSubscriber;
import com.lear7.showcase.network.helper.RxRetrofit;
import com.lear7.showcase.ui.base.BaseActivity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class FullScreenActivity extends BaseActivity {

    @BindView(R.id.imageBig)
    ImageView imageView3;

    private Bitmap bitmap;
    private LruCache<String, Bitmap> mMemoryCache;

    private BitmapRegionDecoder mDecoder;
    private BitmapFactory.Options mOptions;
    private int mImageWidth;
    private int mImageHeight;


    @Override
    protected void initView() {
        super.initView();

        createCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bitmap = null;
        mMemoryCache = null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_full_screen;
    }

    @Override
    protected void onStart() {
        super.onStart();
        downloadImageManually();
    }

    private void createCache() {
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
                return bitmap.getByteCount() / 1024;
            }
        };
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    private void downloadImageManually() {
//        if (mMemoryCache != null && mMemoryCache.get(Constants.BIG_IMAGE) != null) {
//            bitmap = mMemoryCache.get(Constants.BIG_IMAGE);
//            runOnUiThread(new Runnable() {//开启主线程更新UI
//                @Override
//                public void run() {
//                    Timber.d( "Show image, current thread is: " + Thread.currentThread().getName());
//                    imageView3.setImageBitmap(bitmap);
//                }
//            });
//        } else {
        Timber.d("Download....current thread is: " + Thread.currentThread().getName());
        RxRetrofit.getInstance()
                .create(ApiService.class)
                .downloadImg2(Urls.BIG_IMAGE)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ResponseBody>() {

                    @Override
                    public void onNext(ResponseBody value) {
                        Timber.d("Download finish, current thread is: " + Thread.currentThread().getName());
                        // data of image
                        try {
                            //注意：把byte[]转换为bitmap时，也是耗时操作，也必须在子线程
                            byte[] bytes = value.bytes();
                            InputStream inputStream = new ByteArrayInputStream(bytes);
                            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            runOnUiThread(new Runnable() {//开启主线程更新UI
                                @Override
                                public void run() {
                                    int width = imageView3.getWidth();
                                    int height = imageView3.getHeight();
                                    addBitmapToMemoryCache(Urls.BIG_IMAGE, bitmap);
                                    Timber.d("Show image, current thread is: " + Thread.currentThread().getName());
                                    Bitmap small = Bitmap.createScaledBitmap(bitmap, width, height, false);
                                    bitmap = null;
//                                    imageView3.setImageBitmap(small);
//                                    try {//调用saveFile方法
//                                        Timber.d( "Saving image...");
//                                        Utils.saveBitmap(FullScreenActivity.this, bitmap);
//                                        Timber.d( "Image saved!");
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    imageView3.setInputStream(inputStream);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
//        }
    }

}
