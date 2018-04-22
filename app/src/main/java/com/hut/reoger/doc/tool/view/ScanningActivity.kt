package com.hut.reoger.doc.tool.view

import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivityWithMainFragment
import com.hut.reoger.doc.tool.view.fragment.ScanningFragment

/**
 * Created by reoger on 2018/4/18.
 *
 */
class ScanningActivity : BaseActivityWithMainFragment() {
    override fun setActionBar() {
        setActivityTitle("扫描")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_contet)
    }

    override fun initView() {
        fragment = ScanningFragment.getInstance()
        fragmentManager.beginTransaction().replace(R.id.fragment_main,fragment).commit()
    }

}