package com.hut.reoger.doc.home.presenter

import com.hut.reoger.doc.search.bean.HitsItem

/**
 * Created by CM on 2018/1/28.
 */
interface IHomeFragmentPresenter {
    fun doSearch(str: String): Boolean

    fun loadHistoryFormDB():List<HitsItem>?
}