package com.hut.reoger.doc.tool.view

import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivityWithMainFragment
import com.hut.reoger.doc.tool.view.fragment.TextToQRCodeFragment

/**
 * Created by reoger on 2018/4/23.
 */
class TextToQRCodeActivity :BaseActivityWithMainFragment(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_contet)
    }
    override fun setActionBar() {
        setActivityTitle("文字转二维码")
    }

    override fun initView() {
        val fragment = TextToQRCodeFragment()
        fragmentManager.beginTransaction().replace(R.id.fragment_main,fragment).addToBackStack(null).commit()
    }

}