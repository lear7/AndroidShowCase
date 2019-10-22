package com.lear7.showcase.fragment;

import android.view.View;
import android.widget.Button;

import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;

import butterknife.BindView;
import butterknife.OnClick;

public class UiFragment extends BaseFragment {

    @BindView(R.id.btn_constraint)
    Button btnGoToB;

    @BindView(R.id.btn_material)
    Button btnMaterialSimple;

    @BindView(R.id.btn_material_alipay)
    Button btnMaterial;

    @BindView(R.id.btn_listview)
    Button btnListView;

    @OnClick({R.id.btn_constraint,
            R.id.btn_material,
            R.id.btn_material_alipay,
            R.id.btn_listview,
    })
    public void onClick(View view) {
        if (view == btnGoToB) {
            goTo(Routers.Act_Constaint);
        } else if (view == btnMaterial) {
            goTo(Routers.Act_MaterialAlipay);
        } else if (view == btnListView) {
            goTo(Routers.Act_ListView);
        } else if (view == btnMaterialSimple) {
            goTo(Routers.Act_Material);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui;
    }
}
