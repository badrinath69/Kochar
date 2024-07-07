package com.pivotalsoft.kochar.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.Utils
import com.pivotalsoft.kochar.Utilities.snackbar
import com.pivotalsoft.kochar.adapters.CartAdapter
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.databinding.ActivityCartBinding
import com.pivotalsoft.kochar.modal.*
import com.pivotalsoft.kochar.response.DeleteCartResponse
import com.pivotalsoft.kochar.response.GetCartDetailsResponse
import com.pivotalsoft.kochar.response.updateCartItemsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    val productArrayList: ArrayList<CartDataModal> = arrayListOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private var dataList = mutableListOf<CartDataModal>()
    private var userid: String? = null
    private var productdatalist:ArrayList<GetCartDetailsDataModel> = arrayListOf()
    private lateinit var preferences: SharedPreferences
    private var cartList: ArrayList<String> = arrayListOf()
    private var actualList: ArrayList<String> = arrayListOf()
    private var finalPList: ArrayList<String> = arrayListOf()
    private var addonList: ArrayList<String> = arrayListOf()
    private var nounits: ArrayList<String> = arrayListOf()
    private var cartitemid: String? = null
    var minimumOrder = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        userid = preferences.getString("userid",null)!!
        getCartDetails()
//        backbtcartactivity.setOnClickListener {
//            val i = Intent(this,TabSubTwoActivity::class.java)
//            startActivity(i)
//            finish()
//        }
//        backbtcartactivity2.setOnClickListener {
//            val i = Intent(this,TabSubTwoActivity::class.java)
//            startActivity(i)
//            finish()
//        }
        val toolbar : Toolbar =  findViewById(R.id.toolbarcartactivity)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Final Cart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.backbtwhite)

        binding.btnCheckOut.setOnClickListener {
            val i = Intent(this,CheckoutActivity::class.java)
            //i.putExtras(intent)
            startActivity(i)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                finishAfterTransition()
//            }
        }
        recyclerView = findViewById(R.id.cartrecyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(this)
        recyclerView.adapter = cartAdapter
//        dataList.add(CartDataModal(R.drawable.manbg2,"Active Uniform For men Solid shirt For Men Nice Shirts",
//        "Quantity : 1","₹399","Total : ₹399"))
//        dataList.add(CartDataModal(R.drawable.manbg2,"Active Uniform For men Solid shirt For Men Nice Shirts",
//            "Quantity : 2","₹299","Total : ₹398"))
//        dataList.add(CartDataModal(R.drawable.manbg2,"Active Uniform For men Solid shirt For Men Nice Shirts",
//            "Quantity : 3","₹199","Total : ₹597"))


        cartAdapter.setDataList(dataList)
        cartAdapter.setOnItemClickListner(object : CartAdapter.onItemClickListner{
            override fun onCartDelete(position: Int) {
                Log.d("CartActivity","selected : $position")
                Log.d("CartActivity_id","${cartList[position]}")
                Log.d("CartActivityUID","$userid")
                cartitemid = cartList[position]
                deleteCartItem()

                //cartAdapter.notifyDataSetChanged()
                cartAdapter.notifyItemRemoved(position)



            }

            @SuppressLint("LongLogTag")
            override fun plusPressed(position: Int) {

                Log.d("PLUS PRESSED POSITION","POSITION: $position ")
                Log.d("PLUS PRESSED CARTITEM_ID","CARTITEMID: ${cartList[position]} ")
                Log.d("PLUS PRESSED INITIAL_PRICE","INITIAL_PRICE: ${actualList[position]} ")
                Log.d("PLUS PRESSED NO_UNITS","NO_UNITS: ${nounits[position]} ")
                Log.d("PLUS PRESSED FINAL_PRICE","FINAL_PRICE: ${finalPList[position]} ")
                Log.d("PLUS PRESSED ADDEDON","ADDEDON: ${addonList[position]} ")
                val quantity: Int = (nounits[position]).toInt() + 1
                val itemTotal : Int = (actualList[position]).toInt() * quantity
                Log.d("PLUS PRESSED NO_UNITS_INCREASEDTO","NO_UNITS_INCREASEDTO: $quantity ")
                Log.d("PLUS PRESSED ITEM_TOTAL","ITEM_TOTAL: $itemTotal ")
                Log.d("PLUS PRESSED GAP","**************************************************")

                updateQuantity(cartList[position],actualList[position],
                    quantity.toString(),itemTotal.toString(),addonList[position])
                cartAdapter.notifyDataSetChanged()
                calculateCartTotal()

//                val i: Int = productdatalist.indexOf(cartItemsBean)
//                val quantity: Int = cartItemsBean.no_units!!.toInt() + 1
//                val itemTotal: Int = (cartItemsBean.actual_price)!!.toInt() * quantity
//                Log.d("TAG", "onPlusClick: $quantity")
//                val updatedProduct = GetCartDetailsDataModel(
//                    cartItemsBean.cartitemid,
//                    cartItemsBean.product_id,
//                    quantity.toString(),
//                    cartItemsBean.user_id,
//                    cartItemsBean.added_on,
//                    itemTotal.toString(),
//                    cartItemsBean.actual_price,
//                    cartItemsBean.productcode,
//                    cartItemsBean.title,
//                    cartItemsBean.description,
//                    cartItemsBean.is_available,
//                    cartItemsBean.subcatid,
//                    cartItemsBean.sxcode,
//                    cartItemsBean.schoolcode
//
//                )
//                productdatalist.remove(cartItemsBean)
//                productdatalist.add(i, updatedProduct)
//                Log.e("QUNATITY", "" + updatedProduct.no_units)
//                updateQuantity(updatedProduct)
//                cartAdapter.notifyDataSetChanged()
//                calculateCartTotal()

            }

            @SuppressLint("LongLogTag")
            override fun minusPressed(position: Int) {

                Log.d("PLUS PRESSED POSITION","POSITION: $position ")
                Log.d("PLUS PRESSED CARTITEM_ID","CARTITEMID: ${cartList[position]} ")
                Log.d("PLUS PRESSED INITIAL_PRICE","INITIAL_PRICE: ${actualList[position]} ")
                Log.d("PLUS PRESSED NO_UNITS","NO_UNITS: ${nounits[position]} ")
                Log.d("PLUS PRESSED FINAL_PRICE","FINAL_PRICE: ${finalPList[position]} ")
                Log.d("PLUS PRESSED ADDEDON","ADDEDON: ${addonList[position]} ")
                if (nounits[position].toInt() > 1){
                    val quantity: Int = (nounits[position]).toInt() - 1
                    val itemTotal : Int = (actualList[position]).toInt() * quantity
                    Log.d("PLUS PRESSED NO_UNITS_DECREASEDTO","NO_UNITS_DECREASEDTO: $quantity ")
                    Log.d("PLUS PRESSED ITEM_TOTAL","ITEM_TOTAL: $itemTotal ")
                    Log.d("PLUS PRESSED GAP","**************************************************")
                    updateQuantity(cartList[position],actualList[position],
                        quantity.toString(),itemTotal.toString(),addonList[position])
                    cartAdapter.notifyDataSetChanged()
                    calculateCartTotal()

                }


//                val i: Int = productdatalist.indexOf(cartItemsBean)
//                if (cartItemsBean.no_units!! > 1.toString()) {
//                    val quantity: Int = cartItemsBean.no_units.toInt() - 1
//                    val itemTotal: Int = ((cartItemsBean.actual_price)!!.toInt() * quantity)
//                    Log.d("TAG", "onMinusClick: $quantity")
//                    val updatedProduct = GetCartDetailsDataModel(
//                        cartItemsBean.cartitemid,
//                        cartItemsBean.product_id,
//                        quantity.toString(),
//                        cartItemsBean.user_id,
//                        cartItemsBean.added_on,
//                        itemTotal.toString(),
//                        cartItemsBean.actual_price,
//                        cartItemsBean.productcode,
//                        cartItemsBean.title,
//                        cartItemsBean.description,
//                        cartItemsBean.is_available,
//                        cartItemsBean.subcatid,
//                        cartItemsBean.sxcode,
//                        cartItemsBean.schoolcode
//
//                    )
//                    productdatalist.remove(cartItemsBean)
//                    productdatalist.add(i, updatedProduct)
//                    updateQuantity(updatedProduct)
//                    cartAdapter.notifyDataSetChanged()
//                    calculateCartTotal()
//                }
            }



        })
    }

    private fun updateQuantity(cartitemid: String,actualprice: String,no_units: String,total_price:String,added_on:String) {


        RetrofitClient.instance.updateCartItems("$cartitemid",
        "$actualprice","$no_units",
        "$total_price","$added_on")
            .enqueue(object :Callback<updateCartItemsResponse>{
                override fun onResponse(call: Call<updateCartItemsResponse>, response: Response<updateCartItemsResponse>) {
                    if (response.isSuccessful){

                        Toast.makeText(applicationContext,"added or decresed",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<updateCartItemsResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,"check your internet connection",Toast.LENGTH_SHORT).show()

                }

            })

    }

    private fun calculateCartTotal() {
//        val productArrayList: java.util.ArrayList<CartDataModal> = arrayListOf()
        var grandTotal = 0
        for (order in productdatalist) {
            grandTotal += (order.total_price)?.toInt() ?: 0 //* order.no_units
            val userid = order.user_id
            Log.d("PLUS_TOTAL_GRAND", "calculateCartTotal: $grandTotal")
        }
        Log.d("PLUS_TOTAL", "" + productdatalist.size + "Items | " + "  DISCOUNT : " + grandTotal)
        binding.txtSubTotal.text = productdatalist.size.toString() + " Items | Rs " + grandTotal
        if (grandTotal >= minimumOrder) {
            binding.btnCheckOut.visibility = View.VISIBLE
            binding.txtAlert.visibility = View.GONE
        } else {
            binding.btnCheckOut.setVisibility(View.INVISIBLE)
            binding.txtAlert.visibility = View.VISIBLE
            binding.txtAlert.text = "Minimum Order Must Be Greater Than Rs.$minimumOrder*"
        }
        val finalGrandTotal = grandTotal
        Log.d("PLUS_TOTAL_GRAND_2", "calculateCartTotal2: $finalGrandTotal")
        binding.btnCheckOut.setOnClickListener(View.OnClickListener { //
            val intent = Intent(this@CartActivity, CheckoutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//            intent.putExtra("Checkout", true)
//            intent.putExtra("grandTotal", finalGrandTotal)
//            intent.putExtra("storeId", storeId)
//            intent.putExtra("storeName", storeName)
//            intent.putExtra("storeFcmKey", storeFcmKey)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        })
        if (productdatalist.size == 0) {
            setContentView(R.layout.activity_emptycart)
            val btnGotohome = findViewById<View>(R.id.btnGotohome) as Button
            btnGotohome.setOnClickListener {
                val intent = Intent(this@CartActivity, OrderActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                intent.putExtra("storeId", storeId)
//                intent.putExtra("storeName", storeName)
//                intent.putExtra("storeFcmKey", storeFcmKey)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }
    }

    private fun deleteCartItem() {
        val utils:Utils = Utils()
        utils.startLoadingDialog(this)
        RetrofitClient.instance.deleteCartItems("$cartitemid","$userid")
            .enqueue(object : Callback<DeleteCartResponse>{
                override fun onResponse(call: Call<DeleteCartResponse>, response: Response<DeleteCartResponse>) {
                    if (response.body()?.status.equals("success")){

                        utils.dismissDialog()
                        binding.nsv5.snackbar("Address Deleted")
                        cartAdapter.notifyDataSetChanged()

                    }
                }

                override fun onFailure(call: Call<DeleteCartResponse>, t: Throwable) {
                    utils.dismissDialog()
                    binding.nsv5.snackbar("Try Again")
                }

            })
    }

    private fun getCartDetails() {
        val utilsclass: Utils = Utils()
        utilsclass.startLoadingDialog(this)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                dataList.clear()

                RetrofitClient.instance.getCartDetails("$userid")
                    .enqueue(object : Callback<GetCartDetailsResponse>{
                        override fun onResponse(call: Call<GetCartDetailsResponse>, response: Response<GetCartDetailsResponse>) {
                            val det = response.body()
                            if (response.isSuccessful){
                                utilsclass.dismissDialog()
                               // binding.nsv2.snackbar("DATA Getted")
                                if (det != null) {
                                    for (i in det.cartlistdata.indices){
                                        val items = GetCartDetailsDataModel(
                                            det.cartlistdata[i].cartitemid,det.cartlistdata[i].product_id,
                                            det.cartlistdata[i].no_units,det.cartlistdata[i].user_id,
                                            det.cartlistdata[i].added_on,det.cartlistdata[i].total_price,
                                            det.cartlistdata[i].actual_price,
                                            det.cartlistdata[i].productcode,det.cartlistdata[i].title,
                                            det.cartlistdata[i].description,det.cartlistdata[i].is_available,
                                            det.cartlistdata[i].subcatid,det.cartlistdata[i].sxcode,
                                            det.cartlistdata[i].schoolcode
                                        )
                                        productdatalist.addAll(listOf(items))
                                       val imagesdet = "img23.jpg"
                                        val titledet = productdatalist[i].title
                                        val desdet = productdatalist[i].description
                                        val modeldet = productdatalist[i].productcode
                                        val finalprice = productdatalist[i].total_price
                                        val no_units = productdatalist[i].no_units
                                        val userid = productdatalist[i].user_id
                                        val product_id = productdatalist[i].product_id
                                        val cartitemid = productdatalist[i].cartitemid
                                        val addedon = productdatalist[i].added_on
                                        val actual_price = productdatalist[i].actual_price
                                       //5 val quantity = productdatalist[i].discount_price
                                        //  Toast.makeText(applicationContext,"$imagesdet",Toast.LENGTH_SHORT).show()

                                        val items2 = CartDataModal("$imagesdet",
                                            "$titledet","$actual_price",
                                            "$finalprice","$userid",
                                            "$no_units","$product_id",
                                            "$cartitemid","$addedon")
                                        val cartIds = "${productdatalist[i].cartitemid}"

                                        cartList.add(cartIds)
                                        actual_price?.let { actualList.add(it) }
                                        finalprice?.let { finalPList.add(it) }
                                        if (addedon != null) {
                                            addonList.add(addedon)
                                        }
                                        no_units?.let { nounits.add(it) }

                                        dataList.addAll(listOf(items2))
                                        cartAdapter.notifyDataSetChanged()
                                }
                            }}else{
                                utilsclass.dismissDialog()
                            }

                        }


                        override fun onFailure(call: Call<GetCartDetailsResponse>, t: Throwable) {
                            utilsclass.dismissDialog()
                            Toast.makeText(applicationContext,"Please Check The Internet Connection",
                                Toast.LENGTH_SHORT).show()
                        }
                    })





            }
            catch (e:Exception) { withContext(Dispatchers.Main) {

                Toast.makeText(this@CartActivity, "Cannot Load Data", Toast.LENGTH_LONG).show()

            }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
//            android.R.id.home -> {
//                Toast.makeText(applicationContext,"hello",Toast.LENGTH_SHORT).show()
//                NavUtils.navigateUpFromSameTask(this)
////
////                val i = Intent(this,TabSubOneActivity::class.java)
////                i.putExtras(intent)
////                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
////                startActivity(i)
////                finish()
//
//            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val i = Intent(this,TabSubTwoActivity::class.java)
        i.putExtras(intent)
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

}