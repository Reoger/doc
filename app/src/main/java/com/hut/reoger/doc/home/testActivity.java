package com.hut.reoger.doc.home;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.*;
import android.widget.Toast;

//import com.hut.reoger.doc.utils.aop.AspectLogTest;
import com.hut.reoger.doc.R;
import com.hut.reoger.doc.doc.adapter.MyDocAdapter;
import com.hut.reoger.doc.utils.aop.permission.AspectPermissionAnnotation;

/**
 * Created by reoger on 2018/4/14.
 */

public class testActivity extends AppCompatActivity{

//    @AspectLogTest.AspectLog(value = "hello 测试")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_doc);
        //chePer();

        initView();
    }

    private void initView() {
        TabLayout tableLayout = findViewById(R.id.timeline_tablayout);
        ViewPager viewPager = findViewById(R.id.timeline_viewpager);
        MyDocAdapter myDocAdapter = new MyDocAdapter(this,getSupportFragmentManager());
        viewPager.setAdapter(myDocAdapter);
        tableLayout.setupWithViewPager(viewPager);
    }

    @AspectPermissionAnnotation(value = Manifest.permission.CAMERA)
    private void chePer() {
        Toast.makeText(this,"检查权限",Toast.LENGTH_SHORT).show();
    }


}
