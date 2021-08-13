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

class SecondActivity : AppCompatActivity() {
    var data2 = ArrayList<MatchData>()
    var group = "건국대학교"
    var check = false
    lateinit var area: String
    lateinit var event: String
    lateinit var data: ArrayList<TestMatchData>
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")
    lateinit var binding: ActivitySecondBinding
    lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    fun init() {

        var data: ArrayList<Int> = arrayListOf<Int>(R.drawable.img2, R.drawable.img1)
        adapter = ViewPagerAdapter(data)
        binding.viewpager.adapter = adapter

        binding.spBtn.setOnClickListener {
            val intent = Intent(this, MatchActivity::class.java)
            intent.putExtra("matchevent", "sports")
            startActivity(intent)
        }
        binding.espBtn.setOnClickListener {
            val intent = Intent(this, MatchActivity::class.java)
            intent.putExtra("matchevent", "esports")
            startActivity(intent)
        }
        val long_now = System.currentTimeMillis()
        val t_date = Date(long_now)
        val t_dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale("ko", "KR"))
        val str_date = t_dateFormat.format(t_date)

        val today = str_date.substring(8, 10).toInt()
        var t = 0

        data2.add(MatchData("","","","","","","","",true))
        data2.add(MatchData("","","","","","","","",true))
        data2.add(MatchData("","","","","","","","",true))




        for (i in today..14) {
            if (check == true) break
            var day = (i + 64).toChar().toString() + i.toString() + "일"
            for (k in 1..3) {
                if (check == true) break
                if (k == 1) event = "b축구"
                else if (k == 2) event = "c농구"
                else if (k == 3) event = "d풋살"
                for (j in 1..2) {
                    if (check == true) break
                    if (j == 1) area = "a서울"
                    else if (j == 2) area = "b경기"
                    mydatas.child(day).child(event).child(area).orderByChild("accept").equalTo(false).addValueEventListener(object :
                            ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (ds in snapshot.children) {

                                if (ds.child("group").value.toString() == group || ds.child("group2").value.toString() == group) {
                                    val item = MatchData(ds.child("matchTitle").value.toString(), ds.child("time").value.toString(), "", ds.child("group").value.toString(), ds.child("group2").value.toString(), ds.child("team").value.toString(), ds.child("team2").value.toString(), ds.child("num").value.toString(), false)
                                    data2.set(t,item)
                                    t++
                                    Log.i("data",data2.toString())
                                    Log.i("dataaccept",data2.get(2).accept.toString())
                                    if (data2.get(2).accept!=true) {
                                        Log.i("check","check")
                                        initData(data2,3)
                                        check = true
                                        break
                                    }
                                }

                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    }
                    )

                }
            }
//            if(i==14 && data2.get(2).accept == true) {
//                Log.i("check4",data2.toString())
//                initData(data2,2)
//            }
//            if(data2.get(0).accept == false) {
//                initData(data2,1)
//            }
//            if(data2.get(0).accept == true) {
//                initData(data2,0)
//            }

        }

    }

    private fun initData(data: ArrayList<MatchData>,num:Int) {
        Log.i("data22",data.toString())

        if(num!=0) {
            Log.i("check3","check3")


            if(num>=1) {
                Log.i("check12","check12")
                binding.group12.text = data.get(2).group
                if (binding.group12.text == "건국대학교") binding.matchImg3.setImageResource(R.drawable.img33)
                else if (binding.group12.text == "한양대학교") binding.matchImg3.setImageResource(R.drawable.img44)
                else if (binding.group12.text == "동국대학교") binding.matchImg3.setImageResource(R.drawable.img55)

                binding.team12.text = data.get(2).team

                binding.sportTitle.text = data.get(2).matchTitle
                binding.numTitle.text = data.get(2).num

                binding.group22.text = data.get(2).group2
                if (binding.group22.text == "건국대학교") binding.matchImg5.setImageResource(R.drawable.img33)
                else if (binding.group22.text == "한양대학교") binding.matchImg5.setImageResource(R.drawable.img44)
                else if (binding.group22.text == "동국대학교") binding.matchImg5.setImageResource(R.drawable.img55)
                binding.team22.text = data.get(2).team2
            }
//            else {
//                binding.layout2.visibility = View.GONE
//                binding.layout3.visibility = View.GONE
//            }


            if(num>=2) {
                Log.i("check123","check123")
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
//            else {
//                binding.layout3.visibility = View.GONE
//            }

            if (num>=3) {
                Log.i("check345","check345")
                binding.group55.text = data.get(0).group
                if (binding.group55.text == "건국대학교") binding.matchImg8.setImageResource(R.drawable.img33)
                else if (binding.group55.text == "한양대학교") binding.matchImg8.setImageResource(R.drawable.img44)
                else if (binding.group55.text == "동국대학교") binding.matchImg8.setImageResource(R.drawable.img55)
                binding.team55.text = data.get(0).team

                binding.sportTitle2.text = data.get(0).matchTitle
                binding.numTitle2.text = data.get(0).num

                binding.group66.text = data.get(0).group2
                if (binding.group66.text == "건국대학교") binding.matchImg9.setImageResource(R.drawable.img33)
                else if (binding.group66.text == "한양대학교") binding.matchImg9.setImageResource(R.drawable.img44)
                else if (binding.group66.text == "동국대학교") binding.matchImg9.setImageResource(R.drawable.img55)
                binding.team66.text = data.get(0).team2
            }
        }
//        else {
//            Log.i("check2","check2")
//            binding.layout.visibility = View.GONE
//        }




    }
}