package com.hut.reoger.doc.utils.netWork

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by reoger on 2018/3/2.
 */
interface LoadFileApi {

    @GET
    fun loadPdfFile(@Url fileUrl: String): Call<ResponseBody>
}
