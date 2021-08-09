package com.example.flag

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivityMatchBinding

class MatchActivity : AppCompatActivity() {
    lateinit var binding:ActivityMatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        if(intent.getStringExtra("matchevent") == "sports") {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.replace(R.id.framelayout,SportsMatchFragment())
            fragment.commit()
        }
        else {
            binding.espradio.isChecked = true
            val fragment = supportFragmentManager.beginTransaction()
            fragment.replace(R.id.framelayout,ESportsMatchFragment())
            fragment.commit()
        }
        setContentView(binding.root)
        init()
    }

    fun init() {
        binding.group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.spradio -> {
                    binding.spradio.setTextColor(Color.WHITE)
                    binding.espradio.setTextColor(Color.BLACK)

                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.framelayout,SportsMatchFragment())
                    fragment.commit()
                }
                R.id.espradio -> {
                    binding.espradio.setTextColor(Color.WHITE)
                    binding.spradio.setTextColor(Color.BLACK)
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.framelayout,ESportsMatchFragment())
                    fragment.commit()
                }
            }
        }
    }
}