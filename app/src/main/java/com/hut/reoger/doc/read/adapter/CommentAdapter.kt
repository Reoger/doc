package com.hut.reoger.doc.read.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.hut.reoger.doc.App
import com.hut.reoger.doc.R
import com.hut.reoger.doc.read.bean.DataItem

/**
 * Created by reoger on 2018/4/6.
 * 评论列表的适配器
 */
class CommentAdapter(val mContext:Context) :RecyclerView.Adapter<CommentAdapter.ItemViewHolder>(){

    private var data:MutableList<DataItem>?=null

    private var listener:OnRemoveClickListener?=null


     fun setData(data: List<DataItem>){
         this.data = data as MutableList<DataItem>
         notifyDataSetChanged()
     }

    fun updataData(data: List<DataItem>,pos:Int){
        if (data!=null && data.isNotEmpty()){
            this.data = data as MutableList<DataItem>
            notifyItemRemoved(pos)
        }
    }

    fun deleteData(position: Int){
        data?.removeAt(position)
        notifyItemRemoved(position)

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
            if (item.commentUserId== App.instance.userId){
                holder?.delete?.visibility = View.VISIBLE
                holder?.delete?.setOnClickListener({
                    if (this.listener != null)
                        this.listener!!.onRemoveClickListener(position)
                })
            }

        }
    }

    class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
        var title: TextView?= null
        var time: TextView?= null
        var author: TextView?= null
        var delete:ImageButton?=null

        init {
            title = itemView?.findViewById(R.id.comment_content)
            time = itemView?.findViewById(R.id.comment_time)
            author = itemView?.findViewById(R.id.comment_user)
            delete = itemView?.findViewById(R.id.comment_delete)
        }
    }


    interface OnRemoveClickListener{
        fun onRemoveClickListener(position:Int)
    }

     fun setRemoveClikcLister(listener:OnRemoveClickListener){
        this.listener = listener
    }

}