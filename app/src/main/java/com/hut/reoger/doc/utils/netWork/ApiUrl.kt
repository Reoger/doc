package com.hut.reoger.doc.utils.netWork


import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.read.bean.CommentsByDoc
import com.hut.reoger.doc.search.bean.ResultAsSearchBean
import com.hut.reoger.doc.search.bean.SearchByContentBean
import com.hut.reoger.doc.user.model.LoginInfo
import com.hut.reoger.doc.user.model.RegisterInfo
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by CM on 2018/1/25.
 */

interface ApiUrl {

    @Headers("url:doc")
    @POST("_search")
    fun searchByContent(@Body searchByContentBean: SearchByContentBean): Observable<ResultAsSearchBean>

    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/login.php")
    fun login(@Field("user") user: String, @Field("passwd") passwd: String): Observable<LoginInfo>


    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/loginout.php")
    fun logout(@Field("token") token: String): Observable<ServiceReply>


    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/register.php")
    fun register(@Field("user_name") user: String, @Field("user_passwd") passwd: String, @Field("nickname") nickname: String, @Field("user_birthday") user_birthday: String
                 , @Field("user_email") user_email: String, @Field("user_introduction") user_introduction: String): Observable<RegisterInfo>


    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/feedback.php")
    fun feedBack(@Field("app_id") app_id: Int, @Field("did") did: String, @Field("version_id") version_id: Int,
                 @Field("version_mini") version_mini: Int, @Field("feedback_log") feedback_log: String, @Field("links") links: String
    ): Observable<ServiceReply>


    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/error.php")
    fun feedError(@Field("app_id") app_id: Int, @Field("did") did: String, @Field("version_id") version_id: Int,
                  @Field("version_mini") version_mini: Int, @Field("error_log") feedback_log: String): Observable<ServiceReply>

    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/insertComment.php")
    fun insertComment(@Field("token") token: String, @Field("comment_user_name") comment_user_name: String, @Field("comment_content") comment_content: String,
                      @Field("comment_score") comment_score: Int, @Field("comment_doc_id") comment_doc_id: String): Observable<ServiceReply>

    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/deleteComment.php")
    fun deleteComment(@Field("token") token: String, @Field("comment_id") comment_id: Int): Observable<ServiceReply>

    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/queryCommentsByUser.php")
    fun queryCommentByUserID(@Field("token") token: String): Observable<CommentsByDoc>

    @Headers("url:user")
    @FormUrlEncoded
    @POST("userMaster/api/queryCommentsByDoc.php")
    fun queryCommentByDocId(@Field("token") token: String, @Field("comment_doc_id") comment_doc_id: String): Observable<CommentsByDoc>

}
