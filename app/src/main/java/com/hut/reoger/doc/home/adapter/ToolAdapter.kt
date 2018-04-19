package com.hut.reoger.doc.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.model.ToolBean

/**
 * Created by reoger on 2018/4/18.
 * hulala
 */

class ToolAdapter(val mContext:Context) :RecyclerView.Adapter<ToolAdapter.ItemViewHold>(){

    private var datas:List<ToolBean> ?=null


    fun initData(datas:List<ToolBean>){
        this.datas = datas
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ToolAdapter.ItemViewHold {
        val v:View = LayoutInflater.from(mContext).inflate(R.layout.item_tools,parent,false)
        return ItemViewHold(v)
    }

    override fun getItemCount(): Int {
        datas?.let { return it.size }
        return 0
    }

    override fun onBindViewHolder(holder: ToolAdapter.ItemViewHold?, position: Int) {
        if (holder!=null && datas!=null){
            val item = datas!![position]
            Glide.with(mContext).load(item.imageUrl).into(holder.mImage)
            holder.mdesc?.text = item.textDesc
        }
    }

    class ItemViewHold(item:View):RecyclerView.ViewHolder(item){
        var mImage :ImageView ?=null
        var mdesc : TextView ?=null
        init {
            mImage = item.findViewById<ImageView>(R.id.item_tool_image)
            mdesc = item.findViewById(R.id.item_tool_text)
        }
    }
}