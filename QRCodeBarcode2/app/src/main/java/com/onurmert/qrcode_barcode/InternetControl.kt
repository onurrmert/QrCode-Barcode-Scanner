package com.onurmert.qrcode_barcode

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.google.android.material.snackbar.Snackbar

class InternetControl(val context: Context, val view: View) {

    fun netControl1() : Boolean{

        val manager =
            context.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = manager.activeNetworkInfo

        if (networkInfo == null) {
            return true
        }else{
            return false
        }
    }

    fun internetSnackMessage(connection : Boolean){
        if (connection){
            Snackbar.make(view,
                "Your internet connection has been interrupted!!!",
                Snackbar.LENGTH_INDEFINITE)
                .setAction("Ok", View.OnClickListener {
                    internetSnackMessage(netControl1())
                }).show()
        }else{
            return
        }
    }
}