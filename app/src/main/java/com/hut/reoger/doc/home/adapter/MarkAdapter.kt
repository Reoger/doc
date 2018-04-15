package com.hut.reoger.doc.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.bean.MarksBean

/**
 * Created by reoger on 2018/4/15.
 */
class MarkAdapter(val context:Context) : RecyclerView.Adapter<MarkAdapter.ViewHolder>() {
    var data:MutableList<MarksBean>?=null
    var listener:OnDeleteClickListener?=null


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v =  LayoutInflater.from(context).inflate(R.layout.item_mark_doc,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        data?.let { return it.size }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        data?.apply {
            val itemViewHolder = this[position]
            holder?.fileName?.text = itemViewHolder.doc_name
            holder?.markTime?.text = itemViewHolder.mark_time.toString()

            holder?.markDel?.setOnClickListener({
                listener?.onClick(position)
            })
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fileName: TextView? = null
        var markTime: TextView? = null
        var markDel: ImageButton? = null

        init {
            fileName = itemView.findViewById(R.id.tv_item_mark_name)
            markTime = itemView.findViewById(R.id.tv_item_mark_time)
            markDel = itemView.findViewById(R.id.btu_item_mark_del)
        }
    }

    fun initData(data:List<MarksBean>){
        this.data = data as MutableList<MarksBean>
        notifyDataSetChanged()
    }

    fun deleteItem(pos:Int){
        this.data?.removeAt(pos)
        notifyItemRemoved(pos)
    }

     fun setDeleteOnClickListener(listener :OnDeleteClickListener){
        this.listener = listener
     }

    interface OnDeleteClickListener {
        fun onClick(pos:Int)
    }
}