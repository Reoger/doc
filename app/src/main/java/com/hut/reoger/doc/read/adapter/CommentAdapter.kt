package com.hut.reoger.doc.read.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.read.bean.DataItem

/**
 * Created by reoger on 2018/4/6.
 * 评论列表的适配器
 */
class CommentAdapter(val mContext:Context) :RecyclerView.Adapter<CommentAdapter.ItemViewHolder>(){

    private var data:List<DataItem>?=null


     fun setData(data: List<DataItem>){
         this.data = data
         notifyDataSetChanged()
     }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
       val v = LayoutInflater.from(mContext).inflate(R.layout.item_comment,parent,false)
        return ItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data?.size ?:0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (data!=null){
            val item = data!![position]
            holder.title?.text = item.commentContent
            holder.author?.text = item.commentUserName
            holder.time?.text = item.commentTime
        }
    }

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var title: TextView?= null
        var time: TextView?= null
        var author: TextView?= null
        init {
            title = itemView?.findViewById(R.id.comment_content)
            time = itemView?.findViewById(R.id.comment_time)
            author = itemView?.findViewById(R.id.comment_user)
        }
    }

}