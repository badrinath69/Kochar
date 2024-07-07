package com.pivotalsoft.kochar.adapters

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.MenDataModel

class MenAdapter(private val context: Activity,private val dataList: ArrayList<MenDataModel>) :
                     ArrayAdapter<MenDataModel>(context, R.layout.list_item_listview,dataList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_listview,null)

        val txt : TextView = view.findViewById(R.id.textlistview)
        val img : ImageView = view.findViewById(R.id.imagelistview)
        txt.text = dataList[position].text2
        img.setImageResource(dataList[position].image2)

       when(position){
           0 -> view.setBackgroundResource(R.drawable.excoticgradient)
           //  view.setBackgroundColor(Color.parseColor("#f7f7f7"))
           1 -> view.setBackgroundResource(R.drawable.toxicgradient)
           2 -> view.setBackgroundResource(R.drawable.orbitgradient)
           3 -> view.setBackgroundResource(R.drawable.orangegradient)
       }


        return view
    }
}