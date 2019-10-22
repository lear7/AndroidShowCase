package com.lear7.showcase.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import androidx.lifecycle.ViewModelProviders;
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
import com.lear7.showcase.fragment.ListFragment;
import com.lear7.showcase.lifecycle.observer.MyObserver;
import com.lear7.showcase.lifecycle.viewmodel.UserModel;
import com.lear7.showcase.service.WeatherService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = Routers.Act_Main)
public class MainActivity extends BaseActivity {

    @BindView(R.id.app_toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_go_to_b)
    Button btnGoToB;

    @BindView(R.id.btn_go_to_thread)
    Button btnGoToThread;

    @BindView(R.id.btn_start_service)
    Button startService;

    @BindView(R.id.btn_thread_test)
    Button startThreadTest;

    @BindView(R.id.btn_start_download)
    Button startDownloadTest;

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

    @BindView(R.id.btn_video_demo)
    Button btnVideoDemo;

    @BindView(R.id.btn_listview)
    Button btnListView;

    @BindView(R.id.btn_material)
    Button btnMaterialSimple;

    @BindView(R.id.btn_material_alipay)
    Button btnMaterial;

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

    private int share = 0;

    // 声明ThreadLocal
    private static ThreadLocal<Integer> result = new ThreadLocal<>();


    public static final String A = "abcdeabcdef";
    public static final String B = "aaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddaaddddaaddddaaddddaadddddeffffaaabbddccdeeebbddadddaaddddabcdeabcdefaaddddaaddddaadddd";

    static class Thread1 implements Runnable {

        @Override
        public void run() {
            int lenA = A.length();
            int lenB = B.length();
            int i = 0;

            while (result.get() == null && i < lenB) {
                boolean isMatch = true;
                for (int j = 0; j < lenA; j++) {
                    if (A.charAt(j) != B.charAt(i + j)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    result.set(i);
                    break;
                }
                i++;
            }
        }
    }

    static class Thread2 implements Runnable {

        @Override
        public void run() {
            int lenA = A.length();
            int lenB = B.length();
            int i = lenB - lenA;

            while (result.get() == null && i >= lenA) {
                boolean isMatch = true;
                for (int j = lenA - 1; j >= 0; j--) {
                    if (A.charAt(j) != B.charAt(i - j)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    result.set(i);
                    break;
                }
                i--;
            }
        }
    }

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

        titles.add("Testing");
        titles.add("Thread");
        titles.add("Image");
        titles.add("MVP");
        titles.add("Jetpack");
        titles.add("Material");

        for (String title : titles) {
            tablayout.addTab(tablayout.newTab().setText(title));
            fragments.add(new ListFragment());
        }


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

    private void setViewModel() {
        UserModel model = ViewModelProviders.of(this).get(UserModel.class);
        model.setAge("29");
        model.setName("Lihua");
        getLifecycle().addObserver(new MyObserver());
    }

    @Override
    protected void initView() {
        super.initView();

        setToolbar();
        setTabLayout();
        setViewModel();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    private void bindWeatherService() {
        Intent serviceIntent = new Intent(this, WeatherService.class);
        startService(serviceIntent);
    }

    @OnClick({R.id.btn_go_to_b,
            R.id.btn_go_to_thread,
            R.id.btn_start_service,
            R.id.btn_thread_test,
            R.id.btn_start_download,
            R.id.btn_test_mvp,
            R.id.btn_test_mvp_wrap,
            R.id.btn_test_mvp_dagger,
            R.id.btn_data_binding,
            R.id.btn_view_model,
            R.id.btn_material_alipay,
            R.id.btn_video_demo,
            R.id.btn_listview,
            R.id.btn_material
    })
    public void onClick(View view) {
        if (view == btnGoToB) {
            startActivity(new Intent(this, ConstaintActivity.class));
        } else if (view == btnGoToThread) {
            Intent intent = new Intent(this, ThreadLearnActivity.class);
            startActivity(intent);
        } else if (view == startService) {
            bindWeatherService();
        } else if (view == startThreadTest) {
            Intent intent = new Intent(this, ThreadTestActivity.class);
            startActivity(intent);
        } else if (view == startDownloadTest) {
            Intent intent = new Intent(this, DownloadTestActivity.class);
            startActivity(intent);
        } else if (view == btnTestMvp) {
            goTo(Routers.Act_MvpDemo);
        } else if (view == btnTestMvpWrap) {
            goTo(Routers.Act_MvpWrap);
        } else if (view == btnTestMvpDagger) {
            goTo(Routers.Act_Dagger);
        } else if (view == btnDatabinding) {
            goTo(Routers.Act_DataBinding);
        } else if (view == btnViewModel) {
            goTo(Routers.Act_ViewModel);
        } else if (view == btnMaterial) {
            goTo(Routers.Act_MaterialAlipay);
        } else if (view == btnVideoDemo) {
            goTo(Routers.Act_Video);
        } else if (view == btnListView) {
            goTo(Routers.Act_ListView);
        } else if (view == btnMaterialSimple) {
            goTo(Routers.Act_Material);
        }
    }


    public class MyAsyncTask extends AsyncTask {

        private String taskName;

        public MyAsyncTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        protected String doInBackground(Object[] objects) {
            String time = new Date().toString();
            System.out.println(taskName + " Start");
            try {
                share++;
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return time;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            System.out.println(taskName + " End");
        }
    }

    private void testPool() {
        // 这种方式不建议
        // ExecutorService pool = Executors.newFixedThreadPool(5);
        ExecutorService pool = new ThreadPoolExecutor(4, 20,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        List<MyAsyncTask> tasks = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            MyAsyncTask task = new MyAsyncTask("task" + i);
            tasks.add(task);
        }

        for (MyAsyncTask task : tasks) {
            task.executeOnExecutor(pool);
        }

        pool.shutdown();
    }

}
