package com.hut.reoger.doc.read.view

/**
 * Created by reoger on 2018/3/25.
 * 阅读界面的接口
 */
interface IReadView{
    /**
     * 更新下载进度
     */
    fun updateProgress(progress:Int)
}