package com.pivotalsoft.kochar.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.system.Os.close
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.adapters.HomeAdapter
import com.pivotalsoft.kochar.adapters.OrderAdapter
import com.pivotalsoft.kochar.databinding.ActivityHomeBinding
import com.pivotalsoft.kochar.modal.HomeDataModel
import com.pivotalsoft.kochar.modal.OrderDataModel
import com.pivotalsoft.kochar.modal.SharedPrefManager

import java.lang.Exception

class HomeActivity : AppCompatActivity() {
   // lateinit var toggle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar
  //  lateinit var navigationView: NavigationView
   // lateinit var drawerLayout: DrawerLayout
    lateinit var recyclerView: RecyclerView
    lateinit var orderAdapter: OrderAdapter
    private var doubleBackToExitPressedOnce = false
    //private lateinit var preferences: SharedPreferences
    private val dataList = arrayListOf<OrderDataModel>()
    private var datalist = mutableListOf<HomeDataModel>()

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbarhome)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Orders"
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.backbtwhite)


//        //drawerLayout = findViewById(R.id.drawer)
//        navigationView = findViewById(R.id.nav_one)





        recyclerView = findViewById(R.id.orderidRecycler)
        orderAdapter = OrderAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = orderAdapter
        dataList.add(OrderDataModel("SDC9898340","COD","Rs 899"))
        dataList.add(OrderDataModel("SDC9909022","COD","Rs 399"))

        orderAdapter.setData(dataList)






        try {
            setSupportActionBar(toolbar)
        }catch (e: Exception){
            supportActionBar?.hide()
        }

//
//        toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//      //  supportActionBar?.setHomeAsUpIndicator(R.drawable.homeicon)
//         supportActionBar?.setDisplayHomeAsUpEnabled(false)
//

//
//        navigationView.setNavigationItemSelectedListener {
//            when(it.itemId) {
//
//                R.id.my_profile -> {
//                    // Toast.makeText(applicationContext, "change password", Toast.LENGTH_SHORT).show()
//                    val i = Intent(this, AccountDetails::class.java)
//                    startActivity(i)
//                    drawerLayout.closeDrawer(GravityCompat.START)
//                }
//
//                R.id.my_orders -> {
//                    val i = Intent(this,HomeActivity::class.java)
//                    startActivity(i)
//                    drawerLayout.closeDrawer(GravityCompat.START)
//
//                }
//                R.id.idlogout -> {
//
//                    preferences = this.getSharedPreferences(
//                        SharedPrefManager.SHARED_PREF_NAME,
//                        Context.MODE_PRIVATE
//                    )
//                    val editor = preferences.edit()
//                    editor.clear()
//                    editor.apply()
//                    binding.drawer.closeDrawer(GravityCompat.START)
//                    val intent = Intent(applicationContext, LoginActivity::class.java)
//                    startActivity(intent)
//
//                }
////                R.id.my_notifications ->{
////                    val i = Intent(this,TabSubTwoActivity::class.java)
////                    startActivity(i)
////                    drawerLayout.closeDrawer(GravityCompat.START)
////                }
//
//
//            }
//            true
//        }


    }

//    private fun imageListadd(): List<SlideModel> {
//
//        val items = arrayListOf<SlideModel>()
//        items.add(SlideModel("https://as1.ftcdn.net/v2/jpg/04/41/50/04/1000_F_441500497_opBWe6cIoAhCW5PBjxCvv9iVXgTihzU8.jpg","Coupoun 1"))
//        items.add(SlideModel("https://image.freepik.com/free-vector/mega-sale-background-with-discount_23-2148891128.jpg","Coupoun 2"))
//        items.add(SlideModel("https://as1.ftcdn.net/v2/jpg/04/41/50/04/1000_F_441500497_opBWe6cIoAhCW5PBjxCvv9iVXgTihzU8.jpg","Coupoun 3"))
//        items.add(SlideModel("https://image.freepik.com/free-vector/mega-sale-background-with-discount_23-2148891128.jpg","Coupoun 4"))
//        items.add(SlideModel("https://image.freepik.com/free-vector/mega-sale-background-with-discount_23-2148891128.jpg"))
//
//        return items
//    }


//    override fun onBackPressed() {
//        if (drawerLayout != null){
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)){
//                drawerLayout.closeDrawer(GravityCompat.START)
//            }
//        }
//        super.onBackPressed()
//    }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (toggle.onOptionsItemSelected(item)) {
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }


}