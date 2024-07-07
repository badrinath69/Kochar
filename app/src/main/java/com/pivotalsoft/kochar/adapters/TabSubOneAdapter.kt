package com.pivotalsoft.kochar.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.TabSubOneDataModel


class TabSubOneAdapter(private val context: Context): RecyclerView.Adapter<TabSubOneAdapter.MyTabSubOneAdapter>(){
    private lateinit var mListner: onItemClickListner
    interface onItemClickListner{
        fun onItemClick(position: Int)
        fun onAddToCartBt(position: Int)
    }
    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }


    inner class MyTabSubOneAdapter(view: View,listner: onItemClickListner): RecyclerView.ViewHolder(view){

        val img = view.findViewById<ImageView>(R.id.tabsuboneimage)
        val title = view.findViewById<TextView>(R.id.tabsubonetitle)
        val colorsavailable = view.findViewById<TextView>(R.id.tabsubonecolorsavailable)
        val modelnumber = view.findViewById<TextView>(R.id.tabsubonemodelnumber)
        val price = view.findViewById<TextView>(R.id.tabsuboneprice)
        val discountid = view.findViewById<TextView>(R.id.discountid)
   //     val btaddtocart = view.findViewById<AppCompatButton>(R.id.bttabsuboneaddtocart)
        init {
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
//
//            btaddtocart.setOnClickListener {
//                listner.onAddToCartBt(adapterPosition)
//            }
        }


    }

    var dataList = emptyList<TabSubOneDataModel>()
    internal fun setDataList(dataList : List<TabSubOneDataModel>){
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTabSubOneAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_prodct,parent,false)
        return MyTabSubOneAdapter(v,mListner)
    }

    override fun onBindViewHolder(holder: MyTabSubOneAdapter, position: Int) {
        val data = dataList[position]

        val img = data.imagesubone
        Glide.with(context).load("https://mobilevakil.com/vasu/kochar/upload/$img").into(holder.img)


        holder.title.text = data.title
        holder.colorsavailable.text = data.colorsavailable
        holder.modelnumber.text = data.modelname
        holder.price.text = data.price
    }

    override fun getItemCount(): Int  = dataList.size

}