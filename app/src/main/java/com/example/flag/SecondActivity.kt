package com.example.flag

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivitySecondBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SecondActivity : AppCompatActivity() {
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")
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
        val long_now = System.currentTimeMillis()
        val t_date = Date(long_now)
        val t_dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale("ko", "KR"))
        val str_date = t_dateFormat.format(t_date)

        val today = str_date.substring(8,10).toInt()

        for(i in 1..2) {
            Log.i("today",(today+64).toChar().toString())

            mydatas.child((today+64).toChar().toString()+today.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (ds in snapshot.children) {
                        ds.child("b축구").child("a서울")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        var data2 = ArrayList<MatchData>()




        mydatas.orderByChild("group").equalTo("건국대학교").addValueEventListener(object :ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.i("dd",snapshot.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        )
//        mydatas.addListenerForSingleValueEvent(object : ValueEventListener
//        {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val data = snapshot.child("A1일").child("b축구").child("a서울")
//                for(ds in data.children) {
//                    if(ds.child("group").value.toString() == "건국대학교" ) {
//                        data2.add(MatchData(ds.child("matchTitle").value.toString(),ds.child("time").value.toString(),"","건국대학교",ds.child("team").value.toString(),"11:11",false))
//                        Log.i("data2",data2.toString())
//                    }
//                    Log.i("ds",ds.toString())
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

//        Log.i("data2",data2.toString())


    }



}