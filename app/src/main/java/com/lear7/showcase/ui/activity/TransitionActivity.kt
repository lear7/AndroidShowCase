package com.lear7.showcase.ui.activity

import android.transition.*
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.routing.Routers
import com.lear7.showcase.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_transition.*

@Route(path = Routers.Act_Transition)
class TransitionActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_transition

    override fun initView() {
        super.initView()

        val sceneRoot: ViewGroup = findViewById(R.id.scene_root)
        val a1Scene: Scene = Scene.getSceneForLayout(sceneRoot, R.layout.a1_scene, this)
        val a2Scene: Scene = Scene.getSceneForLayout(sceneRoot, R.layout.a2_scene, this)

        var fadeTransition: Transition =
            TransitionInflater.from(this)
                .inflateTransition(R.transition.fade_transition)
        var autoTransition: Transition = AutoTransition()

        trans_button.setOnClickListener {
            TransitionManager.go(a2Scene, autoTransition)
        }
    }

}