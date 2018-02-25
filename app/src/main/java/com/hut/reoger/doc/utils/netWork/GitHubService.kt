package com.hut.reoger.doc.utils.netWork


import com.hut.reoger.doc.bean.Repo
import com.hut.reoger.doc.home.model.ResultBySearchContent
import com.hut.reoger.doc.home.model.SearchByContentBean
import com.hut.reoger.doc.user.model.LoginInfo
import com.hut.reoger.doc.user.model.RegisterInfo
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by CM on 2018/1/25.
 */

interface GitHubService{
    //请添加相应的`API`调用方法
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Observable<List<Repo>> //每个方法的返回值即一个Observable

    @FormUrlEncoded
    @POST("userMaster/api/login.php")
    fun login(@Field("user") user:String,@Field("passwd") passwd: String ): Observable<LoginInfo>

    @FormUrlEncoded
    @POST("userMaster/api/register.php")
    fun register(@Field("user_name")user:String,@Field("user_passwd")passwd: String,@Field("nickname")nickname:String,@Field("user_birthday")user_birthday:String
    ,@Field("user_email")user_email:String,@Field("user_introduction")user_introduction:String): Observable<RegisterInfo>

    @POST("_search")
    fun searchByContent(@Body searchByContentBean: SearchByContentBean): Observable<ResultBySearchContent>


}
