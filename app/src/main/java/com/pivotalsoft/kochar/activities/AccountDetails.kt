package com.pivotalsoft.kochar.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.databinding.ActivityAccountDetailsBinding

class AccountDetails : AppCompatActivity() {
    lateinit var binding: ActivityAccountDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbaraccountactivity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"
        toolbar.navigationIcon = ContextCompat.getDrawable(this,R.drawable.backbtwhite)

//        backbtaccountactivity.setOnClickListener {
//            val i = Intent(this,HomeActivity::class.java)
//            startActivity(i)
//            finish()
//        }
//        val name = intent.getStringExtra("name")
//        val image = intent.getIntExtra("img",R.drawable.fashionbg)
//        txtUserName.text = name
//        img.setImageResource(image)
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater : MenuInflater = menuInflater
//        inflater.inflate(R.menu.menu1,menu)
//        return true
//    }
}
