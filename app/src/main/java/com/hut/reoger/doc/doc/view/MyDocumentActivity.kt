package com.hut.reoger.doc.doc.view

import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.doc.adapter.MyDocAdapter
import kotlinx.android.synthetic.main.activity_my_doc.*

/**
 * Created by reoger on 2018/5/17.
 */
class MyDocumentActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_doc)
    }

    override fun setActionBar() {
        setActivityTitle("我的文档")
    }

    override fun initView() {
        timeline_viewpager.adapter = MyDocAdapter(this,supportFragmentManager)
        timeline_tablayout.setupWithViewPager(timeline_viewpager)
    }

}