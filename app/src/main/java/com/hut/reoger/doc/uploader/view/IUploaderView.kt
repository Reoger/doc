package com.hut.reoger.doc.uploader.view

/**
 * Created by reoger on 2018/3/28.
 * 上传文件界面的接口
 */
interface IUploaderView{
    /**
     * 文件上传成功
     */
    fun uploaderFileSuccessful(doc_id:String)

    /**
     * 文件上传失败
     */
    fun uploaderFileFail(error:String)
}