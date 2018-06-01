package com.hut.reoger.doc

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.firefly1126.permissionaspect.PermissionCheckSDK
import com.hut.reoger.doc.user.model.LoginData
import com.hut.reoger.doc.user.model.LoginInfo
import com.hut.reoger.doc.utils.exception.ExceptionHandler
import com.hut.reoger.doc.utils.loadingUtils.SlidingInAndOutDialogFragment
import com.hut.reoger.doc.utils.netWork.ApiClient

/**
 * Created by reoger on 2018/2/25.
 */

class App : Application() {

    companion object {
        val instance: App by lazy { App() }
    }

    //App.instance 获得单例模式
    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
//        ExceptionHandler.instance.initConfig(this)
        PermissionCheckSDK.init(this@App)
    }


    var userInfo: LoginData? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun showDialog(context: Activity) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        val ft = context.fragmentManager.beginTransaction()
        val prev = context.fragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
//              ft.setCustomAnimations(R.anim.fragment_slide_left_enter, 0);

        val newFragment = SlidingInAndOutDialogFragment()
        newFragment.show(ft, "dialog")
    }
}
