package com.example.flag.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.R
import com.example.flag.databinding.ActivityTeamBinding
import com.example.flag.view.fragment.ESportsTeamFragment
import com.example.flag.view.fragment.SportsTeamFragment

class TeamActivity : AppCompatActivity() {


    lateinit var binding:ActivityTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.framelayout2, SportsTeamFragment())
        fragment.commit()
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    override fun onBackPressed() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    fun init() {

        binding.group2.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.spradio2 -> {
                    binding.spradio2.setTextColor(Color.WHITE)
                    binding.espradio2.setTextColor(Color.BLACK)

                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.framelayout2, SportsTeamFragment())
                    fragment.commit()
                }
                R.id.espradio2 -> {
                    binding.espradio2.setTextColor(Color.WHITE)
                    binding.spradio2.setTextColor(Color.BLACK)
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.framelayout2, ESportsTeamFragment())
                    fragment.commit()
                }
            }
        }

    }

}