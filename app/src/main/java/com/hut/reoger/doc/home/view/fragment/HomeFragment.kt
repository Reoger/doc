package com.hut.reoger.doc.home.view.fragment

import android.app.Fragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.presenter.HomeFragmentPresenter
import com.hut.reoger.doc.home.presenter.IHomeFragmentPresenter
import com.hut.reoger.doc.utils.imageUtils.GlideImageLoader
import com.youth.banner.Banner


/**
 * Created by CM on 2018/1/28.
 */

class HomeFragment : Fragment(),  android.widget.SearchView.OnQueryTextListener, IHomeFragmenView {

    var presenter: IHomeFragmentPresenter?= null

    var banner : Banner? = null

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
        return view
    }

    private fun initView(v: View) {
        val list = listOf("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517161511337&di=dcd28bf080ffdd48321d55dca891eb02&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2015%2F209%2F42%2F6H182F668EP9.jpg",
                "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3339282030,2075813709&fm=27&gp=0.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517756281&di=ccf10592d1698516b3af2337333996fd&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.iyaxin.com%2Fcontent%2Fattachement%2Fjpg%2Fsite2%2F20090415%2F0019667873730b5006db22.jpg")
        val a:SearchView = v.findViewById(R.id.fragment_home_search)
        a.setOnQueryTextListener(this)
        banner = v.findViewById(R.id.banner)
        banner?.setImageLoader(GlideImageLoader())
        banner?.setImages(list)
        banner?.start()

        //https://github.com/youth5201314/banner
    }

}