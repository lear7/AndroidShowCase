package com.lear7.showcase.fragment;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProviders;

import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.lifecycle.observer.MyObserver;
import com.lear7.showcase.lifecycle.viewmodel.UserModel;

import butterknife.BindView;
import butterknife.OnClick;

public class JetpackFragment extends BaseFragment {

    @BindView(R.id.btn_test_mvp)
    Button btnTestMvp;

    @BindView(R.id.btn_test_mvp_wrap)
    Button btnTestMvpWrap;

    @BindView(R.id.btn_test_mvp_dagger)
    Button btnTestMvpDagger;

    @BindView(R.id.btn_data_binding)
    Button btnDatabinding;

    @BindView(R.id.btn_view_model)
    Button btnViewModel;

    @OnClick({
            R.id.btn_test_mvp,
            R.id.btn_test_mvp_wrap,
            R.id.btn_test_mvp_dagger,
            R.id.btn_data_binding,
            R.id.btn_view_model,
    })
    public void onClick(View view) {
        if (view == btnTestMvp) {
            goTo(Routers.Act_MvpDemo);
        } else if (view == btnTestMvpWrap) {
            goTo(Routers.Act_MvpWrap);
        } else if (view == btnTestMvpDagger) {
            goTo(Routers.Act_Dagger);
        } else if (view == btnDatabinding) {
            goTo(Routers.Act_DataBinding);
        } else if (view == btnViewModel) {
            goTo(Routers.Act_ViewModel);
        }
    }

    private void setViewModel() {
        UserModel model = ViewModelProviders.of(this).get(UserModel.class);
        model.setAge("29");
        model.setName("Lihua");
        getLifecycle().addObserver(new MyObserver());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_jetpack;
    }
}
