package com.hut.reoger.doc.home.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hut.reoger.doc.App
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.home.presenter.CommentPresenterImpl
import com.hut.reoger.doc.home.presenter.ICommentPresenter
import com.hut.reoger.doc.read.adapter.CommentAdapter
import com.hut.reoger.doc.read.bean.CommentsByDoc
import com.hut.reoger.doc.read.bean.DataItem
import com.hut.reoger.doc.search.adapter.ItemClickSupport
import com.hut.reoger.doc.utils.log.LogUtils

/**
 * Created by reoger on 2018/4/15.
 */

class CommentDocFragment : BaseFragment(), ICommentDocView {


    object InnerClass {
        val instance: CommentDocFragment = CommentDocFragment()
    }

    companion object {
        fun getInstance(): CommentDocFragment {
            return InnerClass.instance
        }

    }

    var presenter: ICommentPresenter? = null
    var markAdapter: CommentAdapter? = null
    var notice_Date: TextView? = null

    var data: List<DataItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CommentPresenterImpl(activity, this)
        presenter?.getMarkListByUsrId(App.instance.token)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_mark_list, container, false)
        initView(view!!)
        return view
    }

    private fun initView(view: View) {
        val recyeler = view.findViewById<RecyclerView>(R.id.recycler_mark_fragment)
        notice_Date = view.findViewById<TextView>(R.id.tv_mark_fragment_none)

        recyeler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        markAdapter = CommentAdapter(activity)
        recyeler.adapter = markAdapter
        markAdapter!!.setRemoveClikcLister(object : CommentAdapter.OnRemoveClickListener {
            override fun onRemoveClickListener(position: Int) {
                presenter?.deleteMarkComment(data!![position].commentId, position)
            }
        })
        ItemClickSupport.addTo(recyeler).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                toast("点击了$position")
            }
        })


    }

    override fun loadCommentSuccessful(data: CommentsByDoc?) {
        if (data == null) {
            notifyNoData()
        } else {
            if (data.data!!.isEmpty()) {
                notifyNoData()
            } else {
                this.data = data.data
                markAdapter?.setData(data = data?.data!!)
            }
        }
    }

    override fun loadCommentFaile(error: String) {
        toast(error)
    }


    override fun deleteCommentSuccessful(position: Int) {
        markAdapter?.deleteData(position)
        if (this.data?.isEmpty() == true) {
            notifyNoData()
        }
    }

    fun notifyNoData() {
        notice_Date?.visibility = View.GONE
    }
}