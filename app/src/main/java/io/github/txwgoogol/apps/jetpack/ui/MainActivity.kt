package io.github.txwgoogol.apps.jetpack.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.txwgoogol.apps.jetpack.R
import io.github.txwgoogol.apps.jetpack.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}