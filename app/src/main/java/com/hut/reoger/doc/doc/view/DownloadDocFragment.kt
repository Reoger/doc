package com.hut.reoger.doc.doc.view

import android.view.View
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragmentV4

/**
 * Created by reoger on 2018/5/17.
 * 已经下载的文档
 */

class DownloadDocFragment : BaseFragmentV4(){

    companion object {
        fun getInstance():DownloadDocFragment{
            return innerClasss.instance
        }
    }

    object innerClasss{
        val instance = DownloadDocFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_downloaded
    }

    override fun initView(mRootView: View) {

    }

}