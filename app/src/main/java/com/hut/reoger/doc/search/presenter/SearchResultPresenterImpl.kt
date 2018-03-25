package com.hut.reoger.doc.search.presenter

import android.content.Context
import android.os.Message
import android.util.Log
import com.hut.reoger.doc.search.bean.*
import com.hut.reoger.doc.search.view.ISearchResultView
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.netWork.ApiResponse
import com.hut.reoger.doc.utils.netWork.NetworkScheduler

/**
 * Created by reoger on 2018/3/25.
 */

class SearchResultPresenterImpl(var context: Context,var iSearchview:ISearchResultView) : ISearchResultPresenter{


    override fun loadSearch(pos: Int,data:String) {
        ApiClient.instance.service.searchByContent(SearchByContentBean(Highlight(Fields(NullPoint())), 10, Query(Match(data)),pos,
                listOf("name", "size", "time", "organization","author","down_link","update_time")))
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<ResultAsSearchBean>(context = context) {
                    override fun success(data: ResultAsSearchBean) {
                        iSearchview.loadSucceeful(data)
                    }
                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        iSearchview.loadFail(apiErrorModel.toString())
                    }
                })
    }

}