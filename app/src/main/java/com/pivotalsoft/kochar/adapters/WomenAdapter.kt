package com.pivotalsoft.kochar.adapters

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.MenDataModel
import com.pivotalsoft.kochar.modal.WomenDataModel

class WomenAdapter(private val context: Activity, private val arrayList: ArrayList< WomenDataModel>) :
    ArrayAdapter<WomenDataModel>(context, R.layout.list_item_listview2,arrayList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_listview2,null)

        val txt : TextView = view.findViewById(R.id.textlistview2)
        val img : ImageView = view.findViewById(R.id.imagelistview2)
        txt.text = arrayList[position].text3
        img.setImageResource(arrayList[position].image3)

        when(position){
            0 -> view.setBackgroundResource(R.drawable.orbitgradient)
            //  view.setBackgroundColor(Color.parseColor("#f7f7f7"))
            1 -> view.setBackgroundResource(R.drawable.orangegradient)
            2 -> view.setBackgroundResource(R.drawable.excoticgradient)
            3 -> view.setBackgroundResource(R.drawable.toxicgradient)
        }



        return view
    }
}