package com.lear7.showcase.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lear7.showcase.R;
import com.lear7.showcase.beans.User;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.databinding.ActivityBindingDemoBinding;

@Route(path = Routers.Act_DataBinding)
public class BindingDemoActivity extends AppCompatActivity {

    private ActivityBindingDemoBinding binding;
    private User user;

    public class Handler {

        public void onClick(View view) {
            user.setFirstName("Hua");
            user.setLastName("Li");
//            User user = new User("Li", "Hua");
//            binding.setUser(user);
        }

        public void onClick(View view, User user) {
            Toast.makeText(BindingDemoActivity.this, user.getFirstName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_demo);

        user = new User("Edvard", "Lee");
        binding.setUser(user);
        // 注意这一句一定要添加
        binding.setHandler(new Handler());

    }
}
