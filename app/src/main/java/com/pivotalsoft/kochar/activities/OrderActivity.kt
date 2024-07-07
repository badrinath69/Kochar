package com.pivotalsoft.kochar.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.adapters.OrderAdapter
import com.pivotalsoft.kochar.databinding.ActivityOrderBinding
import com.pivotalsoft.kochar.modal.OrderDataModel
import com.pivotalsoft.kochar.modal.SharedPrefManager
import java.lang.Exception


class OrderActivity : AppCompatActivity() {
    //private var imageSlider: ImageSlider? = null
    val imageList = ArrayList<SlideModel>()
    lateinit var toogle: ActionBarDrawerToggle
    lateinit  var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout

    private lateinit var preferences: SharedPreferences
    private lateinit var binding:ActivityOrderBinding

    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val toolbar: Toolbar = findViewById(R.id.toolbaroorderactivity)
       drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.nav_one)

        try {
            setSupportActionBar(toolbar)
        }catch (e: Exception){
            supportActionBar?.hide()
        }

        toogle = ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu)



        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {

                R.id.my_profile -> {
                    // Toast.makeText(applicationContext, "change password", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, AccountDetails::class.java)
                    startActivity(i)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.my_orders -> {
                    val i = Intent(this,HomeActivity::class.java)
                    startActivity(i)
                    drawerLayout.closeDrawer(GravityCompat.START)

                }
                R.id.idlogout -> {

                    preferences = this.getSharedPreferences(
                        SharedPrefManager.SHARED_PREF_NAME,
                        Context.MODE_PRIVATE
                    )
                    val editor = preferences.edit()
                    editor.clear()
                    editor.apply()
                    binding.drawer.closeDrawer(GravityCompat.START)
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)

                }
//                R.id.my_notifications ->{
//                    val i = Intent(this,TabSubTwoActivity::class.java)
//                    startActivity(i)
//                    drawerLayout.closeDrawer(GravityCompat.START)
//                }


            }
            true
        }

    binding.fab.setOnClickListener {
        val i = Intent(this,FilterProduct::class.java)
        startActivity(i)
    }

//        val imageSlider = findViewById<ImageSlider>(R.id.orderimageslider)
//         imageSlider.setImageList(imageList)
//        imageList.add(SlideModel("https://www.baapoffers.com/uploads/1416505685.jpg", " GET UPTO 30% OFF",ScaleTypes.FIT))
//        imageList.add(SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyjo4nylXQfJQKSphRFJ_uY4QcNzVehMUUQA&usqp=CAU", "WINTER SALE 20% OFF",ScaleTypes.FIT))


    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId
//        when(id){
//            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
//        }
//                return super.onOptionsItemSelected(item)
//    }
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            moveTaskToBack(true)
            Process.killProcess(Process.myPid())
            System.exit(1)
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        if (drawerLayout != null){
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}