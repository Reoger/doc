package com.hut.reoger.doc.base

import android.os.Bundle
import android.os.PersistableBundle

/**
 * Created by reoger on 2018/4/15.
 */
abstract class BaseActivityMainFragment :BaseActivity(){
    var  fragment: BaseFragment?=null

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (fragment?.isAdded==true){

        }
    }
}