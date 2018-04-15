package com.hut.reoger.doc.base

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Toast

/**
 * Created by CM on 2018/2/1.
 *
 */
abstract class BaseFragment : Fragment() {

    //基础的fragment
    fun toast(str: String) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun openActivity(targetActivityClass: Class<*>, targetName: String, targetMessage: String?) {
        val intent = Intent(activity, targetActivityClass)
        targetMessage?.let { intent.putExtra(targetName, targetMessage) }
        startActivity(intent)
    }


    @SuppressLint("NewApi")
    @JvmOverloads
    fun openActivity(targetActivityClass: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(activity, targetActivityClass)
        bundle?.let { intent.putExtras(bundle) }
        startActivity(intent)
    }
}