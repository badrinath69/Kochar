package com.pivotalsoft.kochar.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.CircleImageDataModel
import de.hdodenhof.circleimageview.CircleImageView

class CircleImageAdapter(private val context: Context): RecyclerView.Adapter<CircleImageAdapter.MyCircleImageAdapter>() {

    var dataList = ArrayList<CircleImageDataModel>()
    internal fun setData(dataList: ArrayList<CircleImageDataModel>){
        this.dataList = dataList
        notifyDataSetChanged()
    }
    inner class MyCircleImageAdapter(view: View): RecyclerView.ViewHolder(view){

        val image : CircleImageView = view.findViewById(R.id.avatar_select)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCircleImageAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listitem_circlepic,parent,false)
        return MyCircleImageAdapter(v)
    }

    override fun onBindViewHolder(holder: MyCircleImageAdapter, position: Int) {
        val data = dataList[position]
        holder.image.setImageResource(data.circleimage)
        holder.image.borderColor = Color.WHITE
        holder.image.setOnClickListener {

            holder.image.borderColor = Color.parseColor("#d12531")
            for (i in 0..dataList.size) {
                if (i != position) {
                    notifyItemChanged(i, null)
                }
            }
        }
    }

    override fun getItemCount(): Int  = dataList.size
}