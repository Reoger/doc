package com.hut.reoger.doc.doc.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragmentV4
import com.hut.reoger.doc.doc.adapter.DownLoadAdapter
import com.hut.reoger.doc.doc.adapter.MyDocAdapter
import com.hut.reoger.doc.utils.downloader.service.DownloadService
import com.hut.reoger.doc.utils.log.LogUtils

/**
 * Created by reoger on 2018/5/17.
 * 已经下载的文档
 */

class DownloadDocFragment : BaseFragmentV4() {

    companion object {
        fun getInstance(): DownloadDocFragment {
            return innerClasss.instance
        }
    }

    object innerClasss {
        val instance = DownloadDocFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_downloaded
    }

    override fun initView(mRootView: View) {
        val recyclerView = mRootView.findViewById<RecyclerView>(R.id.recycler_downloaded)
        val linearLayoutManager  = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        val docAdapter = DownLoadAdapter(context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = docAdapter
        DownloadService.downloadTask?.let {
            LogUtils.d("正在下载的列表${it.size}")
            docAdapter.setData(it) }
    }

}