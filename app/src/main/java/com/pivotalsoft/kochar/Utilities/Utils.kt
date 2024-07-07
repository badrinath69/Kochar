package com.pivotalsoft.kochar.Utilities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.pivotalsoft.kochar.R
import kotlinx.coroutines.CoroutineScope


fun View.snackbar(message: String) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun View.hideKeyboard() {

    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

class Utils(){
    private var dialog: AlertDialog? = null
    fun startLoadingDialog(context: Activity){
        val builder = AlertDialog.Builder(context)
        builder.setIcon(ContextCompat.getDrawable(context.applicationContext,R.drawable.kochar_logo))
        builder.setView(context.layoutInflater.inflate(R.layout.dialogblueprint,null))
        dialog = builder.create()
        dialog?.setCancelable(true)
        dialog?.show()
    }
    fun dismissDialog(){
       dialog?.dismiss()
    }

}
