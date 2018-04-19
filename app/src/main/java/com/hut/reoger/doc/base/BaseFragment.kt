package com.hut.reoger.doc.base

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * Created by CM on 2018/2/1.
 *
 */
abstract class BaseFragment : Fragment() {

    private var mRootView:View ?=null

    protected var minflater:LayoutInflater ?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = inflater.inflate(getLayoutId(),container,false)
        minflater = inflater
        initView(mRootView!!)
        return mRootView!!
    }



    fun toast(str: String) {
        Toast.makeText(activity, str, Toast.LENGTH_SHORT).show()
    }



    fun openActivity(targetActivityClass: Class<*>, targetName: String, targetMessage: String?) {
        val intent = Intent(activity, targetActivityClass)
        targetMessage?.let { intent.putExtra(targetName, targetMessage) }
        startActivity(intent)
    }


    @JvmOverloads
    fun openActivity(targetActivityClass: Class<*>, bundle: Bundle? = null) {
        val intent = Intent(activity, targetActivityClass)
        bundle?.let { intent.putExtras(bundle) }
        startActivity(intent)
    }

    abstract fun getLayoutId():Int

    abstract fun initView(mRootView: View)

}