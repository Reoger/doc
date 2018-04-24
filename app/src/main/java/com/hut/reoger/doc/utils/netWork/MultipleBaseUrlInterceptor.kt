package com.hut.reoger.doc.utils.netWork

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by reoger on 2018/4/24.
 */

class MultipleBaseUrlInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取原始的originalRequest
        val originalRequest = chain.request()
        //获取老的url
        val oldUrl = originalRequest.url()
        //获取originalRequest的创建者builder
        val builder = originalRequest.newBuilder()
        //获取头信息的集合如：user,doc
        val urlnameList = originalRequest.headers("url")
        if (urlnameList != null && urlnameList.size > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader("url")
            //获取头信息中配置的value,如：manage或者mdffx
            val urlname = urlnameList[0]
            var baseURL: HttpUrl? = null
            //根据头信息中配置的value,来匹配新的base_url地址
            if ("doc" == urlname) {
                baseURL = HttpUrl.parse(ApiClient.BASE_URL_DOC)
            } else if ("user" == urlname) {
                baseURL = HttpUrl.parse(ApiClient.BASE_URL_USER)
            }
            //重建新的HttpUrl，需要重新设置的url部分
            val newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL!!.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .build()
            //获取处理后的新newRequest
            val newRequest = builder.url(newHttpUrl).build()
            return chain.proceed(newRequest)
        } else {
            return chain.proceed(originalRequest)
        }

    }
}
