package com.example.flag

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var binding:ActivitySecondBinding
    lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    fun init() {
        setSupportActionBar(binding.toolbar)
        var data:ArrayList<Int> = arrayListOf<Int>(R.drawable.img2,R.drawable.img1)
        adapter = ViewPagerAdapter(data)
        binding.viewpager.adapter = adapter

        binding.spBtn.setOnClickListener {
            val intent = Intent(this,MatchActivity::class.java)
            intent.putExtra("matchevent","sports")
            startActivity(intent)
        }
        binding.espBtn.setOnClickListener {
            val intent = Intent(this,MatchActivity::class.java)
            intent.putExtra("matchevent","esports")
            startActivity(intent)
        }


    }



}