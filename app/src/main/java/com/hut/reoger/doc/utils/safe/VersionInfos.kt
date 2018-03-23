package com.hut.reoger.doc.utils.safe

import android.annotation.SuppressLint
import android.content.Context



/**
 * Created by reoger on 2018/3/19.
 */
object VersionInfos {


    @SuppressLint("HardwareIds")
    fun getDid(context: Context):String{
       return  android.provider.Settings.Secure.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
    }

    /**
     * 假设这个就是达版本号
     */
    fun getVersion(context: Context):Int{
        return   try {
            val manager = context.packageManager
            val info = manager.getPackageInfo(context.packageName, 0)
            val version = info.versionCode
            version
        } catch (e: Exception) {
            e.printStackTrace()
           0
        }

    }

    /**
     * 假设这个是小版本号
     */
    fun getMinVersion():Int{
        return 1
    }


}