package com.pivotalsoft.kochar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.CartDataModal
import com.pivotalsoft.kochar.modal.GetCartDetailsDataModel

class CartAdapter(val context: Context): RecyclerView.Adapter<CartAdapter.MyCartAdapter>() {

    var dataList = emptyList<CartDataModal>()
    var dl = ArrayList<GetCartDetailsDataModel>()
    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onCartDelete(position: Int)
        fun plusPressed(position: Int)
        fun minusPressed(position: Int)
    }
    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    internal fun setDataList(dataList: List<CartDataModal>){
        this.dataList = dataList
        notifyDataSetChanged()
    }


    inner class MyCartAdapter(view: View,listner: onItemClickListner): RecyclerView.ViewHolder(view){
        val photo: ImageView = view.findViewById(R.id.thumbnail1)
        val productdes: TextView = view.findViewById(R.id.product_name)
        val productunit: TextView = view.findViewById(R.id.product_unit)
        val productunitt: TextView = view.findViewById(R.id.product_quantity)
        val productPrice: TextView = view.findViewById(R.id.product_Price)
        val producttotalprice: TextView = view.findViewById(R.id.final_product_Price)
        val delproduct: ImageView = view.findViewById(R.id.img_remove)
        val tvplus: TextView = view.findViewById(R.id.product_plus)
        val tvminus: TextView = view.findViewById(R.id.product_minus)

        init {
            delproduct.setOnClickListener {
                listner.onCartDelete(adapterPosition)
                notifyDataSetChanged()
            }
            tvplus.setOnClickListener {
                listner.plusPressed(adapterPosition)
            }
            tvminus.setOnClickListener {
                listner.minusPressed(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listitem_finalcartproduct,parent,false)
        return MyCartAdapter(v,mListner)
    }

    override fun onBindViewHolder(holder: MyCartAdapter, position: Int) {
        val data = dataList[position]
        val img = data.photo
        Glide.with(context).load("https://mobilevakil.com/vasu/kochar/upload/$img").into(holder.photo)


        holder.productdes.text = data.productdes
        holder.productunit.text = data.no_units
        holder.productunitt.text = data.no_units
        holder.productPrice.text = data.actual_price
        holder.producttotalprice.text = data.producttotalprice

//        holder.tvplus.setOnClickListener {
//
//            mListner.plusPressed(dl[position])
//        }
//        holder.tvminus.setOnClickListener {
//            mListner.minusPressed(dl[position])
//
//        }
    }

    override fun getItemCount(): Int = dataList.size
}