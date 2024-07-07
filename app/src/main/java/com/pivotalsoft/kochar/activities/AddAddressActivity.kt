package com.pivotalsoft.kochar.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.Utils
import com.pivotalsoft.kochar.Utilities.snackbar
import com.pivotalsoft.kochar.adapters.AddressAdapter
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.databinding.ActivityAddAddressBinding
import com.pivotalsoft.kochar.modal.AddressDataModel
import com.pivotalsoft.kochar.modal.GetAddressDataModel
import com.pivotalsoft.kochar.modal.SharedPrefManager
import com.pivotalsoft.kochar.response.DeleteAddressResponse
import com.pivotalsoft.kochar.response.GetAddressResponse
import com.pivotalsoft.kochar.userdetails.SchoolData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.sign


class AddAddressActivity : AppCompatActivity(){
    lateinit var binding: ActivityAddAddressBinding
    private var userid: String? = null
    private lateinit var preferences: SharedPreferences
    private lateinit var addressAdapter: AddressAdapter
    private var mDataList: ArrayList<GetAddressDataModel> = arrayListOf()
    private var dataList : ArrayList<AddressDataModel> = arrayListOf()
    private var username: String? = null
    private var addposition: String? = null
    private var addresslist: ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbaraddaddressactivity)
        setSupportActionBar(toolbar)
        preferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        userid = preferences.getString("userid",null)!!
        username = preferences.getString("fullname", null)!!
        getAddtessDetails()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Select Address"
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.backbtwhite)
        binding.btnAddAddress.setOnClickListener {
            val i = Intent(this,AddressDetails::class.java)
            startActivity(i)
        }
//        dataList.add(AddressDataModel("Badri","jamshadpur,1/3-333","near NAD",
//        "Vizag","Andhra Pradesh"))
//        dataList.add(AddressDataModel("sai","jamshadpur,2/3-333","near Dolakpur",
//            "Kanchi","Arunachal Pradesh"))
//        dataList.add(AddressDataModel("nivas","jamshadpur,3/3-333","near Kasi",
//            "Gopalpuram","Uttar Pradesh"))

        binding.addressRecyclerview.layoutManager = LinearLayoutManager(this)
        addressAdapter = AddressAdapter(this,dataList)
        binding.addressRecyclerview.adapter = addressAdapter
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        addressAdapter.setOnItemClickListner(object : AddressAdapter.onItemClickListner{

            override fun onRadioButtonPressed(position: Int) {
                val data = dataList[position]
                val fullAddress: String ="${data.addressline}"+"${data.landmark}"+"${data.city}"+"${data.state}"



                    Toast.makeText(applicationContext,"you pressed ${position +1}",Toast.LENGTH_SHORT).show()
                 val sharedPreferences: SharedPreferences = getSharedPreferences("Address_Sharedpref", Context.MODE_PRIVATE)

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("add_nam","${data.name}")
                editor.putString("add_det","$fullAddress")
                editor.apply()
                editor.commit()
                val i = Intent(this@AddAddressActivity,CheckoutActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim)
                finish()
            }

            override fun onDeletePresssed(position: Int) {
                Log.d("AddAddressActivity","selected : $position")
                Log.d("AddAddressActivity_id","${addresslist[position]}")
                 addposition = addresslist[position]
                deleteAddress()
            }

//            override fun isButtonChecked(isChecked: Boolean, position: Int) {
//                val sharedPreferences: SharedPreferences = getSharedPreferences("Addressadaptersp",Context.MODE_PRIVATE)
//                var radiobool = sharedPreferences.getBoolean("numbersp",false)
//                val editor = sharedPreferences.edit()
//
//
//               when(position){
//                   0 -> {
//                       if (isChecked == true){
//                           editor?.putBoolean("numbersp",true)
//                       }else{
//                           editor?.putBoolean("numbersp",false)
//                       }
//                       radiobool = isChecked
//                       Toast.makeText(applicationContext,"you pressed ${position +1}",Toast.LENGTH_SHORT).show()
//                       editor?.commit()
//
//                   }
//                   1 ->{
//                       if (isChecked == true){
//                           editor?.putBoolean("numbersp",true)
//                       }else{
//                           editor?.putBoolean("numbersp",false)
//                       }
//                       radiobool = isChecked
//                       Toast.makeText(applicationContext,"you pressed ${position +1}",Toast.LENGTH_SHORT).show()
//                       editor?.commit()
//
//                   }
//                   2 ->{
//                       if (isChecked == true){
//                           editor?.putBoolean("numbersp",true)
//                       }else{
//                           editor?.putBoolean("numbersp",false)
//                       }
//                       radiobool = isChecked
//                       Toast.makeText(applicationContext,"you pressed ${position +1}",Toast.LENGTH_SHORT).show()
//                       editor?.commit()
//
//                   }
//                   else ->{
//                       Toast.makeText(applicationContext,"nothing selected",Toast.LENGTH_SHORT).show()
//                   }
//
//               }
//            }


        })

/////////////////////////////////////////////////////////////////////////////////////////////////



    }


    private fun deleteAddress() {

        val utils:Utils = Utils()
        utils.startLoadingDialog(this)
        RetrofitClient.instance.deleteAddress("$userid","$addposition")
            .enqueue(object : Callback<DeleteAddressResponse>{


                override fun onResponse(call: Call<DeleteAddressResponse>, response: Response<DeleteAddressResponse>) {
                   if (response.body()?.status.equals("success")){

                       utils.dismissDialog()
                       binding.nsv4.snackbar("Address Deleted")
                       addressAdapter.notifyDataSetChanged()
                   }
                }

                override fun onFailure(call: Call<DeleteAddressResponse>, t: Throwable) {
                    utils.dismissDialog()
                    binding.nsv4.snackbar("Try Again")
                }

            })


    }

    private fun getAddtessDetails() {
        Log.e("AddressDetailsPage"," Userid: $userid")
        Toast.makeText(applicationContext," Userid: $userid",Toast.LENGTH_SHORT).show()

        dataList.clear()
        val call = RetrofitClient.instance.getAddress("$userid")
        val utilsclass: Utils = Utils()
        utilsclass.startLoadingDialog(this)
        GlobalScope.launch(Dispatchers.Main) {
            try {

                call.enqueue(object : Callback<GetAddressResponse>{
                    override fun onResponse(call: Call<GetAddressResponse>, response: Response<GetAddressResponse>) {
                        val det = response.body()
                        if (response.isSuccessful){
                            utilsclass.dismissDialog()
                            for (i in det?.address?.indices!!){
                                val items = GetAddressDataModel(
                                    det.address[i].userid, det.address[i].fullname,
                                    det.address[i].mobile, det.address[i].password,
                                    det.address[i].role, det.address[i].status,
                                    det.address[i].schoolcode, det.address[i].address_id,
                                    det.address[i].address, det.address[i].city,
                                    det.address[i].landmark,det.address[i].state
                                )
                                mDataList.addAll(listOf(items))

                                val address = "${mDataList[i].address} , "+"${mDataList[i].city} , "+
                                        "${mDataList[i].landmark} , "+"${mDataList[i].state}"

                                val item2 = AddressDataModel("$username",
                                    "${mDataList[i].address} , ",
                                    "${mDataList[i].landmark} , ",
                                    "${mDataList[i].city} , ",
                                    "${mDataList[i].state}")
                                val addressIds = "${mDataList[i].address_id}"
                                addresslist.add(addressIds)
                                dataList.addAll(listOf(item2))

                                addressAdapter.notifyDataSetChanged()

                            }
                        }else{
                            utilsclass.dismissDialog()
                        }

                    }

                    override fun onFailure(call: Call<GetAddressResponse>, t: Throwable) {
                        utilsclass.dismissDialog()
                        Toast.makeText(applicationContext, "Check The Internet Connection", Toast.LENGTH_LONG).show()
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
            android.R.id.home -> onBackPressed()
                //NavUtils.navigateUpFromSameTask(this)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val i = Intent(this,CheckoutActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
        super.onBackPressed()
    }

    override fun onPause() {
        getAddtessDetails()
        super.onPause()
    }

}