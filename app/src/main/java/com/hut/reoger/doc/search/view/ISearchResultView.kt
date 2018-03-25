package com.hut.reoger.doc.search.view

import com.hut.reoger.doc.search.bean.ResultAsSearchBean

/**
 * Created by reoger on 2018/3/25.
 */

interface ISearchResultView {
    /**
     * 加载成功
     */
    fun loadSucceeful(data: ResultAsSearchBean)

    /**
     * 加载失败
     */
    fun loadFail(str:String)
}