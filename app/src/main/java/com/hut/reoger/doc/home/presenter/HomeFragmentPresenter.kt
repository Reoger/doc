package com.hut.reoger.doc.home.presenter

import android.content.Context

import com.example.cm.mytestdemo.home.view.fragment.IHomeFragmentView
import com.hut.reoger.doc.search.bean.HitsItem
import com.hut.reoger.doc.utils.db.HistoryDbImpl


/**
 * Created by CM on 2018/1/28.
 * homeFragment的操作类
 */
class HomeFragmentPresenter(var mContext: Context, var mHomeView: IHomeFragmentView): IHomeFragmentPresenter {
    var historyDb : HistoryDbImpl ?= null

    init {
        historyDb = HistoryDbImpl(mContext)
    }
    override fun loadHistoryFormDB(): List<HitsItem>? {
        return historyDb?.getReadHistory()
    }

    override fun doSearch(str: String): Boolean {
//        ApiClient.instance.service.searchByContent(SearchByContentBean(0,10, Query(Match(str))))
//                .compose(NetworkScheduler.compose())
//                .subscribe(object : ApiResponse<ResultBySearchContent>(context = mContext){
//                    override fun success(data: ResultBySearchContent) {
//                        Log.d("TAG","${data.took} == ${data.hits.total} =="+data.timedOut)
//                    }
//
//                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
//                        Log.d("TAG","$apiErrorModel 加载失败")
//                    }
//                })

        mHomeView.loadSuccessful(str)
        return true
    }

}