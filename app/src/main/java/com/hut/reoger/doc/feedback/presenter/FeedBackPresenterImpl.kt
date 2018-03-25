package com.hut.reoger.doc.feedback.presenter

import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.feedback.view.IFeedBackView
import com.hut.reoger.doc.utils.log.TLog
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.netWork.ApiResponse
import com.hut.reoger.doc.utils.netWork.NetworkScheduler
import com.hut.reoger.doc.utils.safe.VersionInfos
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent

/**
 * Created by reoger on 2018/3/19.
 */

class FeedBackPresenterImpl(var mContext: BaseActivity?, var mFeedback: IFeedBackView?) : IFeedBackPresenter{


    override fun doFeedBack(content: String, links: String) {
        ApiClient.instance.service.feedBack(1,VersionInfos.getDid(mContext!!),VersionInfos.getVersion(mContext!!),VersionInfos.getMinVersion(), content,links)
                .compose(NetworkScheduler.compose())
                .bindUntilEvent(mContext!!, event = ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<ServiceReply>(mContext!!) {
                    override fun success(data: ServiceReply) {
                        if (data.code == 2000){
                            mFeedback?.feedBackSuccessful()
                            TLog.d("YY", "测试成功$data")
                        } else{
                            mFeedback?.feedBackFail("---"+data.toString())
                            TLog.d("YY", "测试失败$data")
                        }

                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        TLog.d("YY","失败"+apiErrorModel.message)
                        mFeedback?.feedBackFail(apiErrorModel.message)
                    }
                })
    }

}