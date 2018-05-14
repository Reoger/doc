package com.hut.reoger.doc.user.view.fragment

import android.app.Fragment

/**
 * Created by reoger on 2018/5/14.
 */
class TestFragment : Fragment(){

    companion object {
        fun getInstance ():TestFragment{
            return innerClass.instance
        }
    }
    private object innerClass{
        val instance = TestFragment()
    }
}