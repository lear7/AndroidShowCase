package com.lear7.showcase.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.model.User;
import com.lear7.showcase.common.Routers;
import com.lear7.showcase.databinding.ActivityBindingDemoBinding;

@Route(path = Routers.Act_DataBinding)
public class BindingDemoActivity extends AppCompatActivity {

    private ActivityBindingDemoBinding binding;
    private User user;

    public class Handler {

        public void onClick(View view) {
            user.getFirstName().set("Edvard");
            user.getLastName().set("Lee");
        }

        public void onClick(View view, User user) {
            Toast.makeText(BindingDemoActivity.this, user.getFirstName().get(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_demo);

        user = new User(new ObservableField<String>("Hua"), new ObservableField<String>("Li"));
        binding.setUser(user);
        // 注意这一句一定要添加
        binding.setHandler(new Handler());
    }
}
