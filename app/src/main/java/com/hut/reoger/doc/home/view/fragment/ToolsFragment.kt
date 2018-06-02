package com.hut.reoger.doc.home.view.fragment

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.base.LoadingPopuWindow
import com.hut.reoger.doc.home.adapter.ToolAdapter
import com.hut.reoger.doc.home.model.ToolBean
import com.hut.reoger.doc.search.adapter.ItemClickSupport
import com.hut.reoger.doc.tool.view.ScanningActivity
import com.hut.reoger.doc.tool.view.TextToImageActivity
import com.hut.reoger.doc.tool.view.TextToQRCodeActivity

/**
 * Created by CM on 2018/2/1.
 */
class ToolsFragment : BaseFragment() {

    companion object {
        fun getInstance(): ToolsFragment {
            return Inner.fragment
        }
    }

    private object Inner {
        val fragment = ToolsFragment()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_tools
    }


    override fun initView(mRootView: View) {
        val recyclerView = mRootView.findViewById<RecyclerView>(R.id.recycler_tool)
        val toolAdapter = ToolAdapter(mContext = activity)
        recyclerView.adapter = toolAdapter
        val manager: RecyclerView.LayoutManager = GridLayoutManager(activity, 3)
        recyclerView.layoutManager = manager
        toolAdapter.initData(initData())

        val login = LoadingPopuWindow(recyclerView)
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                toast("点击了这里$position")
                when(position){
                    0->{
                       openActivity(TextToImageActivity::class.java)
                    }
                    1->{
                        openActivity(ScanningActivity::class.java)
                    }
                    2->{
                       openActivity(TextToQRCodeActivity::class.java)
                    }
                }
            }

        })
    }

    private fun initData(): List<ToolBean> {
        return listOf(ToolBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3217764537,3208946253&fm=27&gp=0.jpg", getString(R.string.text2image)),
                ToolBean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3382991775,1398157683&fm=27&gp=0.jpg", getString(R.string.QRCode)),
                ToolBean("http://ku.90sjimg.com/element_origin_min_pic/17/05/16/46dd04adc424abb7cd8c4bca9d0980e4.jpg", getString(R.string.Text2QRcode)))
    }


}