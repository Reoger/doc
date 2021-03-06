package com.hut.reoger.doc.home.view.fragment


import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import com.hut.reoger.doc.base.BaseFragment
import com.example.cm.mytestdemo.home.view.fragment.IHomeFragmentView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.adapter.HistoryAdapter
import com.hut.reoger.doc.home.presenter.HomeFragmentPresenter
import com.hut.reoger.doc.home.presenter.IHomeFragmentPresenter
import com.hut.reoger.doc.read.view.DocumentReaderActivity
import com.hut.reoger.doc.search.adapter.ItemClickSupport
import com.hut.reoger.doc.search.view.SearchResultActivity
import com.hut.reoger.doc.utils.imageUtils.GlideImageLoader
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.log.TLog

import com.youth.banner.Banner
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout


/**
 * Created by CM on 2018/1/28.
 */

class HomeFragment : BaseFragment(),  android.widget.SearchView.OnQueryTextListener, IHomeFragmentView {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }


    var presenter: IHomeFragmentPresenter?= null

    var banner : Banner? = null

    var textView :TextView ?=null

    var linearMain :LinearLayout ?= null

    var linearSearch :LinearLayout ?= null



    companion object {
        fun getInstance(): HomeFragment {
            return Inner.fragment
        }
    }

    private object Inner{
        val fragment = HomeFragment()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun loadSuccessful(data: String) {
        openActivity(SearchResultActivity::class.java,"search",data)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter?.doSearch(query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("debug","输入监控："+newText)
        return false
    }





     override fun initView(v: View) {
        presenter = HomeFragmentPresenter(v.context,this)
        linearMain = v.findViewById(R.id.home_search_show_linear)
        linearSearch = v.findViewById(R.id.search_show_linear)


        val list = listOf("http://www.ty-print.com/uploadfile/image/20171025/20171025154212611261_ZYCH.jpg",
                "http://n.sinaimg.cn/news/transform/20161201/tBYL-fxyiayt5451778.jpg",
                "http://www.cfa.com.cn/uploads/160218/2-16021Q50J2431.png")
         val a:SearchView = v.findViewById(R.id.fragment_home_search)
        a.setOnQueryTextListener(this)
        a.isSubmitButtonEnabled = true
        a.queryHint = getString(R.string.search)

        val id = a.context.resources.getIdentifier("android:id/search_src_text", null, null)
//        textView = a!!.findViewById(id) as TextView
//        textView?.setTextColor(Color.RED)//字体颜色
//        textView?.textSize = 20f//字体、提示字体大小
//        textView?.setHintTextColor(Color.BLUE)//提示字体颜色


        a?.setOnQueryTextFocusChangeListener { view, b ->
            when(b){
                true->{
                    linearMain?.visibility = View.GONE
                    linearSearch?.visibility = View.VISIBLE
                }
                false->{
                    linearMain?.visibility = View.VISIBLE
                    linearSearch?.visibility = View.GONE
                }
            }
        }
        banner = v.findViewById(R.id.banner)
        banner?.setImageLoader(GlideImageLoader())
        banner?.setImages(list)
        banner?.start()

        //https://github.com/youth5201314/banner

        val historyAdapter = HistoryAdapter(v.context)
        val listView :RecyclerView= v.findViewById(R.id.recycler_history)
        val manager = LinearLayoutManager(v.context, LinearLayoutManager.VERTICAL, false)
        listView.adapter = historyAdapter
        listView.layoutManager = manager

        val historyData = presenter?.loadHistoryFormDB()
        if (null==historyData|| historyData.isEmpty()){
            //提示没有历史数据
            LogUtils.d("暂时没有任何数据")
        }else {
            historyAdapter.setData(historyData)
            LogUtils.d("加载了数据")
        }
        ItemClickSupport.addTo(listView).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                //点击事件,测试一下
//                val url = "http://www.hrssgz.gov.cn/bgxz/sydwrybgxz/201101/P020110110748901718161.doc"
                val item = historyData!![position]
                val url = "http://"+item.Source.downLink
                val docId = item.Id
                val bundle = Bundle()
                LogUtils.d("doc_id =$docId ,url = $url")
                bundle.putString(DocumentReaderActivity.DOC_ID,docId)
                bundle.putString(DocumentReaderActivity.DOC_URL,url)
                openActivity(DocumentReaderActivity::class.java, bundle)
            }
        })

        val rr= listOf("牙签","猫","小可爱")
        val mFlowLayout = v.findViewById<TagFlowLayout>(R.id.id_flowlayout)

        mFlowLayout.adapter = object : TagAdapter<String>(rr) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = minflater?.inflate(R.layout.tv,
                        mFlowLayout, false) as TextView
                tv.text = s
                return tv
            }
        }

        mFlowLayout.setOnTagClickListener({ _, position, _ ->
            TLog.d("TAG", "这里显示的是主要的$position")
            textView?.text = rr[position]
            presenter?.doSearch(rr[position])
            true
        })

    }

}