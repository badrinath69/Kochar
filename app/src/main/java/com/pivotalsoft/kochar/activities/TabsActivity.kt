package com.pivotalsoft.kochar.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.adapters.ViewPagerAdapter
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.adapters.TabAdapter
import com.pivotalsoft.kochar.databinding.ActivityTabsBinding
import com.pivotalsoft.kochar.fragments.MenFragment
import com.pivotalsoft.kochar.fragments.UnisexFragment
import com.pivotalsoft.kochar.fragments.WomenFragment


class TabsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTabsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.backbtwhite))

//        backbttabactivity.setOnClickListener {
//            val i = Intent(this,HomeActivity::class.java)
//            startActivity(i)
//            finish()
//        }
        setUpTabs()

        val index = intent.getStringExtra("index")

     // Toast.makeText(applicationContext,"yout number is $index",Toast.LENGTH_SHORT).show()
        val names: ArrayList<String> = arrayListOf(
            "School Wear","Doctor Wear","Industrial Wear","Fashion Wear"
        )
        when(index){
            "0" -> supportActionBar?.title = names[0]
            "1" -> supportActionBar?.title = names[1]
            "2" -> supportActionBar?.title = names[2]
            "3" -> supportActionBar?.title = names[3]
            else -> supportActionBar?.title = "Select Dress Type"
        }




    }

    private fun setUpTabs() {
        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(MenFragment(),"MEN")
        adapter.addFragment(WomenFragment(),"WOMEN")
        adapter.addFragment(UnisexFragment(),"UNISEX")
        binding.viewPagertabs.adapter = adapter
        binding.tabstabs.setupWithViewPager(binding.viewPagertabs)
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            android.R.id.home -> NavUtils.navigateUpFromSameTask(this)
        }
        return super.onOptionsItemSelected(item)
    }
}