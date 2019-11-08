package com.lear7.showcase.activity

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.jaeger.library.StatusBarUtil
import com.lear7.showcase.App
import com.lear7.showcase.R
import com.lear7.showcase.beans.AnimalMain
import com.lear7.showcase.constants.Routers.Act_Video
import com.lear7.showcase.constants.Urls
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_video.*


@Route(path = Act_Video)
class VideoActivity : BaseActivity() {

    lateinit var orientationUtils: OrientationUtils

    override fun getLayoutId(): Int {
        return R.layout.activity_video
    }


    override fun initView() {
        super.initView()
        Log.e(App.TAG, "initialing")

        var animal = AnimalMain(this, "Duck", AnimalMain.FEMALE)
        AnimalMain.getSex(animal.name)

//        video_player.post {
//            var width = video_player.layoutParams.width
//            var height = width / 16 * 9
//            Log.e(App.TAG, "new height: ${height}")
//            Log.e(App.TAG, "new width: ${width}")
//            video_player.layoutParams = LinearLayout.LayoutParams(width, height)
//        }

        StatusBarUtil.setColor(VideoAcivity@ this, Color.BLACK);
        video_edittext.setText(Urls.VIDEO_URL2)
        btn_refresh.setOnClickListener {
            resetPlayer()
            video_player.setUp(video_edittext.text.toString(), true, "Good Video")
        }


        //增加封面
        val imageView = ImageView(this)
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        Glide.with(this).load(Urls.SMALL_IMAGE).into(imageView)
        video_player.setThumbImageView(imageView)
        //增加title
        video_player.getTitleTextView().setVisibility(View.VISIBLE)
        //设置返回键
        video_player.getBackButton().setVisibility(View.VISIBLE)
        //设置旋转
        orientationUtils = OrientationUtils(this, video_player)
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        video_player.getFullscreenButton().setOnClickListener {
            orientationUtils.resolveByClick()
            if (orientationUtils.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
        //是否可以滑动调整
        video_player.setIsTouchWiget(true)
        //设置返回按键功能
        video_player.getBackButton().setOnClickListener { onBackPressed() }
        video_player.startPlayLogic()
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !isInMultiWindowMode) {
            video_player.onVideoPause()
        }
    }

    override fun onResume() {
        super.onResume()
        video_player.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
        if (orientationUtils != null) orientationUtils.releaseListener()
    }

    override fun onBackPressed() {
        resetPlayer()
        super.onBackPressed()
    }

    fun resetPlayer(){
        //先返回正常状态
        if (orientationUtils.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            video_player.fullscreenButton.performClick()
            return
        }
        //释放所有
        video_player.setVideoAllCallBack(null)
    }

}
