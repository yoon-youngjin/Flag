package com.example.flag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.databinding.ActivityAllBinding
import com.google.firebase.database.FirebaseDatabase

class AllActivity : AppCompatActivity() {
    lateinit var binding:ActivityAllBinding
    lateinit var adapter: AllMatchAdapter
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllBinding.inflate(layoutInflater)
        binding.dataview.text = intent.getStringExtra("data").toString().substring(1,3)
        init()
        setContentView(binding.root)
    }
    fun init() {
        adapter = AllMatchAdapter(data)

        binding.recycle.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recycle.adapter = adapter


    }
}