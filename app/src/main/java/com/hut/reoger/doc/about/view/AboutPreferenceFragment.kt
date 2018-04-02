package com.hut.reoger.doc.about.view

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.hut.reoger.doc.R
import com.hut.reoger.doc.about.presenter.AboutPresenter
import com.hut.reoger.doc.about.presenter.IPresenter
import com.hut.reoger.doc.utils.log.LogUtils

/**
 * Created by reoger on 2018/3/31.
 * 关于界面的fragment
 */
class AboutPreferenceFragment : PreferenceFragmentCompat(){

    var mPresenter:IPresenter?=null

    fun setPresent(presenter:IPresenter?){
        this.mPresenter =  presenter
    }

    override fun onCreatePreferences(bundle: Bundle?, rootKey: String?) {

        addPreferencesFromResource(R.xml.about_preference_fragment)
        findPreference("github_open").setOnPreferenceClickListener {
            mPresenter?.doOpenGithub(activity.getString(R.string.github_url))
            false
        }


        findPreference("blog").setOnPreferenceClickListener {
            LogUtils.d("doSomething")
            false
        }

        findPreference("blog_app").setOnPreferenceClickListener {
            LogUtils.d("doSomething")
            false
        }

    }

}