package com.hut.reoger.doc.home.view

import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivity

/**
 * Created by reoger on 2018/4/15.
 */
class CommentActivity :BaseActivity(){

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setContentView(R.layout.activity_main)
    }

    override fun setActionBar() {
        setActivityTitle("我的评论")
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}