package com.pivotalsoft.kochar.adapters

import android.app.Activity
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.modal.AddressDataModel


class AddressAdapter(private val context: Activity,private val dataList: ArrayList<AddressDataModel>):
RecyclerView.Adapter<AddressAdapter.MyAddressAdapter>(){


   var  editor: SharedPreferences.Editor? = null
    private var selected: RadioButton? = null
    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onRadioButtonPressed(position: Int)
       // fun isButtonChecked(isChecked : Boolean,position: Int)
        fun onDeletePresssed(position: Int)

    }
    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }



    inner class MyAddressAdapter(view: View,listner: onItemClickListner): RecyclerView.ViewHolder(view){

        val radioselect = view.findViewById<RadioButton>(R.id.radioButton2)
        val name = view.findViewById<TextView>(R.id.txtName)
        val fulladdress : TextView = view.findViewById(R.id.txtAddress)
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val delete:AppCompatButton =view.findViewById(R.id.btnDelete)


        init {
//            val sharedPreferences = context.getSharedPreferences("addressadaptersp",Context.MODE_PRIVATE)
//            var numsp: Int = sharedPreferences.getInt("numsp",3)
//            editor = sharedPreferences.edit()
//            radioselect.isChecked = numsp == 1



            radioGroup.setOnCheckedChangeListener { group, checkedId ->

//                if (checkedId == R.id.radioButton){
//                    editor.putInt("numsp",1)
//                }else{
//                    editor.putInt("numsp",0)
//                }



                radioselect.setOnClickListener {
                    if (selected != null) {
                        selected!!.isChecked = false
                    }
                    radioselect.isChecked = true
                    selected = radioselect
                    listner.onRadioButtonPressed(adapterPosition)
                }
            }
////////////////////////////////////////////////////////////////////////////////////////////
            delete.setOnClickListener {
                listner.onDeletePresssed(adapterPosition)
            }

//                listner.isButtonChecked(radioselect.isChecked,adapterPosition)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAddressAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.listitem_address,parent,false)
        return MyAddressAdapter(v,mListner)
    }

    override fun onBindViewHolder(holder: MyAddressAdapter, position: Int) {
        val data = dataList[position]

        holder.name.text = data.name
        holder.fulladdress.text = "${data.addressline}"+"${data.landmark}"+
        "${data.city}"+"${data.state}"
    }

    override fun getItemCount(): Int = dataList.size
}
//
//class AddressAdapter(private val context: Activity,private val dataList: ArrayList<AddressDataModel>):
//        ArrayAdapter<AddressDataModel>(context,R.layout.listitem_address,dataList){
//    private var selectedIndex = -1
//    private var selected: RadioButton? = null
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
////        var convertView = convertView
////        val result: View
//        val v = LayoutInflater.from(context).inflate(R.layout.listitem_address,null)
//
//
//
//        val radioselect = v.findViewById<RadioButton>(R.id.radioButton)
//        val name = v.findViewById<TextView>(R.id.txtName)
//        val fulladdress : TextView = v.findViewById(R.id.txtAddress)
//
//
//        radioselect.setOnClickListener {
//            if (selected != null) {
//                selected!!.isChecked = false
//            }
//            radioselect.isChecked = true
//            selected = radioselect
//
//        }
//
//
//        name.text = dataList[position].name
//        fulladdress.text =
//
//        return v
//    }
//
//    override fun getCount(): Int = dataList.size
//
//
//
//        }


