package com.pivotalsoft.kochar.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.activities.CartActivity
import com.pivotalsoft.kochar.activities.TabSubOneActivity
import com.pivotalsoft.kochar.adapters.UniSexAdapter
import com.pivotalsoft.kochar.adapters.WomenAdapter
import com.pivotalsoft.kochar.databinding.FragmentUnisexBinding
import com.pivotalsoft.kochar.modal.UniSexDataModel
import com.pivotalsoft.kochar.modal.WomenDataModel


class UnisexFragment : Fragment() {
    private lateinit var binding: FragmentUnisexBinding
    private lateinit var userArrayList: ArrayList<UniSexDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUnisexBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageId = intArrayOf(
            R.drawable.manpng,R.drawable.womenjeans,R.drawable.menunderwear,R.drawable.womenshoes
        )
        val names = arrayOf(
            "Top Wear","Jeans Wear","Inner Wear","Shoe Wear"
        )
        userArrayList = ArrayList()

        for (i in names.indices){
            val user = UniSexDataModel(names[i],imageId[i])
            userArrayList.add(user)
        }
        binding.listitemmen3.isClickable = true
        binding.listitemmen3.adapter = UniSexAdapter(requireActivity(),userArrayList)
        binding.listitemmen3.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(requireActivity(), TabSubOneActivity::class.java)
            startActivity(i)
        }

    }

}