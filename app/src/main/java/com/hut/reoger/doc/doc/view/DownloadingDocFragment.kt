package com.hut.reoger.doc.doc.view

import android.view.View
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragmentV4

/**
 * Created by reoger on 2018/5/17.
 * 正在下载的文档
 */

class DownloadingDocFragment : BaseFragmentV4(){

    companion object {
        fun getInstance():DownloadingDocFragment{
            return innerClasss.instance
        }
    }

    object innerClasss{
        val instance = DownloadingDocFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_downloading
    }

    override fun initView(mRootView: View) {

    }

}