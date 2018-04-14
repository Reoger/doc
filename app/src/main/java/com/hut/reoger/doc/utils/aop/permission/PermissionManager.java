package com.hut.reoger.doc.utils.aop.permission;

/**
 * Created by reoger on 2018/4/14.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by CM on 2018/1/18.
 *
 */

public class PermissionManager {
    private static volatile PermissionManager permissionManager;

    public PermissionManager(){}

    //DCL单例模式
    public static PermissionManager getInstance(){
        if (permissionManager == null){
            synchronized (PermissionManager.class){
                if (permissionManager == null){
                    permissionManager = new PermissionManager();
                }
            }
        }
        return permissionManager;
    }

    private static class InnerInsatance{
        public static final PermissionManager instance = new PermissionManager();
    }

    //内部类单例模式
    public static PermissionManager getInnerInstance(){
        synchronized (PermissionManager.class){
            return InnerInsatance.instance;
        }
    }

    public boolean checkPermission(Context context, String permission){
        Log.i("TAG","检查的权限："+permission);
        if (ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }
}