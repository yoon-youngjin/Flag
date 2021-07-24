package com.example.flag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivityMatchBinding

class MatchActivity : AppCompatActivity() {
    lateinit var binding:ActivityMatchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)

        setContentView(binding.root)
        init()
    }

    fun init() {
        binding.group.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.spradio -> {
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.framelayout,SportsMatchFragment())
                    fragment.commit()
                }
                R.id.espradio -> {
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.framelayout,ESportsMatchFragment())
                    fragment.commit()
                }
            }
        }
    }
}