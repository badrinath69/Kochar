package com.pivotalsoft.kochar.modal

import android.content.Context
import com.pivotalsoft.kochar.activities.AccountDetails
import com.pivotalsoft.kochar.userdetails.User

class SharedPrefManager(val mCtx: Context) {

    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("userid", null) != null
        }

    val isSignup: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getString("userid", null) != null
        }

//    val user: User?
//        get() {
//            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//            return sharedPreferences.getString("status", null)?.let {
//                User(
//                    sharedPreferences.getString("empid", null)!!,
//                    sharedPreferences.getString("name", null)!!,
//                    sharedPreferences.getString("adhar", null)!!,
//                    sharedPreferences.getString("proof", null)!!,
//                    sharedPreferences.getString("mobile", null)!!,
//                    sharedPreferences.getString("password", null)!!,

//                    it
//                )
//            }
//        }


    fun saveUser(user: List<User>) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("userid", user[0].userid)
        editor.putString("fullname", user[0].fullname)
        editor.putString("mobile", user[0].mobile)
        editor.putString("password", user.get(0).password)
        editor.putString("role",user[0].role)
        editor.putString("status", user.get(0).status)
        editor.putString("schoolcode", user[0].schoolcode)
        editor.apply()
    }

//    fun userDetails(user: List<ProfileDetails>) {
//
//        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//
//        editor.putString("empid2",user.get(0).empid)
//        editor.putString("name2", user.get(0).name)
//        editor.putString("adhar2", user.get(0).adhar)
//        editor.putString("proof2", user.get(0).proof)
//        editor.putString("mobile2", user.get(0).mobile)
//        editor.putString("password2", user.get(0).password)
//        editor.putString("status2", user.get(0).status)
//        editor.apply()
//        editor.commit()
//
//    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        val SHARED_PREF_NAME = "my_shared_preff"
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}