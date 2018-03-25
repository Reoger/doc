package com.hut.reoger.doc.search.view


import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.search.adapter.SearchResultAdapter
import com.hut.reoger.doc.search.bean.*
import kotlinx.android.synthetic.main.activity_search_result.*
import java.lang.ref.WeakReference
import com.hut.reoger.doc.search.presenter.DropDownListener
import com.hut.reoger.doc.search.presenter.SearchResultPresenterImpl
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hut.reoger.doc.read.DocumentReaderActivity
import com.hut.reoger.doc.search.adapter.ItemClickSupport




/**
 * Created by CM on 2018/2/1.
 * 搜索结果页
 */

class SearchResultActivity : BaseActivity(),ISearchResultView {


    private var myHandler :MyHandler ?=null
    private var mSearchPresenter :SearchResultPresenterImpl?= null
    private var mAdapter :SearchResultAdapter ?=null


    companion object {
        const val  SHOW_SEARCH_RESULT = 0x0001
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        myHandler = MyHandler(this)

    }

    override fun setActionBar() {
        setActivityTitle("搜索结果")
    }

    override fun initView() {
        mAdapter = SearchResultAdapter(this)
        recycler_search_result.adapter = mAdapter
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_search_result.layoutManager = manager
        val data = intent.getStringExtra("search")
        tv_search.text = data

        mSearchPresenter =  SearchResultPresenterImpl(this,this)
        mSearchPresenter?.loadSearch(0,data)

        recycler_search_result.addOnScrollListener(object : DropDownListener(manager) {
            override fun onLoadMore(currentPage: Int) {
                //实现加载更多
                mSearchPresenter?.loadSearch(currentPage,data)
            }
        })

        ItemClickSupport.addTo(recycler_search_result).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
               //点击事件,测试一下
                toast("点击事件$position")
                openActivity(DocumentReaderActivity::class.java)
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        MyHandler(this).removeCallbacksAndMessages(null)
    }


    override fun loadSucceeful(data: ResultAsSearchBean) {
        val msg = Message()
        msg.what = SHOW_SEARCH_RESULT
        msg.obj = "总共找到+${data.hits.total}个相关文件，相关系数最高为${data.hits.maxScore}"
        myHandler?.sendMessage(msg)
        mAdapter?.setData(data.hits)
    }

    override fun loadFail(str: String) {
        toast(str)
    }


    private class MyHandler(activity: Activity) : Handler() {
        private val mActivity: WeakReference<Activity> = WeakReference(activity)

        override fun handleMessage(msg: Message) {
            if (mActivity.get() == null) {
                return
            }
            val activity = mActivity.get()
            when (msg.what) {
                SHOW_SEARCH_RESULT-> {
                    activity?.tv_show_search_result?.text = msg.obj.toString()
                }
                else -> {
                }
            }
        }
    }
}