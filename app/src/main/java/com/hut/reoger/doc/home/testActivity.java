package com.hut.reoger.doc.home;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

//import com.hut.reoger.doc.utils.aop.AspectLogTest;
import com.hut.reoger.doc.utils.aop.permission.AspectPermissionAnnotation;

/**
 * Created by reoger on 2018/4/14.
 */

public class testActivity extends AppCompatActivity{

//    @AspectLogTest.AspectLog(value = "hello 测试")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("你好");
        setContentView(textView);
        chePer();
    }

    @AspectPermissionAnnotation(value = Manifest.permission.CAMERA)
    private void chePer() {
        Toast.makeText(this,"检查权限",Toast.LENGTH_SHORT).show();
    }


}
