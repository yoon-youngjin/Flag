package com.example.flag

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivityTeamBinding

class TeamActivity : AppCompatActivity() {
    lateinit var binding:ActivityTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onBackPressed() {
        val intent = Intent(this,SecondActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    fun init() {

    }

}