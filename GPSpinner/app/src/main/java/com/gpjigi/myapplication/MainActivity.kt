package com.gpjigi.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gpjigi.library.GPSpinner

class MainActivity : AppCompatActivity() {

    val TAG = "AppCompatActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gpSpinner:GPSpinner = findViewById(R.id.numberGPSpinner)
        val linearLayout: LinearLayout = gpSpinner.findViewById(com.gpjigi.mylibrary.R.id.np_linearLayout_list);
        linearLayout.setClickable(true)
        linearLayout.setFocusable(true)
        linearLayout.requestFocus()

        gpSpinner.setOnGPClickListenr() {
            Log.d(TAG, "gpSpinner > Click()");
        }


    }
}