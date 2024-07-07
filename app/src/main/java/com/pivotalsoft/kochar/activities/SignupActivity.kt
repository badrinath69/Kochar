package com.pivotalsoft.kochar.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.mukesh.OnOtpCompletionListener
import com.pivotalsoft.kochar.R
import com.pivotalsoft.kochar.Utilities.Utils
import com.pivotalsoft.kochar.Utilities.snackbar
import com.pivotalsoft.kochar.api.RetrofitClient
import com.pivotalsoft.kochar.databinding.ActivitySignupBinding
import com.pivotalsoft.kochar.modal.SharedPrefManager
import com.pivotalsoft.kochar.response.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {
    private var otp: Int? = null
    private var code: Int? = null
    private var mob: String? = null
    val TAG = "SIGNUPACTIVITY"
    lateinit var binding: ActivitySignupBinding
    private var bool = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btBack.setOnClickListener {
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
            finish()
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        mob = binding.editTextNumberSignup.text.toString()
        binding.vfbt.isEnabled = false
        //otpverify.isVisible = true
        binding.vfbt.setOnClickListener {
            otpSend(mob!!)
            binding.otpverify.isVisible = true
        }
        binding.otpverify.setOnClickListener {
            Log.d(TAG, "onViewClicked: $otp")
            if (code == otp) {
                binding.otpconfirmed.isVisible = true
                Toast.makeText(applicationContext, "OTP Verified.", Toast.LENGTH_SHORT).show()
                binding.mukushview.isVisible = false
                binding.vfbt.isVisible = false
                binding.otpverify.isVisible = false
                bool = true
                binding.editTextNumberSignup.inputType = InputType.TYPE_NULL
            } else {
                Toast.makeText(applicationContext, "OTP Invalid.", Toast.LENGTH_SHORT).show()
            }

        }
        binding.editTextNumberSignup.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val n  = binding.editTextNumberSignup.text.toString()

                binding.vfbt.isEnabled = !n.isEmpty() && mobileValiate(n)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
///////////////////////////////////////////////////////////////////////////////////////////////////////////



        binding.buttonSignUp.setOnClickListener {
            val name = binding.editTextNameSignup.text.toString().trim()
            val mobile = binding.editTextNumberSignup.text.toString().trim()
            val password = binding.editTextPasswordSignup.text.toString().trim()


            if(name.isEmpty()){
                binding.editTextNameSignup.error = "Name required"
                binding.editTextNameSignup.requestFocus()
                return@setOnClickListener
            }
            if(mobile.isEmpty()){
                binding.editTextNumberSignup.error = "Mobile Number Required"
                binding.editTextNumberSignup.requestFocus()
                return@setOnClickListener

            }

            if (bool == true){

            }else{
                binding.layoutRoot.snackbar("Please Verify OTP")
                return@setOnClickListener
            }


            if (mobileValiate(mobile)){
            }else{
                binding.editTextNumberSignup.error = "check the number you have entered"
                binding.editTextNumberSignup.requestFocus()
                return@setOnClickListener

            }


            if(password.isEmpty()){
                binding.editTextPasswordSignup.error = "Password required"
                binding.editTextPasswordSignup.requestFocus()
                return@setOnClickListener
            }

            val utilsclass: Utils = Utils()
            utilsclass.startLoadingDialog(this)



            RetrofitClient.instance.signupUser(name,mobile,password)
                .enqueue(object : Callback<SignupResponse>{
                    override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {


                        val responseBody = response.body()
                        responseBody?.let {
                            if (responseBody.status == "success"){

                                utilsclass.dismissDialog()
                                val intent = Intent(applicationContext, LoginActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                Toast.makeText(applicationContext,"successfully register please login to continue",
                                    Toast.LENGTH_LONG).show()
                                startActivity(intent)
                                overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim)

                            }else{
                                utilsclass.dismissDialog()
                                Toast.makeText(applicationContext,"Mobile Number Already Registered",
                                    Toast.LENGTH_SHORT).show()
                            }


                        }
                    }

                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                        binding.layoutRoot.snackbar("Please Check the internet")
                    }
                })




            }
        }

    private fun mobileValiate(text : String): Boolean {
        var p: Pattern = Pattern.compile("[6-9][0-9]{9}")
        var m: Matcher = p.matcher(text)
        return m.matches()
    }

    private fun otpSend(mobile: String) {
        // code = Random().nextInt(9000) + 1000
        code = 1234
        Log.d(TAG, "otpSend: $code")
//        val message =
//            " Your registration OTP is $code"
//        val urlString =
//            "http://app.smsmoon.com/submitsms.jsp?user=PIVOTAL&key=7d9a0596c8XX&mobile=$mobile&message=$message&senderid=PVOTAL&accusage=1"
//        val webView = WebView(applicationContext)
//        webView.loadUrl(urlString)
        Toast.makeText(
            applicationContext,
            "OTP sent to your registered mobile no.${mobile}",
            Toast.LENGTH_SHORT
        ).show()
        binding.mukushview.setOtpCompletionListener(OnOtpCompletionListener { otprecive ->
            otp = otprecive.toInt()
            Log.d(TAG, "otpnum: $otp")
        })
    }


    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

}
