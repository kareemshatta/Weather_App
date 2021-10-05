package com.example.weather_app.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.weather_app.R

object Utils {

    fun showLoadingDialog(context: Context, withOutShadow: Boolean = true): AlertDialog? {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        val builder = context.let { it1 ->
            AlertDialog.Builder(it1).setView(mDialogView)
        }.show()
        builder.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            if (withOutShadow) {
                clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }
        return builder
    }

    fun fullScreen(activity: Activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}
