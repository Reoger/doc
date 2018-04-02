package com.hut.reoger.doc.about.view

import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.about.presenter.AboutPresenter
import com.hut.reoger.doc.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base.*

/**
 * Created by reoger on 2018/3/31.
 */
class AboutActivity : BaseActivity(),IView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
    override fun setActionBar() {
        setActivityTitle("关于")
    }

    override fun initView() {
        val presenter = AboutPresenter(this,this)
        val fragmentView = AboutPreferenceFragment()
        fragmentView.setPresent(presenter)
        supportFragmentManager.beginTransaction().add(R.id.fragment_about, fragmentView).commit()
    }

}