package com.hut.reoger.doc.tool.view

import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivityWithMainFragment
import com.hut.reoger.doc.tool.view.fragment.TextToImageFragment

/**
 * Created by reoger on 2018/4/22.
 */
class TextToImageActivity :BaseActivityWithMainFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_contet)
    }
    override fun setActionBar() {
        setActivityTitle("文字转图片")
    }

    override fun initView() {
        val fragment = TextToImageFragment()
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_main,fragment).commit()
    }

}