package com.example.hcanvas

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hcanvas.views.PathDrawable


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = TextView(this)
        view.text = "click me"
        view.setTextColor(Color.TRANSPARENT)
        view.gravity = Gravity.CENTER
        view.textSize = 48f
        val d = PathDrawable(this)
        view.setBackgroundDrawable(d)
        val l: View.OnClickListener = View.OnClickListener { d.startAnimating() }
        view.setOnClickListener(l)
        setContentView(view)
    }
}