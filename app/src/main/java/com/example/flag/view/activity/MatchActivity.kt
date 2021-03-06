package com.example.flag.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.R
import com.example.flag.view.fragment.SportsMatchFragment
import com.example.flag.databinding.ActivityMatchBinding
import com.example.flag.view.fragment.ESportsMatchFragment

class MatchActivity : AppCompatActivity() {
    override fun onBackPressed() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    lateinit var binding:ActivityMatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        if(intent.getStringExtra("matchevent") == "sports") {
            val fragment = supportFragmentManager.beginTransaction()
            fragment.replace(R.id.framelayout, SportsMatchFragment())
            fragment.commit()
        }
        else {
            binding.espradio.isChecked = true
            binding.spradio.setTextColor(Color.BLACK)
            binding.espradio.setTextColor(Color.WHITE)
            val fragment = supportFragmentManager.beginTransaction()
            fragment.replace(R.id.framelayout, ESportsMatchFragment())
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
                    fragment.replace(R.id.framelayout, SportsMatchFragment())
                    fragment.commit()
                }
                R.id.espradio -> {
                    binding.espradio.setTextColor(Color.WHITE)
                    binding.spradio.setTextColor(Color.BLACK)
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.framelayout, ESportsMatchFragment())
                    fragment.commit()
                }
            }
        }
    }



}