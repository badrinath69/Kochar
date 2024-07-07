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
import com.pivotalsoft.kochar.adapters.WomenAdapter
import com.pivotalsoft.kochar.databinding.FragmentWomenBinding
import com.pivotalsoft.kochar.modal.WomenDataModel

class WomenFragment : Fragment() {

    private lateinit var binding: FragmentWomenBinding
    private lateinit var userArrayList: ArrayList<WomenDataModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWomenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageId = intArrayOf(
            R.drawable.womentop,R.drawable.womenskirt,R.drawable.womenjeans,R.drawable.womenshoes
        )
        val names = arrayOf(
            "Tops","Skirts","Jeans","Heels"
        )
        userArrayList = ArrayList()

        for (i in names.indices){
            val user = WomenDataModel(names[i],imageId[i])
            userArrayList.add(user)
        }
        binding.listitemmen2.isClickable = true
        binding.listitemmen2.adapter = WomenAdapter(requireActivity(),userArrayList)
        binding.listitemmen2.setOnItemClickListener { parent, view, position, id ->
            val i = Intent(requireActivity(), TabSubOneActivity::class.java)
            startActivity(i)
        }

    }

}