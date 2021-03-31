package com.example.tipjar.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tipjar.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    fun moveToActivitySlideToRight(activity: Activity, next: Class<*>) {
        val intent = Intent(activity, next)
        startActivity(intent)
        activity.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity)
    }

    fun closeActivitySlideLeft(activity: Activity) {
        activity.finish()
        activity.overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity)
    }
}