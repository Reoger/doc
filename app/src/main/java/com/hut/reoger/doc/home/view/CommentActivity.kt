package com.hut.reoger.doc.home.view

import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivityWithMainFragment
import com.hut.reoger.doc.home.view.fragment.CommentDocFragment

/**
 * Created by reoger on 2018/4/15.
 *
 */
class CommentActivity : BaseActivityWithMainFragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_contet)
    }


    override fun setActionBar() {
        setActivityTitle("我的评论")
    }

    override fun initView() {
         fragment  = CommentDocFragment.getInstance()
        fragmentManager.beginTransaction().replace(R.id.fragment_main,fragment).commit()
    }

}