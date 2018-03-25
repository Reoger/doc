package com.hut.reoger.doc.search.presenter

/**
 * Created by reoger on 2018/3/25.
 * 实现的接口
 */
interface ISearchResultPresenter{
    /**
     * 加载网络请求
     */
    fun loadSearch(pos:Int,data:String)

}