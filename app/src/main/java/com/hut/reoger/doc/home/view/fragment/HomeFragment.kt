package com.hut.reoger.doc.home.view

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import com.example.cm.mytestdemo.base.BaseFragment
import com.example.cm.mytestdemo.home.view.fragment.IHomeFragmentView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.presenter.HomeFragmentPresenter
import com.hut.reoger.doc.home.presenter.IHomeFragmentPresenter
import com.hut.reoger.doc.search.view.SearchResultActivity
import com.hut.reoger.doc.utils.imageUtils.GlideImageLoader

import com.youth.banner.Banner
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout


/**
 * Created by CM on 2018/1/28.
 */

class HomeFragment : BaseFragment(),  android.widget.SearchView.OnQueryTextListener, IHomeFragmentView {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun loadSuccessful(data: String) {
        openActivity(SearchResultActivity::class.java,"search",data)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter?.doSearch(query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("debug","输入监控："+newText)
        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater?.inflate(R.layout.fragment_home, container, false)!!

        presenter = HomeFragmentPresenter(view.context,this)

        initView(view)


        val rr= listOf("牙签","猫","小可爱")
        val mFlowLayout = view.findViewById<TagFlowLayout>(R.id.id_flowlayout)

        mFlowLayout.adapter = object : TagAdapter<String>(rr) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = inflater.inflate(R.layout.tv,
                        mFlowLayout, false) as TextView
                tv.text = s
                return tv
            }
        }

        mFlowLayout.setOnTagClickListener({ _, position, _ ->
            Log.d("TAG","这里显示的是主要的"+position)
            textView?.text = rr[position]
            presenter?.doSearch(rr[position])
            true
        })

        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView(v: View) {

        linearMain = v.findViewById(R.id.home_search_show_linear)
        linearSearch = v.findViewById(R.id.search_show_linear)


        val list = listOf("http://www.ty-print.com/uploadfile/image/20171025/20171025154212611261_ZYCH.jpg",
                "http://n.sinaimg.cn/news/transform/20161201/tBYL-fxyiayt5451778.jpg",
                "http://www.cfa.com.cn/uploads/160218/2-16021Q50J2431.png")
         val a:SearchView = v.findViewById(R.id.fragment_home_search)
        a.setOnQueryTextListener(this)
        a.isSubmitButtonEnabled = true
        a.queryHint = "查找"

        val id = a.context.resources.getIdentifier("android:id/search_src_text", null, null)
//        textView = a!!.findViewById(id) as TextView
//        textView?.setTextColor(Color.RED)//字体颜色
//        textView?.textSize = 20f//字体、提示字体大小
//        textView?.setHintTextColor(Color.BLUE)//提示字体颜色


        a?.setOnQueryTextFocusChangeListener { view, b ->
            log("总的打印店什么"+view.toString()+" : "+b)
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




    }

}