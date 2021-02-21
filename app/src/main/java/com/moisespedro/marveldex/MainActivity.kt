package com.moisespedro.marveldex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moisespedro.marveldex.databinding.ActivityMainBinding
import com.moisespedro.marveldex.extensions.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
