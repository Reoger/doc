package com.hut.reoger.doc.user.view

import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivityWithMainFragment
import com.hut.reoger.doc.user.view.fragment.UserFragment

/**
 * Created by reoger on 2018/5/14.
 * 用户界面
 */
class UserActivity : BaseActivityWithMainFragment(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_contet)
    }
    override fun setActionBar() {
        setActivityTitle(R.string.file_upload)
    }

    override fun initView() {
        fragmentManager.beginTransaction().add(R.id.fragment_main, UserFragment.getInstance()).commit()
    }

}