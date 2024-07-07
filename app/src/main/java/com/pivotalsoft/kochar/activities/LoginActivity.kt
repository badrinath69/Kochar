package com.pivotalsoft.kochar.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.Utils
import com.pivotalsoft.kochar.Utilities.snackbar
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.databinding.ActivityLoginBinding
import com.pivotalsoft.kochar.modal.SharedPrefManager
import com.pivotalsoft.kochar.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "Login_Activity"
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val editTextNumber = findViewById<TextInputEditText>(R.id.editTextNumber)
        val editTextPassword = findViewById<TextInputEditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<AppCompatButton>(R.id.buttonLogin)

        binding.signupBt.setOnClickListener {
            val i = Intent(this,SignupActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.buttonLogin.setOnClickListener {
            val mobile = editTextNumber.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if(mobile.isEmpty()){
                editTextNumber.error = "Number required"
                editTextNumber.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            val utilsclass: Utils = Utils()
            utilsclass.startLoadingDialog(this)
            RetrofitClient.instance.userLogin(mobile,password)
                .enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val responseBody = response.body()
                        response?.let {
                            if (responseBody?.message == "success"){

                                utilsclass.dismissDialog()
                                Log.d(TAG, "onResponse: Successful login")
                                SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.userdata!!)

                                val intent = Intent(this@LoginActivity, OrderActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                startActivity(intent)
                                //overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim)


                            }else{


                                utilsclass.dismissDialog()
                                Toast.makeText(applicationContext, "Invalid username/password", Toast.LENGTH_LONG).show()

                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                        utilsclass.dismissDialog()
                        binding.sv1.snackbar("Please Check The Internet Connection")

                    }

                })



        }
    }
    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, OrderActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}