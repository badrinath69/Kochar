package com.pivotalsoft.kochar.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.HomeDataModel

class HomeAdapter(var context: Context): RecyclerView.Adapter<HomeAdapter.MyHomeAdapter>() {

    private lateinit var mListener : onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListener = listner
    }
    var dataList = emptyList<HomeDataModel>()
    internal fun setDataList(dataList : List<HomeDataModel>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    inner class MyHomeAdapter(view: View,listner: onItemClickListner): RecyclerView.ViewHolder(view){
        val homewimageview = view.findViewById<ImageView>(R.id.homewimageview)
        val hometextview = view.findViewById<TextView>(R.id.hometextview)

        init {
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHomeAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_menu_item,parent,false)
        return MyHomeAdapter(v,mListener)
    }

    override fun onBindViewHolder(holder: MyHomeAdapter, position: Int) {

        var data = dataList[position]
        holder.hometextview.text = data.text
        holder.homewimageview.setImageResource(data.image)


    }

    override fun getItemCount() = dataList.size
}