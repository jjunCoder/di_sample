package com.jjuncoder.distudy.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jjuncoder.distudy.R
import com.jjuncoder.distudy.di.example.usecase.Starbucks

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Starbucks()
    }
}