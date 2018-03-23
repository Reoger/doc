package com.hut.reoger.doc.home.presenter

import android.content.Context

import com.example.cm.mytestdemo.home.view.fragment.IHomeFragmentView





/**
 * Created by CM on 2018/1/28.
 */
class HomeFragmentPresenter(var mContext: Context, var mHomeView: IHomeFragmentView): IHomeFragmentPresenter {

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