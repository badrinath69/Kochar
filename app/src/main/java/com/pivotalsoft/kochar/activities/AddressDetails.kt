package com.pivotalsoft.kochar.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.Utils
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.databinding.ActivityAddAddressBinding
import com.pivotalsoft.kochar.databinding.ActivityAddressDetailsBinding
import com.pivotalsoft.kochar.modal.SharedPrefManager
import com.pivotalsoft.kochar.response.AddAddressResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressDetails : AppCompatActivity() {
    lateinit var binding: ActivityAddressDetailsBinding
    private var userid: String? = null
    private lateinit var preferences: SharedPreferences
    private var username: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)

         userid = preferences.getString("userid",null)!!
         username = preferences.getString("fullname", null)!!
        Toast.makeText(applicationContext,"Username: $username Userid: $userid",Toast.LENGTH_SHORT).show()

        binding.addname.inputType = InputType.TYPE_NULL
        binding.addname.setText(username)
        val toolbar: Toolbar = findViewById(R.id.toolbaraddressdetailsactivity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Address"
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.backbtwhite)
        binding.btaddressdetails.setOnClickListener {



            if(binding.addaddress.text.toString().isEmpty()){
                binding.addaddress.error = "Need Address"
                binding.addaddress.requestFocus()
                return@setOnClickListener
            }
            if(binding.addlandmark.text.toString().isEmpty()){
                binding.addlandmark.error = "Fill The Landmark"
                binding.addlandmark.requestFocus()
                return@setOnClickListener
            }
            if(binding.addcity.text.toString().isEmpty()){
                binding.addcity.error = "Enter Your City Name"
                binding.addcity.requestFocus()
                return@setOnClickListener
            }
            if(binding.addstate.text.toString().isEmpty()){
                binding.addstate.error = "Enter Your City Name"
                binding.addstate.requestFocus()
                return@setOnClickListener
            }
            addAddresses()

        }

    }

    private fun addAddresses() {

        Log.e("AddressDetailsPage","Username: $username Userid: $userid")
        Toast.makeText(applicationContext,"Username: $username Userid: $userid",Toast.LENGTH_SHORT).show()

       val city = RetrofitClient.instance.addAddress("${userid.toString()}",
            "${binding.addaddress.text.toString()}",
        "${binding.addcity.text.toString()}",
        "${binding.addlandmark.text.toString()} ${binding.addcity.text.toString()}",
       "${binding.addstate.text.toString()}")
        val utilsclass: Utils = Utils()
        utilsclass.startLoadingDialog(this)
        GlobalScope.launch(Dispatchers.IO) {
            try {
        city.enqueue(object : Callback<AddAddressResponse>{
            override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                utilsclass.dismissDialog()
                Toast.makeText(applicationContext,"Address Added Successfully",Toast.LENGTH_SHORT).show()
                val i = Intent(this@AddressDetails,CheckoutActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
                finish()


            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                utilsclass.dismissDialog()
                Toast.makeText(applicationContext,"Unable to Add Your Address Once Check Your Internet Connection",Toast.LENGTH_SHORT).show()
            }

        })
            }
            catch (e:Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Cannot Load Data", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }//NavUtils.navigateUpFromSameTask(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this)
        super.onBackPressed()
    }
}