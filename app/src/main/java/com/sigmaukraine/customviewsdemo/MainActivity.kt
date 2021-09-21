package com.sigmaukraine.customviewsdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sigmaukraine.customviewsdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupViews()
        setContentView(binding.root)
    }

    private fun setupViews() {
        with(binding) {
            toggleTextButton.setOnClickListener {
                shapeSelectorView.toggleDisplayingShapeName()
            }
            redColorButton.setOnClickListener {
                shapeSelectorView.setShapeColor(getColor(R.color.red))
            }
            greenColorButton.setOnClickListener {
                shapeSelectorView.setShapeColor(getColor(R.color.green))
            }
            blueColorButton.setOnClickListener {
                shapeSelectorView.setShapeColor(getColor(R.color.blue))
            }
            shapeTextButton.setOnClickListener {
                shapeSelectorView.changeShape()
            }
        }
    }
}