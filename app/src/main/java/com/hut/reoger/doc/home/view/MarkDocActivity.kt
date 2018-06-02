package com.hut.reoger.doc.home.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.home.adapter.MarkAdapter
import com.hut.reoger.doc.home.presenter.MarkPresentrtImpl
import com.hut.reoger.doc.search.adapter.ItemClickSupport
import kotlinx.android.synthetic.main.activity_mark.*

/**
 * Created by reoger on 2018/4/15.
 * 收藏列表界面
 */
class MarkDocActivity : BaseActivity(), IMarkDocView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark)
    }

    override fun setActionBar() {
        setActivityTitle(getString(R.string.fragment_marks))
    }

    override fun initView() {
        val markAdapter = MarkAdapter(this)
        val impl = MarkPresentrtImpl(this, this)


        val data = impl.readMarksFromDb()

        if (data != null && data.isNotEmpty()) {
            recycler_mark.visibility = View.VISIBLE
            tv_mark_none.visibility = View.GONE
            recycler_mark.adapter = markAdapter
            markAdapter.initData(data)
            var dataSize = data.size
            val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recycler_mark.layoutManager = manager
            markAdapter.setDeleteOnClickListener(object : MarkAdapter.OnDeleteClickListener {
                override fun onClick(pos: Int) {
                    if (impl.deleteMarkById(data[pos].doc_id)) {
                        markAdapter.deleteItem(pos)
                        dataSize--
                        if (dataSize <= 0) {
                            notifyNoData()
                        }
                    }
                }
            })

            ItemClickSupport.addTo(recycler_mark).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    toast("点击了重要的姐面$position")
                }

            })
        } else {
            notifyNoData()
        }


    }

    fun notifyNoData() {
        toast("暂时没有找到任何数据哦~")
        recycler_mark.visibility = View.GONE
        tv_mark_none.visibility = View.VISIBLE
    }

}