package com.lear7.showcase.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lear7.showcase.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Lear
 * @description
 * @date 2019/12/4 15:30
 */
public class FragmentTest1 extends BaseFragment {

    public interface BtnCallBack {
        public void onBtnClick(String message);
    }

    @BindView(R.id.fragment_test_tv)
    TextView textView;

    @BindView(R.id.fragment_test_btn1)
    Button btnSendBack;

    String dataFromActivity = "";

    @OnClick({R.id.fragment_test_btn1})
    public void onWidgetClick(View view) {
        if (view == btnSendBack) {
            if (getActivity() instanceof BtnCallBack) {
                ((BtnCallBack) getActivity()).onBtnClick("Hello from Fragment.");
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataFromActivity = getArguments().getString("message");
    }

    @Override
    protected void initView() {
        super.initView();
        textView.setText(dataFromActivity);
    }
}
