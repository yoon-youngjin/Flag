package com.example.flag.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.R
import com.example.flag.data.User
import com.example.flag.adapter.ViewPagerAdapter
import com.example.flag.data.MatchData
import com.example.flag.databinding.ActivitySecondBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SecondActivity : AppCompatActivity() {
    val long_now = System.currentTimeMillis()
    val t_date = Date(long_now)
    val t_dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale("ko", "KR"))
    val str_date = t_dateFormat.format(t_date)

    val today = str_date.substring(8,10).toInt()
    var day:String = (today+64).toChar().toString() + today.toString()+"일"
    lateinit var group :String
    var check = false

    var MyData =  ArrayList<MatchData>()
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")
    lateinit var binding: ActivitySecondBinding
    lateinit var adapter: ViewPagerAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        val uid = auth.currentUser?.uid.toString()
        Log.i("uid",uid)
        database.child("users").child(uid).get().addOnSuccessListener {

            group = it.child("school").value.toString()
            Log.i("group",group.toString())
        }


        //getUserData(uid)
//        auth.uid?.let { createMatch("21.08.14", "08:00", "futsal", "건국대학교", it, "") }

        //getMatchData()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }


    private fun getUserData(uid: String) {
        database.child("users").child(uid).get().addOnSuccessListener {
            group = it.child("school").value.toString()

            val user = it.getValue<User>()
        }.addOnFailureListener {
            Log.e("getUserData", it.toString())
        }
    }

    fun init() {



        var data: ArrayList<Int> = arrayListOf<Int>(R.drawable.img2, R.drawable.img1)
        adapter = ViewPagerAdapter(data)
        binding.viewpager.adapter = adapter

        binding.addteamBtn.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.spBtn.setOnClickListener {
            val intent = Intent(this, MatchActivity::class.java)
            intent.putExtra("matchevent", "sports")
            startActivity(intent)
            finish()
        }
        binding.espBtn.setOnClickListener {
            val intent = Intent(this, MatchActivity::class.java)
            intent.putExtra("matchevent", "esports")
            startActivity(intent)
            finish()
        }

        mydatas.orderByKey().startAt(day).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds0 in snapshot.children) {
                    if (check == true) break
                    for (ds in ds0.children) {
                        if (check == true) break
                        for (ds2 in ds.children) {
                            if (check == true) break
                            ds2.ref.orderByChild("accept").equalTo(false).addListenerForSingleValueEvent(object :
                                ValueEventListener {

                                override fun onDataChange(snapshot: DataSnapshot) {

                                    val data = snapshot.children.iterator()

                                    while (data.hasNext()) {
                                        val data2 = data.next()
                                        val item2 = data2.child("group").value
                                        val item3 = data2.child("group2").value

                                        if (item2.toString().equals(group) || item3.toString().equals(group)) {

                                            val item = MatchData(data2.child("matchTitle").value.toString(), data2.child("time").value.toString().toInt(), "", data2.child("group").value.toString(), data2.child("group2").value.toString(), data2.child("team").value.toString(), data2.child("team2").value.toString(), data2.child("num").value.toString(), false)
                                            MyData.add(item)
                                            if (MyData.size == 3) {
                                                Log.i("check", "check")
                                                initData(MyData, 3)
                                                check = true
                                                break
                                            }



                                        }
                                        Log.i("data",MyData.toString())

                                        if (MyData.size == 2) {
                                            Log.i("check2", "check2")
                                            initData(MyData, 2)
                                        } else if (MyData.size == 1) {
                                            Log.i("check3", "check3")
                                            initData(MyData, 1)
                                        } else if(MyData.size == 0) {
                                            Log.i("check4", "check4")
                                            initData(MyData, 0)
                                        }

                                    }

                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



    }

    private fun initData(data: ArrayList<MatchData>, num:Int) {

        if(num!=0) {

            if(num>=1) {
                binding.layout1.visibility = View.VISIBLE

                binding.group12.text = data.get(0).group
                if (binding.group12.text == "건국대학교") binding.matchImg3.setImageResource(R.drawable.img33)
                else if (binding.group12.text == "한양대학교") binding.matchImg3.setImageResource(R.drawable.img44)
                else if (binding.group12.text == "동국대학교") binding.matchImg3.setImageResource(R.drawable.img55)

                binding.team12.text = data.get(0).team

                binding.sportTitle.text = data.get(0).matchTitle
                binding.numTitle.text = data.get(0).num

                binding.group22.text = data.get(0).group2
                if (binding.group22.text == "건국대학교") binding.matchImg5.setImageResource(R.drawable.img33)
                else if (binding.group22.text == "한양대학교") binding.matchImg5.setImageResource(R.drawable.img44)
                else if (binding.group22.text == "동국대학교") binding.matchImg5.setImageResource(R.drawable.img55)
                binding.team22.text = data.get(0).team2
            }

            if(num>=2) {
                binding.layout2.visibility = View.VISIBLE
                binding.group33.text = data.get(1).group
                if (binding.group33.text == "건국대학교") binding.matchImg6.setImageResource(R.drawable.img33)
                else if (binding.group33.text == "한양대학교") binding.matchImg6.setImageResource(R.drawable.img44)
                else if (binding.group33.text == "동국대학교") binding.matchImg6.setImageResource(R.drawable.img55)
                binding.team33.text = data.get(1).team

                binding.sportTitle1.text = data.get(1).matchTitle
                binding.numTitle1.text = data.get(1).num

                binding.group44.text = data.get(1).group2
                if (binding.group44.text == "건국대학교") binding.matchImg7.setImageResource(R.drawable.img33)
                else if (binding.group44.text == "한양대학교") binding.matchImg7.setImageResource(R.drawable.img44)
                else if (binding.group44.text == "동국대학교") binding.matchImg7.setImageResource(R.drawable.img55)
                binding.team44.text = data.get(1).team2
            }

            if (num>=3) {
                binding.layout3.visibility = View.VISIBLE
                binding.group55.text = data.get(2).group
                if (binding.group55.text == "건국대학교") binding.matchImg8.setImageResource(R.drawable.img33)
                else if (binding.group55.text == "한양대학교") binding.matchImg8.setImageResource(R.drawable.img44)
                else if (binding.group55.text == "동국대학교") binding.matchImg8.setImageResource(R.drawable.img55)
                binding.team55.text = data.get(2).team

                binding.sportTitle2.text = data.get(2).matchTitle
                binding.numTitle2.text = data.get(2).num

                binding.group66.text = data.get(2).group2
                if (binding.group66.text == "건국대학교") binding.matchImg9.setImageResource(R.drawable.img33)
                else if (binding.group66.text == "한양대학교") binding.matchImg9.setImageResource(R.drawable.img44)
                else if (binding.group66.text == "동국대학교") binding.matchImg9.setImageResource(R.drawable.img55)
                binding.team66.text = data.get(2).team2
            }

        }
        else {
            binding.layout.visibility = View.GONE
        }




    }
}