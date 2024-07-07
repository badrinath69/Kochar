package com.pivotalsoft.kochar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.OrderDataModel

class OrderAdapter(private val context: Context): RecyclerView.Adapter<OrderAdapter.MyOrderAdapter>(){

    var dataList = ArrayList<OrderDataModel>()
    internal fun setData(dataList: ArrayList<OrderDataModel>){
        this.dataList = dataList
    }


    inner class MyOrderAdapter(view: View): RecyclerView.ViewHolder(view){
        val invoiceid: TextView = view.findViewById(R.id.txtInvoiceId)
        val paymenttype: TextView = view.findViewById(R.id.txtPaymentStatus)
        val finalbill: TextView = view.findViewById(R.id.txtFinalBill)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listitem_order,parent,false)
        return MyOrderAdapter(v)
    }

    override fun onBindViewHolder(holder: MyOrderAdapter, position: Int) {
        val data = dataList[position]
        holder.invoiceid.text = data.invoiceid
        holder.paymenttype.text = data.paymenttype
        holder.finalbill.text = data.finalbill
    }

    override fun getItemCount(): Int = dataList.size

}