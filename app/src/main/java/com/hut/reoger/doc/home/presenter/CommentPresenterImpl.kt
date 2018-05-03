package com.hut.reoger.doc.home.presenter

import android.content.Context
import com.hut.reoger.doc.App
import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.home.view.fragment.ICommentDocView
import com.hut.reoger.doc.read.bean.CommentsByDoc
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.netWork.ApiResponse
import com.hut.reoger.doc.utils.netWork.NetworkScheduler

/**
 * Created by reoger on 2018/4/15.
 */
class CommentPresenterImpl(val context:Context, val viewPoxy:ICommentDocView) : ICommentPresenter{
    override fun deleteMarkComment(comment_id: Int,pos:Int) {
        ApiClient.instance.service.deleteComment(App.instance.userInfo!!.token,comment_id)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<ServiceReply>(context) {
                    override fun success(data: ServiceReply) {
                       if (data.code == 2000){
                           viewPoxy.deleteCommentSuccessful(pos)
                       }else{
                           viewPoxy.loadCommentFaile(data.message)
                       }
                    }
                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        viewPoxy.loadCommentFaile(apiErrorModel.toString())
                    }
                })
    }

    override fun getMarkListByUsrId(use_id: String) {
        ApiClient.instance.service.queryCommentByUserID(App.instance.userInfo!!.token)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<CommentsByDoc>(context) {
                    override fun success(data: CommentsByDoc) {
                        LogUtils.d("加载评论成功$data")
                        if (data.code == 2000) {
                            viewPoxy.loadCommentSuccessful(data)
                        }
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        viewPoxy.loadCommentFaile(apiErrorModel.toString())
                    }

                })
    }

}