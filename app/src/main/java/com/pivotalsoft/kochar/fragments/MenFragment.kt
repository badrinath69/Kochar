package com.pivotalsoft.kochar.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.activities.AccountDetails
import com.pivotalsoft.kochar.activities.CartActivity
import com.pivotalsoft.kochar.activities.TabSubOneActivity
import com.pivotalsoft.kochar.adapters.MenAdapter
import com.pivotalsoft.kochar.databinding.FragmentMenBinding
import com.pivotalsoft.kochar.modal.MenDataModel


class MenFragment : Fragment() {

   private lateinit var binding: FragmentMenBinding
    private lateinit var userArrayList: ArrayList<MenDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenBinding.inflate(inflater,container,false)
        return binding.root
        binding.listitemmen.setSelector(android.R.color.transparent)
        binding.listitemmen.setDivider(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageId = intArrayOf(
            R.drawable.manpng,R.drawable.menpant,R.drawable.menunderwear,R.drawable.menshoes
        )
        val names = arrayOf(
            "Shirts","Pants","Inner Wear","Shoes"
        )
        userArrayList = ArrayList()

        for (i in names.indices){
            val user = MenDataModel(names[i],imageId[i])
            userArrayList.add(user)
        }
        binding.listitemmen.isClickable = true
        binding.listitemmen.adapter = MenAdapter(requireActivity(),userArrayList)
        binding.listitemmen.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(requireActivity(),TabSubOneActivity::class.java)
            startActivity(i)
//            val nam = names[position]
//            val img = imageId[position]
//            val i = Intent(requireActivity(),AccountDetails::class.java)
//            i.putExtra("name",nam)
//            i.putExtra("img",img)
//            startActivity(i)
//            Toast.makeText(requireActivity(),"you clicked on position $position",
//            Toast.LENGTH_SHORT).show()

        }


    }

}