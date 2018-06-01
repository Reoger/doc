package com.hut.reoger.doc.uploader.presenter

import android.content.Context
import com.hut.reoger.doc.uploader.view.IUploaderView

import android.util.Log
import com.hut.reoger.doc.App
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.safe.Md5Tool
import okhttp3.*
import java.io.File
import okhttp3.OkHttpClient
import java.io.IOException


/**
 * Created by reoger on 2018/3/28.
 */
class UploaderPresenter(var context: Context?,var IUpview:IUploaderView) : IUploadPresenter{


    override fun uploader(isPrivate: Boolean, abstract: String, author: String,fileUri :String) {

        val file = File(fileUri)
        val type = fileUri.substring(fileUri.indexOf("."))
        val fileName = fileUri.substring(fileUri.lastIndexOf("/"))
        LogUtils.d("type=$type")
        if (!file.exists()) {
            Log.e("TAG", "file is not exit!")
            return
        }


        val requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file)

        val multipartBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user", App.instance.userInfo!!.userName)
                .addFormDataPart("token",App.instance.userInfo!!.token)
                .addFormDataPart("type",type)
                .addFormDataPart("isPrivate", isPrivate.toString())
                .addFormDataPart("abstract",abstract)
                .addFormDataPart("author",author)
                .addFormDataPart("file", fileName, requestBody)
                .build()
        val builder = Request.Builder()
        //这里需要一个文件上传服务器
        val request = builder.post(multipartBody).url(ApiClient.UPLOADER_FULE_URL + "uploader").build()
        val client = OkHttpClient()
        val call = client.newCall(request)


        call.enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                LogUtils.e("Error$e")

                IUpview.uploaderFileFail(call.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: okhttp3.Call, response: Response) {
                IUpview.uploaderFileSuccessful(response.message().toString())
            }
        })

    }

}
