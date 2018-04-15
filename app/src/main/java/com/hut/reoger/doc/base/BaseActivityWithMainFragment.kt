package com.hut.reoger.doc.base

import android.os.Bundle

/**
 * Created by reoger on 2018/4/15.
 */
abstract class BaseActivityWithMainFragment :BaseActivity(){
    companion object {
        const val FRAGMENG = "fragment"
    }
    var  fragment: BaseFragment?=null

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (fragment?.isAdded==true){
            fragmentManager.putFragment(outState,FRAGMENG,fragment)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
       fragment = fragmentManager.getFragment(savedInstanceState,FRAGMENG) as BaseFragment?
    }
}