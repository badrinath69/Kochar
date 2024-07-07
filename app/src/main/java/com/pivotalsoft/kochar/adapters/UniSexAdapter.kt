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
import com.pivotalsoft.kochar.modal.UniSexDataModel
import com.pivotalsoft.kochar.modal.WomenDataModel

class UniSexAdapter(private val context: Activity, private val arrayList: ArrayList<UniSexDataModel>) :
    ArrayAdapter<UniSexDataModel>(context, R.layout.list_item_listview2,arrayList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_listview3,null)

        val txt : TextView = view.findViewById(R.id.textlistview3)
        val img : ImageView = view.findViewById(R.id.imagelistview3)
        txt.text = arrayList[position].text4
        img.setImageResource(arrayList[position].image4)

        when(position){
            0 -> view.setBackgroundResource(R.drawable.toxicgradient)
            //  view.setBackgroundColor(Color.parseColor("#f7f7f7"))
            1 -> view.setBackgroundResource(R.drawable.excoticgradient)
            2 -> view.setBackgroundResource(R.drawable.orangegradient)
            3 -> view.setBackgroundResource(R.drawable.orbitgradient)
        }


        return view
    }
}