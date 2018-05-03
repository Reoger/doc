package com.hut.reoger.doc.read.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.App
import com.hut.reoger.doc.R
import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.read.adapter.CommentAdapter
import com.hut.reoger.doc.read.bean.CommentsByDoc
import com.hut.reoger.doc.read.bean.DataItem
import com.hut.reoger.doc.read.view.DocumentReaderActivity.Companion.COMMENT_LIST_DATA
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.netWork.ApiResponse
import com.hut.reoger.doc.utils.netWork.NetworkScheduler

/**
 * Created by reoger on 2018/4/6.
 * 加载评论列表
 */

class CommentListFragment : BaseFragment(){


    private  var adapter :CommentAdapter?=null

    private var  data:CommentsByDoc?=null

    private var list :MutableList<DataItem>?=null

    //单利模式
    companion object {
        fun getInstance():CommentListFragment{
            return Inner.instance
        }
    }

    private object Inner{
          val instance = CommentListFragment()
    }

    override fun onStart() {
        super.onStart()
        if (isAdded){
             data= arguments.getSerializable(COMMENT_LIST_DATA) as CommentsByDoc?
            LogUtils.d("数据${data.toString()}")
            if (data != null && data?.data?.isEmpty() == false){
                adapter?.setData(data = data?.data!!)
                this.list= data?.data as MutableList<DataItem>?
            }else{
                LogUtils.d("文件为空")
            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_comment
    }



    override fun initView(v: View) {
        val recycler: RecyclerView? = v?.findViewById(R.id.recycler_comments)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recycler?.layoutManager = manager
        adapter = CommentAdapter(activity)
        recycler?.adapter = adapter
        adapter!!.setRemoveClikcLister(object :CommentAdapter.OnRemoveClickListener{
            override fun onRemoveClickListener(position:Int) {
                LogUtils.d("这里是点击事件$position")
                //调用activity中的方法，这里该怎么实现。。
//                data?.data?.get(position)?.commentDocId?.let {
//                    (activity as DocumentReaderActivity).deleteCommentByDoc(it,position)
//                }
                data?.data?.get(position)?.commentId?.let {
                    deleteComment(it,position)
                }
            }
        })
    }

    fun deleteCommentSuccessful(pos:Int){

        list?.removeAt(pos)
        if (list != null && list?.isNotEmpty()==true)
             adapter?.updataData(list!!,pos)
        else
            LogUtils.d("暂时没有更多的数据")
        LogUtils.d("通知更新$pos")

    }

    fun deleteComment(comment_id:Int,pos:Int){
        ApiClient.instance.service.deleteComment(App.instance.userInfo!!.token,comment_id)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<ServiceReply>(activity) {
                    override fun success(data: ServiceReply) {
                        //评论添加成功
                        LogUtils.d("评论0删除成功")
                        deleteCommentSuccessful(pos)
                    }
                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        LogUtils.d("评论删除fail $apiErrorModel")
                    }
                })
    }


}