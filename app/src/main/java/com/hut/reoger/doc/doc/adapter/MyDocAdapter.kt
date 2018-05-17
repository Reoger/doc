package com.hut.reoger.doc.doc.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.doc.view.DownloadDocFragment
import com.hut.reoger.doc.doc.view.DownloadingDocFragment


/**
 * Created by reoger on 2018/5/17.
 *
 */
class MyDocAdapter(val mContext:Context,val fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    companion object {
        const val FRAGMENT_DOWNLOADING = 0
        const val FRAGMENT_DOWNLOAD = 1

        const val FRAGMENT_SIZE = 2
    }

    var list :List<BaseFragment> ?=null

    override fun getItem(position: Int): Fragment {
       when(position){
           FRAGMENT_DOWNLOADING->{
               return DownloadingDocFragment.getInstance()
           }
           FRAGMENT_DOWNLOAD->{
               return DownloadDocFragment.getInstance()
           }
       }
        return DownloadDocFragment.getInstance()
    }

    override fun getCount(): Int {
        return FRAGMENT_SIZE
    }

    override fun getPageTitle(position: Int): CharSequence {
        when(position){
            FRAGMENT_DOWNLOADING->{
                return "正在下载"
            }
            FRAGMENT_DOWNLOAD->{
                return "下载完成"
            }
        }
        return "默认标题"
    }


}