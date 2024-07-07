package com.pivotalsoft.kochar.activities

import android.R.array
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.ColorFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.Utils
import com.pivotalsoft.kochar.Utilities.snackbar
import com.pivotalsoft.kochar.adapters.CircleImageAdapter
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.badge.NotificationCountSetClass
import com.pivotalsoft.kochar.databinding.ActivityTabSubTwoBinding
import com.pivotalsoft.kochar.modal.CircleImageDataModel
import com.pivotalsoft.kochar.modal.SharedPrefManager
import com.pivotalsoft.kochar.response.AddToCartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TabSubTwoActivity : AppCompatActivity() {

   private lateinit var binding: ActivityTabSubTwoBinding
//    lateinit var sharedPreferences: SharedPreferences
//    lateinit var sharedPreferences2: SharedPreferences

    var picurl: String? = null
    var titdes: String? = null
    var procode: String? = null
    var price: String? = null
    var proid: String? = null
    var notificationCountCart = 0
    private var userid: String? = null
    private lateinit var preferences: SharedPreferences

    private var imgstr :Array<String> = arrayOf()
    private var imageSlider: ImageSlider? = null
    private lateinit var recyclerView: RecyclerView
    private var imageList: ArrayList<SlideModel> = arrayListOf()
    private lateinit var circleImageAdapter: CircleImageAdapter
    private val dataList: ArrayList<CircleImageDataModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabSubTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbartabsubtwoactivity)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Product Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.backbtwhite)
        preferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        userid = preferences.getString("userid",null)!!


//        sharedPreferences2 = getSharedPreferences("SHARED_PREF2",Context.MODE_PRIVATE)
//        sharedPreferences = getSharedPreferences("SHARED_PREF",Context.MODE_PRIVATE)
//        val value = sharedPreferences.getInt("val",0)


       // Toast.makeText(applicationContext,"${value}",Toast.LENGTH_SHORT).show()
        // notificationCountCart = notifvalue!!.toInt()


         picurl = intent.getStringExtra("pro_pic")
         titdes = intent.getStringExtra("pro_titledes")
         procode = intent.getStringExtra("pro_productcode")
         price = intent.getStringExtra("pro_price")
         proid = intent.getStringExtra("pro_productid")


         imgstr = arrayOf(
            "https://mobilevakil.com/vasu/kochar/upload/$picurl"
        )

         imageSlider = findViewById<ImageSlider>(R.id.tsbsubtwoimageslider)
         imageList = ArrayList<SlideModel>()
        setupalluis()


//        imageList.add(SlideModel("https://image.freepik.com/free-photo/waist-up-portrait-handsome-serious-unshaven-male-keeps-hands-together-dressed-dark-blue-shirt-has-talk-with-interlocutor-stands-against-white-wall-self-confident-man-freelancer_273609-16320.jpg"))
//        imageList.add(SlideModel("https://image.freepik.com/free-photo/waist-up-portrait-handsome-serious-unshaven-male-keeps-hands-together-dressed-dark-blue-shirt-has-talk-with-interlocutor-stands-against-white-wall-self-confident-man-freelancer_273609-16320.jpg"))



      //  notificationCountCart = value


        binding.cartaddtocartbt.setOnClickListener {


            addToCart()

            Log.d("TabTwoActivity","userid: $userid")
            notificationCountCart++
            NotificationCountSetClass.setNotifyCount(notificationCountCart)
            invalidateOptionsMenu()
            binding.cartaddtocartbt.isClickable = false
//            val cartvalue = notificationCountCart
//            val editor: SharedPreferences.Editor = sharedPreferences2.edit()
//            editor.putInt("val2",cartvalue)
//            editor.apply()

        }


//        recyclerView = findViewById(R.id.recyclerViewcircleimage)
//        circleImageAdapter = CircleImageAdapter(this)
//        recyclerView.adapter =  circleImageAdapter
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true)
//        dataList.add(CircleImageDataModel(R.color.white))
//        dataList.add(CircleImageDataModel(R.color.kocharcolor))
//        dataList.add(CircleImageDataModel(R.color.black))
//        dataList.add(CircleImageDataModel(R.color.colorGreen))
//
//        circleImageAdapter.setData(dataList)

    }

    private fun setupalluis() {
        for (element in imgstr){
            imageList.add(SlideModel("$element"))
        }
        binding.tabsubtwotitle.text = titdes
        binding.tabsubtwoprocode.text = procode
        binding.tabsubtwoprice.text = "₹ $price"
        imageSlider?.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun addToCart() {
        val utilsclass: Utils = Utils()
        utilsclass.startLoadingDialog(this)
        val call = RetrofitClient.instance.addToCart("$proid","1",
            "$userid","2021-10-05 05:46:00",
        "$price","$price")
        GlobalScope.launch(Dispatchers.Main) {
            try {
                call.enqueue(object : Callback<AddToCartResponse>{
                    override fun onResponse(call: Call<AddToCartResponse>, response: Response<AddToCartResponse>) {
                        val det = response.body()

                            if (det?.message == "success"){

                                utilsclass.dismissDialog()
                                binding.nsv3.snackbar("Added To Cart")

                            }else{
                                utilsclass.dismissDialog()
                                binding.nsv3.snackbar("Already Added to cart")
                            }

                    }

                    override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                        utilsclass.dismissDialog()
                        Toast.makeText(applicationContext,"Please Check The Internet Connection",Toast.LENGTH_SHORT).show()

                    }

                })



            }
            catch (e:Exception) { withContext(Dispatchers.Main) {

                Toast.makeText(this@TabSubTwoActivity, "Cannot Load Data", Toast.LENGTH_LONG).show()

            }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu2,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        val item = menu?.findItem(R.id.action_cart)

        if (item != null) {
            NotificationCountSetClass.setAddToCart(this@TabSubTwoActivity, item, notificationCountCart!!)
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
                onBackPressed()
            }
            R.id.action_cart -> {
                val i = Intent(this,CartActivity::class.java)
                i.putExtras(intent)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)

                //f


            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        val i = Intent(this,TabSubOneActivity::class.java)
        i.putExtras(intent)
       // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)



      //  super.onBackPressed()
    }

    override fun onPause() {
        setupalluis()
        super.onPause()
    }

}
