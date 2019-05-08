package com.qinghua.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qinghua.demo.R;
import com.qinghua.demo.widget.CircleMenuLayout;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/home/main")
public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.circle_menu)
    public void openCircleMenu() {
        ARouter.getInstance().build("/second/circle_menu").navigation();
    }
    @OnClick(R.id.glide_demo)
    public void openGlideDemo() {
        ARouter.getInstance().build("/second/glide_demo").navigation();
    }

    @OnClick(R.id.coordinator_demo)
    public void openCoordinatorDemo() {
        ARouter.getInstance().build("/second/coordinator_demo").navigation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
