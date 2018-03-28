package com.hut.reoger.doc.uploader.presenter

/**
 * Created by reoger on 2018/3/28.
 */
interface IUploadPresenter{
    fun uploader(isPrivate:Boolean,abstract:String,author:String,fileUri :String)
}