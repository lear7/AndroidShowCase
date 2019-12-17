package com.lear7.showcase.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jaeger.library.StatusBarUtil;
import com.lear7.showcase.R;
import com.lear7.showcase.constants.Routers;
import com.lear7.showcase.constants.Urls;
import com.lear7.showcase.fragment.JavaFragment;
import com.lear7.showcase.fragment.JetpackFragment;
import com.lear7.showcase.fragment.MvpFragment;
import com.lear7.showcase.fragment.UiFragment;
import com.lear7.showcase.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = Routers.Act_Main)
public class MainActivity extends BaseActivity {

    @BindView(R.id.app_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_imageview)
    ImageView imageView;

    @BindView(R.id.main_tablayout)
    TabLayout tablayout;

    @BindView(R.id.main_viewpager)
    ViewPager viewPager;

    @BindView(R.id.main_nav)
    NavigationView navigationView;

    @BindView(R.id.main_drawer)
    DrawerLayout drawerLayout;

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).load(Urls.SMALL_IMAGE).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.white));

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_title);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setCollapsedTitleGravity(GravityCompat.START);

        toolbar.setTitle("Android Showcase");
        toolbar.setSubtitle("github.com/lear7");

        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        // actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.mipmap.ic_back));

        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener((item) -> {
            if (toggle.onOptionsItemSelected(item)) {
                return true;
            } else if (item.getItemId() == R.id.menu_contact_me) {
                Toast.makeText(this, "Contacting...", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == R.id.menu_about) {
                Toast.makeText(this, "This is a demo version", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void setTabLayout() {
        List<String> titles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();

        titles.add("Java");
        titles.add("Material Design");
        titles.add("MVP");
        titles.add("Jetpack");

        for (String title : titles) {
            tablayout.addTab(tablayout.newTab().setText(title));
        }

        fragments.add(new JavaFragment());
        fragments.add(new UiFragment());
        fragments.add(new MvpFragment());
        fragments.add(new JetpackFragment());

        FragmentStatePagerAdapter pageAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        };

        // binding
        viewPager.setAdapter(pageAdapter);
        tablayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbar();
        setTabLayout();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
