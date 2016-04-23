package demo.chen.com.androiddemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import demo.chen.com.androiddemo.base.BaseActivity;
import demo.chen.com.androiddemo.base.BaseItem;
import demo.chen.com.androiddemo.chapter1.aidl.AidlCallbackActivity;
import demo.chen.com.androiddemo.chapter1.messager.MessengerActivity;
import demo.chen.com.androiddemo.chapter3.ArcActivity;
import demo.chen.com.androiddemo.chapter3.ArcView;
import demo.chen.com.androiddemo.chapter3.CustomViewActivity;
import demo.chen.com.androiddemo.fragment.CheeseListFragment;

public class MainActivity extends BaseActivity {

    public static String[] DEMO_LIST = new String[]{
            "Chapter 1 : IPC"
    };
    private ListView listview;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        ArrayList<BaseItem> ipcList = new ArrayList<>();
        BaseItem aidl = new BaseItem();
        aidl.setName("AIDL");
        aidl.setItemClass(AidlCallbackActivity.class);
        ipcList.add(aidl);
        BaseItem messenger = new BaseItem();
        messenger.setName("messenger");
        messenger.setItemClass(MessengerActivity.class);
        ipcList.add(messenger);
        CheeseListFragment ipcFragment = CheeseListFragment.newInstance(ipcList);
        adapter.addFragment(ipcFragment, "Chapter 1：IPC");
        adapter.addFragment(new CheeseListFragment(), "Chapter 2：Anim");
        ArrayList<BaseItem> viewList = new ArrayList<>();
        BaseItem customView = new BaseItem();
        customView.setName("Custom View");
        customView.setItemClass(CustomViewActivity.class);
        viewList.add(customView);
        BaseItem arcView = new BaseItem();
        arcView.setName("Arc View");
        arcView.setItemClass(ArcActivity.class);
        viewList.add(arcView);

        CheeseListFragment viewFragment = CheeseListFragment.newInstance(viewList);
        adapter.addFragment(viewFragment, "Chapter 3：View");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
