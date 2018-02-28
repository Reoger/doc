package com.hut.reoger.doc.home.presenter

import android.content.Context
import android.util.Log
import com.hut.reoger.doc.home.model.Match
import com.hut.reoger.doc.home.model.Query
import com.hut.reoger.doc.home.model.ResultBySearchContent
import com.hut.reoger.doc.home.model.SearchByContentBean
import com.hut.reoger.doc.home.view.HomeFragment
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.netWork.ApiResponse
import com.hut.reoger.doc.utils.netWork.NetworkScheduler


/**
 * Created by CM on 2018/1/28.
 */
class HomeFragmentPresenter(var mContext: Context, var mHomeView: HomeFragment): IHomeFragmentPresenter {

    override fun doSearch(str: String): Boolean {
        ApiClient.instance.service.searchByContent(SearchByContentBean(0,10, Query(Match(str))))
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<ResultBySearchContent>(context = mContext){
                    override fun success(data: ResultBySearchContent) {
                        Log.d("TAG","${data.took} == ${data.hits.total} =="+data.timedOut)
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        Log.d("TAG","$apiErrorModel 加载失败")
                    }
                })
        return true
    }

}