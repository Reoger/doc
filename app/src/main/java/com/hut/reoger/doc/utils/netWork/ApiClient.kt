package com.hut.reoger.doc.utils.netWork

import com.hut.reoger.doc.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by CM on 2018/1/25.
 *
 */
class ApiClient  private constructor(){


    lateinit var service: ApiUrl

    private object Holder {
        val INSTANCE = ApiClient()
    }

    companion object {
        val instance by lazy { Holder.INSTANCE }
        const val UPLOADER_FULE_URL = "www.baidu.com" //上传服务器的url
        const val BASE_URL_DOC = "http://172.22.66.245:9200/"
        const val BASE_URL_USER = "http://172.22.66.245/"
    }

    fun init() {  //在Application的onCreate中调用一次即可
        val okHttpClient = OkHttpClient().newBuilder()
                //输入http连接时的log，也可添加更多的Interceptor
                .addInterceptor(HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                ))
                .addInterceptor(MultipleBaseUrlInterceptor())
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_DOC)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        service = retrofit.create(ApiUrl::class.java)
    }

}