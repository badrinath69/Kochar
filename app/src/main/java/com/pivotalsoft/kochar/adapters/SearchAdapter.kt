package com.pivotalsoft.kochar.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.userdetails.SchoolData

class SearchAdapter(private val context: Activity,
                    @LayoutRes private val layoutResource: Int,
                    private var dataList: ArrayList<SchoolData>):
    ArrayAdapter<SchoolData>(context,0,dataList){
    private var mdataList: ArrayList<SchoolData> = dataList
//    override fun getFilter(): Filter {
//        return countryFilter
//    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as TextView
        view.text = "${mdataList[position].fullname}"
        return view

//       // var convertView = convertView
//        //if (convertView == null)
//            val v = LayoutInflater.from(parent.context).inflate(R.layout.dropdown_itemlist,parent,false)
//            val searchdet1 = v.findViewById<TextView>(R.id.searchdet1)
//           // val searchdet2 = v.findViewById<TextView>(R.id.searchdet2)
//            var data = dataList[position]
//            searchdet1.text = data.fullname
//           // searchdet2.text = "${data.fullname} , "+"${data.address} , "+
//                  //  "${data.city}"
//            //            searchdet1.text = dataList[position].fullname
////
////            searchdet2.text = "${data.fullname} , "+"${data.address} , "+
////                    "${data.city}"  return v






    }

    override fun getCount(): Int {
        return mdataList.size
    }

    override fun getItem(p0: Int): SchoolData? {
        return mdataList[p0]
    }

    override fun getItemId(p0: Int): Long {
        // Or just return p0
        return mdataList[p0].schoolid.toLong()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                mdataList = filterResults.values as ArrayList<SchoolData>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    dataList
                else
                    dataList.filter {
                        it.fullname.lowercase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

}











































//private val countryFilter: Filter = object : Filter(){
//    override fun performFiltering(constraint: CharSequence?): FilterResults {
//        val results = FilterResults()
//        val suggestions: ArrayList<SchoolData> = arrayListOf()
//        if (constraint == null || constraint.isEmpty()) {
//            suggestions.addAll(dataList)
//        } else{
//            val filterPattern = constraint.toString()
//            for (item in dataList) {
//                if (item.fullname.contains(filterPattern)) {
//                    suggestions.add(item)
//                }
//            }
//        }
//        results.values = suggestions
//        results.count = suggestions.size
//        return results
//
//    }
//
//    override fun publishResults(constraint: CharSequence, results: FilterResults) {
//        //clear()
//        mdataList = results.values as ArrayList<SchoolData>
//        notifyDataSetChanged()
//    }
//
//    override fun convertResultToString(resultValue: Any?): CharSequence {
//        return (resultValue as SchoolData).fullname
//    }
//
//}