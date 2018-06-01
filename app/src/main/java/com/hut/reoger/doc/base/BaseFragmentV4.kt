package com.hut.reoger.doc.base


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by reoger on 2018/5/17.
 */

abstract class BaseFragmentV4 : Fragment() {

    protected var mContext: Context?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       val mRootView = inflater.inflate(getLayoutId(),container,false)
        mContext = activity
        initView(mRootView)
        return mRootView
    }



    fun toast(str: String) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show()
    }



    fun openActivity(targetActivityClass: Class<*>, targetName: String, targetMessage: String?) {
        val intent = Intent(mContext, targetActivityClass)
        targetMessage?.let { intent.putExtra(targetName, targetMessage) }
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mContext !=null) mContext = null
    }


    @JvmOverloads
    fun openActivity(targetActivityClass: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(mContext, targetActivityClass)
        bundle?.let { intent.putExtras(bundle) }
        startActivity(intent)
    }

    abstract fun getLayoutId():Int

    abstract fun initView(mRootView: View)

}