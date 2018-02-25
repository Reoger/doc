package com.hut.reoger.doc

import android.app.Application

/**
 * Created by reoger on 2018/2/25.
 */

class App : Application()  {

    companion object {
        val instance: App by lazy { App() }
    }
    //App.instance 获得单例模式
    override fun onCreate() {
        super.onCreate()
        ApiClient.instance.init()
    }
}
