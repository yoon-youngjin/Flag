package com.example.flag

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.databinding.ActivityAllBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllActivity : AppCompatActivity() {
    lateinit var binding:ActivityAllBinding
    lateinit var adapter: AllMatchAdapter
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")
    lateinit var day:String
    lateinit var event:String
    lateinit var area:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllBinding.inflate(layoutInflater)
        day = intent.getStringExtra("data").toString()
        event = intent.getStringExtra("data2").toString()
        area = intent.getStringExtra("data3").toString()

        Log.i("day",day)
        Log.i("event",event)
        Log.i("area",area)

        binding.dataview.text = day.substring(1)
        init()
        setContentView(binding.root)
    }
    fun init() {
        binding.matchtitle.text = event.substring(1)
        if(event.substring(1)=="축구") {
            binding.matchImg2.setImageResource(R.drawable.football)
        }

        if(event.substring(1)=="농구") {
            binding.matchImg2.setImageResource(R.drawable.basketball)
        }
        binding.recycle.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)



        val items:ArrayList<MatchData> = ArrayList()

        mydatas.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child(day).child(event).child(area).children

                for(ds in temp) {
                    if(ds.key.toString()=="datanum") continue


                    items.add( MatchData(ds.key.toString(),ds.child("time").value.toString(),
                        ds.child("mainImg").value.toString(),
                        ds.child("group").value.toString(),
                        ds.child("team").value.toString(),
                        ds.child("num").value.toString(),
            false))
                }
                adapter = AllMatchAdapter(items)
                binding.recycle.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })




    }
}