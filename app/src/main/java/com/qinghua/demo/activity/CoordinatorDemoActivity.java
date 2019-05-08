package com.qinghua.demo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qinghua.demo.R;
import com.qinghua.demo.adapter.ViewPagerAdapter;
import com.qinghua.demo.fragment.RecyclerDemoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/second/coordinator_demo")
public class CoordinatorDemoActivity  extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
//    @BindView(R.id.title)
//    TextView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //自已切换布局文件看效果
        setContentView(R.layout.activity_coordinator_demo);
        ButterKnife.bind(this);
        initViewPager();
//        initToolbar("Coordinator Demo");
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        RecyclerDemoFragment recyclerDemoFragment = RecyclerDemoFragment.newInstance();
        recyclerDemoFragment.setCount(40);
        RecyclerDemoFragment recyclerDemoFragment2 = RecyclerDemoFragment.newInstance();
        recyclerDemoFragment2.setCount(5);
        SparseArray list = new SparseArray();
        list.put(0, recyclerDemoFragment);
        list.put(1, recyclerDemoFragment2);
        SparseArray listTitle = new SparseArray();
        listTitle.put(0,"第一个tab");
        listTitle.put(1, "第二个tab");
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), list, listTitle);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(listTitle.size());
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * title位于中间 的toolbar
     *
     * @param title 标题
     */
    protected void initToolbar(String title) {
        mToolbar.setNavigationIcon(R.mipmap.black_left);
//        mTitleView.setText(title);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}