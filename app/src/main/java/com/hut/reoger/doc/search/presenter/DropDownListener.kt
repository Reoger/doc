package com.hut.reoger.doc.search.presenter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager



/**
 * Created by reoger on 2018/3/25.
 * 添加下拉加载更多的监听器
 */

abstract class DropDownListener(//声明一个LinearLayoutManager
        private val mLinearLayoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    //当前页，从0开始
    private var currentPage = 0
    //已经加载出来的Item的数量
    private var totalItemCount: Int = 0

    //主要用来存储上一个totalItemCount
    private var previousTotal = 0

    //在屏幕上可见的item数量
    private var visibleItemCount: Int = 0

    //在屏幕可见的Item中的第一个
    private var firstVisibleItem: Int = 0

    //是否正在上拉数据
    private var loading = true

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()
        if (loading) {
            if (totalItemCount > previousTotal) {
                //说明数据已经加载结束
                loading = false
                previousTotal = totalItemCount
            }
        }
        //当没有在加载且 已经加载出来的item数量-屏幕上可见的item数量<=屏幕上可见的第一个item的序号（即已经滑倒底的时候）
        //执行相应的操作
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem) {
            currentPage++
            onLoadMore(currentPage)
            loading = true
        }
    }

    /**
     * 提供一个抽闲方法，在Activity中监听到这个EndLessOnScrollListener
     * 并且实现这个方法
     */
    abstract fun onLoadMore(currentPage: Int)
}