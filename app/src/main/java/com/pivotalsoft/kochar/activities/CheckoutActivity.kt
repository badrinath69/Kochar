package com.pivotalsoft.kochar.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.databinding.ActivityCheckoutBinding


class CheckoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences: SharedPreferences = getSharedPreferences("Address_Sharedpref", Context.MODE_PRIVATE)
        val nnam = sharedPreferences.getString("add_nam","Add Address")
        val nnnam = sharedPreferences.getString("add_det","")
        binding.txtUserName.text = nnam
        binding.txtAddress.text = nnnam
        val toolbar: Toolbar = findViewById(R.id.toolbarcheckoutactivity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Checkout"
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.backbtwhite)
        binding.txtChangeAddress.setOnClickListener {
            val i = Intent(this,AddAddressActivity::class.java)
            startActivity(i)
        }
        binding.btnContinue.setOnClickListener {
            val i = Intent(this,OrderActivity::class.java)
            startActivity(i)
            finish()
        }
//        backbtcheckoutactivity.setOnClickListener {
//            val i = Intent(this,CartActivity::class.java)
//            startActivity(i)
//            finish()
//        }
//        backbtcheckoutactivity2.setOnClickListener {
//            val i = Intent(this,CartActivity::class.java)
//            startActivity(i)
//            finish()
//        }


    }
}