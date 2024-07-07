package com.pivotalsoft.kochar.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.Utils
import com.pivotalsoft.kochar.Utilities.snackbar
import com.pivotalsoft.kochar.adapters.TabSubOneAdapter
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.badge.NotificationCountSetClass
import com.pivotalsoft.kochar.databinding.ActivityTabSubOneBinding
import com.pivotalsoft.kochar.modal.ProductDataModel
import com.pivotalsoft.kochar.modal.TabSubOneDataModel
import com.pivotalsoft.kochar.response.ProductDetailsResponse
import com.pivotalsoft.kochar.userdetails.SchoolData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class TabSubOneActivity : AppCompatActivity() {
//    lateinit var sharedPreferences: SharedPreferences
//    lateinit var sharedPreferences2: SharedPreferences
    var notificationCountCart = 0
    private lateinit var recyclerView: RecyclerView
   // private var userList= mutableListOf<TabSubOneDataModel>()
    lateinit var binding:ActivityTabSubOneBinding

    private var productdatalist:ArrayList<ProductDataModel> = arrayListOf()
    private var dataList: ArrayList<TabSubOneDataModel> = arrayListOf()
    private lateinit var tabSubOneAdapter: TabSubOneAdapter


    init {
       //getDetailsInfo()
    }








    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabSubOneBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        sharedPreferences = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
//        sharedPreferences2 = getSharedPreferences("SHARED_PREF2",Context.MODE_PRIVATE)

      //  val value2 = sharedPreferences2.getInt("val2",0)

        val toolbar: Toolbar = findViewById(R.id.toolbartabsuboneactivity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Collections"
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.backbtwhite))
//        toolbar.setNavigationOnClickListener {
//            finish()
//        }


        getDetailsInfo()


        recyclerView = findViewById(R.id.recyclerViewTabSubOne)
        recyclerView.layoutManager = GridLayoutManager(this,2)
        tabSubOneAdapter = TabSubOneAdapter(this)
        recyclerView.adapter = tabSubOneAdapter


       // notificationCountCart = value2

        tabSubOneAdapter.setOnItemClickListner(object : TabSubOneAdapter.onItemClickListner{

            override fun onAddToCartBt(position: Int) {
                notificationCountCart++
                NotificationCountSetClass.setNotifyCount(notificationCountCart)
                invalidateOptionsMenu()
//                val cartvalue = notificationCountCart
//                val editor: SharedPreferences.Editor = sharedPreferences.edit()
//                editor.putInt("val",cartvalue)
//                editor.apply()
            }
            override fun onItemClick(position: Int) {
                val i = Intent(this@TabSubOneActivity,TabSubTwoActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                i.putExtra("pro_pic",productdatalist[position].picurl)
                i.putExtra("pro_titledes","${productdatalist[position].title} || ${productdatalist[position].description}")
                i.putExtra("pro_productcode","${productdatalist[position].productcode}")
                i.putExtra("pro_productid","${productdatalist[position].productid}")
                i.putExtra("pro_price","${productdatalist[position].actual_price}")

                i.putExtras(intent)
                startActivity(i)



              //  overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_in_left)

               // Toast.makeText(applicationContext,"you clicked ${position + 1}",Toast.LENGTH_SHORT).show()
//               for (i in userList.indices){
//                  val namnes= userList.get(i).title
//                   Toast.makeText(applicationContext,"you clicked $namnes",Toast.LENGTH_SHORT).show()
//               }
            }



        })
//        userList.add(TabSubOneDataModel(R.drawable.manpng,"Active Uniform For men Solid shirt For Men Nice Shirts",
//            "7 Colors Available","NA-8102","₹499"))
//        userList.add(TabSubOneDataModel(R.drawable.manpng,"AActive Uniform For men Solid shirt For Men Nice Shirts",
//            "7 Colors Available","NA-8102","₹499"))
//        userList.add(TabSubOneDataModel(R.drawable.manpng,"AAActive Uniform For men Solid shirt For Men Nice Shirts",
//            "7 Colors Available","NA-8102","₹499"))
//        userList.add(TabSubOneDataModel(R.drawable.manpng,"AAAActive Uniform For men Solid shirt For Men Nice Shirts",
//            "7 Colors Available","NA-8102","₹499"))
//        userList.add(TabSubOneDataModel(R.drawable.manpng,"AAAAActive Uniform For men Solid shirt For Men Nice Shirts",
//            "7 Colors Available","NA-8102","₹499"))
//        userList.add(TabSubOneDataModel(R.drawable.manpng,"AAAAAActive Uniform For men Solid shirt For Men Nice Shirts",
//            "7 Colors Available","NA-8102","₹499"))
//        userList.add(TabSubOneDataModel(R.drawable.manpng,"AAAAAAActive Uniform For men Solid shirt For Men Nice Shirts",
//            "7 Colors Available","NA-8102","₹499"))

        tabSubOneAdapter.setDataList(dataList)

    }

    private fun getDetailsInfo() {
        val utilsclass: Utils = Utils()
        utilsclass.startLoadingDialog(this)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                dataList.clear()
                val schnam = intent.getStringExtra("school_name")
                val clasnam = intent.getIntExtra("class_name",3)
                val gendernam = intent.getIntExtra("gender_name",3)



                RetrofitClient.instance.getProductDetails("${schnam.toString()}",clasnam,gendernam)
                    .enqueue(object : Callback<ProductDetailsResponse> {
                        override fun onResponse(call: Call<ProductDetailsResponse>, response: Response<ProductDetailsResponse>) {
                            val det = response.body()
                            if (response.isSuccessful){
                                utilsclass.dismissDialog()
                                binding.nsv2.snackbar("DATA Getted")
                                if (det != null) {

                                    for (i in det.productdata.indices){
                                        val items = ProductDataModel(
                                            det.productdata[i].productid, det.productdata[i].productcode, det.productdata[i].title,
                                            det.productdata[i].description, det.productdata[i].is_available, det.productdata[i].subcatid,
                                            det.productdata[i].sxcode, det.productdata[i].schoolcode, det.productdata[i].sizeid,
                                            det.productdata[i].size, det.productdata[i].actual_price, det.productdata[i].discount_price,
                                            det.productdata[i].pgid, det.productdata[i].colorurl, det.productdata[i].picurl, det.productdata[i].schoolid,
                                            det.productdata[i].fullname, det.productdata[i].city, det.productdata[i].address,
                                            det.productdata[i].contactname, det.productdata[i].contactno, det.productdata[i].designation
                                        )
                                        productdatalist.addAll(listOf(items))
                                        val imagesdet = productdatalist[i].picurl
                                        val titledet = productdatalist[i].title
                                        val desdet = productdatalist[i].description
                                        val modeldet = productdatalist[i].productcode
                                        val pricedet = productdatalist[i].actual_price
                                        val discountdet = productdatalist[i].discount_price
                                        //  Toast.makeText(applicationContext,"$imagesdet",Toast.LENGTH_SHORT).show()

                                        val items2 = TabSubOneDataModel(imagesdet,
                                            "$titledet","$desdet",
                                            "$modeldet","$pricedet",
                                        )
                                        dataList.addAll(listOf(items2))
                                        tabSubOneAdapter.notifyDataSetChanged()

                                        // Toast.makeText(applicationContext,"$dataList",Toast.LENGTH_SHORT).show()


                                        //schoolNames.addAll(listOf(schoolNamess))

                                        //   Toast.makeText(applicationContext, "name is $schoolNames", Toast.LENGTH_LONG).show()

                                    }


                                }
                            }else{
                                utilsclass.dismissDialog()
                                Toast.makeText(applicationContext,"hello",Toast.LENGTH_SHORT).show()


                            }


                        }

                        override fun onFailure(call: Call<ProductDetailsResponse>, t: Throwable) {

                            utilsclass.dismissDialog()
                            Toast.makeText(applicationContext,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show()


                        }

                    })




            }
            catch (e:Exception) { withContext(Dispatchers.Main) {

                Toast.makeText(this@TabSubOneActivity, "Cannot Load Data", Toast.LENGTH_LONG).show()

            }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        val item = menu?.findItem(R.id.carticon)

        if (item != null) {
            NotificationCountSetClass.setAddToCart(this@TabSubOneActivity, item, notificationCountCart!!)
        }
        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu()

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            android.R.id.home -> {
                //NavUtils.navigateUpFromSameTask(this)
                onBackPressed()
            }
            R.id.carticon -> {
                val i = Intent(this,CartActivity::class.java)
                i.putExtras(intent)
                //i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)



            }
        }
        return super.onOptionsItemSelected(item)
    }
        override fun onBackPressed() {
        val i = Intent(this,FilterProduct::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()

    }
//
//    override fun onPause() {
//        getDetailsInfo()
//        super.onPause()
//    }
//
//    override fun onResume() {
//        getDetailsInfo()
//        super.onResume()
//    }



}