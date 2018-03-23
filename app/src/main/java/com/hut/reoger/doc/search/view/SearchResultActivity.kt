package com.hut.reoger.doc.search.view


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.cm.mytestdemo.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.model.ResultBySearchContent
import com.hut.reoger.doc.search.adapter.SearchResultAdapter
import com.hut.reoger.doc.search.bean.Match
import com.hut.reoger.doc.search.bean.Query
import com.hut.reoger.doc.search.bean.SearchByContent
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.netWork.ApiResponse
import com.hut.reoger.doc.utils.netWork.NetworkScheduler
import kotlinx.android.synthetic.main.activity_search_result.*


/**
 * Created by CM on 2018/2/1.
 */

class SearchResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

    }

    override fun setActionBar() {
        setActivityTitle("搜索结果")
    }

    override fun initView() {
        val adapter = SearchResultAdapter(this)
        recycler_search_result.adapter = adapter
        recycler_search_result.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val data = intent.getStringExtra("search")
        tv_search.text = data

        ApiClient.instance.service.searchByContent(SearchByContent(0, 10, Query(Match(data)),listOf("name","integer","time","organization")))
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<ResultBySearchContent>(context = this) {
                    override fun success(data: ResultBySearchContent) {
                        Log.d("TAG", "${data.took} == ${data.hits.total} ==" + data.timedOut)
                        adapter.setData(data.hits.hits)
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        Log.d("TAG", "$apiErrorModel 加载失败")
                    }
                })

    }

}