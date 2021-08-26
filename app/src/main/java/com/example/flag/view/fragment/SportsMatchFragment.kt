package com.example.flag.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.R
import com.example.flag.adapter.MatchingAdapter
import com.example.flag.adapter.TabItemRecyclerViewAdapter
import com.example.flag.adapter.TabItemRecyclerViewAdapter2
import com.example.flag.data.MatchData
import com.example.flag.databinding.FragmentSportsMatchBinding
import com.example.flag.view.activity.AllActivity
import com.example.flag.view.activity.RoomActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SportsMatchFragment : Fragment() {
    lateinit var group :String
    lateinit var team2 :String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    val long_now = System.currentTimeMillis()

    // 현재 시간을 Date 타입으로 변환
    val t_date = Date(long_now)
    val t_dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale("ko", "KR"))
    val str_date = t_dateFormat.format(t_date)

    val today = str_date.substring(8,10).toInt()
    var day:String = (today+64).toChar().toString() + today.toString()+"일"

    lateinit var adapter: TabItemRecyclerViewAdapter
    lateinit var adapter2: TabItemRecyclerViewAdapter2
    lateinit var adapter3: TabItemRecyclerViewAdapter2
    lateinit var adapter4 : MatchingAdapter
    lateinit var aaa :DatabaseReference
    lateinit var dialogView:View
    lateinit var binding:FragmentSportsMatchBinding
    var rdb:FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")

    var area :String = "a서울"
    var event:String = "a전체"


    var data = ArrayList<ArrayList<MatchData>>()
    var datass = ArrayList<MatchData>()
    var datass2 = ArrayList<MatchData>()
    var datass3 = ArrayList<MatchData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        val uid = auth.currentUser?.uid.toString()
        database.child("users").child(uid).get().addOnSuccessListener {
            group = it.child("school").value.toString()
            team2 = it.child("team").value.toString()

        }



        binding = FragmentSportsMatchBinding.inflate(layoutInflater,container,false)
        binding.recyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView3.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        binding.matchRecycler.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL, false)
        dialogView = inflater.inflate(R.layout.custom_dialog, container, false)
        givemeAllData(day, area)

        initData()
        init()
        return binding.root
         }

    private fun givemeAllData(day:String,area:String) {

        mydatas.addListenerForSingleValueEvent(object : ValueEventListener
        {

            override fun onDataChange(snapshot: DataSnapshot) {
                var k = 0
                val data3 = snapshot.child(day)

                for (ds in data3.children) { // 축구 / 농구 / 풋살
                    ds.child(area).ref.orderByChild("time").startAt(900.0).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            dataSet(snapshot, k)
                            k++

                        }
                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                }


            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("error","error")

            }

        })

    }

    private fun dataSet(snapshot: DataSnapshot, k: Int) {


            for(ds in snapshot.children)  {// data

                var temp = MatchData(ds.child("matchTitle").value.toString(),
                        ds.child("time").value.toString().toInt(),
                        ds.child("mainImg").value.toString(),
                        ds.child("group").value.toString(),
                        ds.child("group2").value.toString(),
                        ds.child("team").value.toString(),
                        ds.child("team2").value.toString(),
                        ds.child("num").value.toString(),
                        ds.child("accept").value.toString().toBoolean()
                )


                if(k==0) {
                    datass.add(temp) // 축구

                }
                if(k==1) {
                    datass2.add(temp) // 농구

                }
                if(k==2) {
                    datass3.add(temp) // 풋살
                }
            }
            if(k==0) {
                data.add(datass)
            }
            if(k==1) {
                data.add(datass2)
            }
            if(k==2) {
                data.add(datass3)
                adapter4init(data)
            }

    }

    private fun closedMatch(holder: MatchingAdapter.ViewHolder, num: Int, data: ArrayList<ArrayList<MatchData>>, position:Int) {

        val mBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .show()

        val groupname = dialogView.findViewById<TextView>(R.id.groupname)
        val teamname = dialogView.findViewById<TextView>(R.id.teamname)
        val time = dialogView.findViewById<TextView>(R.id.time)
        val sportsname = dialogView.findViewById<TextView>(R.id.sportsname)

        sportsname.text = holder.matchTitle.text
        if(num==1) {
            groupname.text = holder.firstView.schoolTitle.text
            teamname.text = holder.firstView.teamTitle.text
            time.text = holder.firstView.timeTitle.text
        }
        else if(num==2) {
            groupname.text = holder.secondView.schoolTitle.text
            teamname.text = holder.secondView.teamTitle.text
            time.text = holder.secondView.timeTitle.text
        }
        else {
            groupname.text = holder.thirdView.schoolTitle.text
            teamname.text = holder.thirdView.teamTitle.text
            time.text = holder.thirdView.timeTitle.text
        }

        val okButton = dialogView.findViewById<Button>(R.id.yesBtn)
        val noButton = dialogView.findViewById<Button>(R.id.noBtn)

        okButton.setOnClickListener {
            var event = ""
            var team = ""
            database.child("teamData").child(event)

            if(holder.matchTitle.text == "축구") {
                event = "b축구"
            }
            if(holder.matchTitle.text == "농구") {
                event = "c농구"
            }
            if(holder.matchTitle.text == "풋살") {
                event = "d풋살"
            }

            database.child("teamData").child(event).child(team2).child("teamTitle").get().addOnSuccessListener {
                team = it.value.toString()
                if(num==1) {
                    mydatas.child(day).child(event).child(area).child("data1").child("accept").setValue(false)
                    mydatas.child(day).child(event).child(area).child("data1").child("group2").setValue(group)
                    mydatas.child(day).child(event).child(area).child("data1").child("team2").setValue(team)
                    data.get(position).get(0).accept = false
                    adapter4.notifyDataSetChanged()
                }
                else if(num==2) {
                    mydatas.child(day).child(event).child(area).child("data2").child("accept").setValue(false)
                    mydatas.child(day).child(event).child(area).child("data2").child("group2").setValue(group)
                    mydatas.child(day).child(event).child(area).child("data2").child("team2").setValue(team)
                    data.get(position).get(1).accept = false
                    adapter4.notifyDataSetChanged()
                }
                else {
                    mydatas.child(day).child(event).child(area).child("data3").child("accept").setValue(false)
                    mydatas.child(day).child(event).child(area).child("data3").child("group2").setValue(group)
                    mydatas.child(day).child(event).child(area).child("data3").child("team2").setValue(team)
                    data.get(position).get(2).accept = false
                    adapter4.notifyDataSetChanged()
                }

            }



            if (dialogView.getParent() != null) {
                (dialogView.getParent() as ViewGroup).removeView(dialogView)
            }
            mBuilder.dismiss()

        }
        noButton.setOnClickListener {
            if (dialogView.getParent() != null) {
                (dialogView.getParent() as ViewGroup).removeView(dialogView)
            }
            mBuilder.dismiss()
        }

    }

    private fun initData() {

        val items: ArrayList<String> = ArrayList()
        val items2: ArrayList<String> = ArrayList()
        val items3: ArrayList<String> = ArrayList()

        mydatas.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp4 = snapshot.children

                for(ds in temp4) {
                    val key = ds.key.toString().substring(1)
                    if(today > key.substring(0,key.length-1).toInt()) {
                        continue
                    }

                    items.add(key)

                }
                adapter = TabItemRecyclerViewAdapter(items)

                binding.recyclerView.adapter = adapter
                items2.add("전체")
                val temp2 = snapshot.child("A1일").children
                for (ds in temp2) {
                    items2.add(ds.key.toString().substring(1))
                }
                adapter2 = TabItemRecyclerViewAdapter2(items2)
                binding.recyclerView2.adapter = adapter2

                val temp = snapshot.child("A1일").child("b축구").children
                for (ds in temp) {
                    items3.add(ds.key.toString().substring(1, 3))
                }
                adapter3 = TabItemRecyclerViewAdapter2(items3)
                binding.recyclerView3.adapter = adapter3

                adapter.itemClickListener = object :
                    TabItemRecyclerViewAdapter.OnItemClickListener {

                    override fun OnItemClick(holder: TabItemRecyclerViewAdapter.ViewHolder, view: View, position:Int) {

                        data.clear()
                        datass.clear()
                        datass2.clear()
                        datass3.clear()

                        val key = holder.idView.text.toString()

                        val key2 = key.substring(0,key.length-1).toInt()
                        if(key2<=26) {
                            day = (key2+64).toChar().toString() + holder.idView.text.toString()
                        }

                        else {
                            day = (key2+70).toChar().toString() + holder.idView.text.toString()
                        }

                        Log.i("day",day)
                        datachange(holder.idView.text.toString(),(key2+64).toChar().toString(),99999)


                    }

                }
                adapter2.itemClickListener = object :
                    TabItemRecyclerViewAdapter2.OnItemClickListener {
                    override fun OnItemClick(holder: TabItemRecyclerViewAdapter2.ViewHolder, view: View) {
                        data.clear()
                        datass.clear()
                        datass2.clear()
                        datass3.clear()


                        if (holder.idView.text == "전체" && holder.idView.isChecked) {
                            event = "a전체"
                            datachange(holder.idView.text.toString(), "a", 1000)
                        }

                        if (holder.idView.text == "축구" && holder.idView.isChecked) {
                            event = "b축구"
                            datachange(holder.idView.text.toString(), "b", 1000)
                        }
                        if (holder.idView.text == "농구" && holder.idView.isChecked) {
                            event = "c농구"
                            datachange(holder.idView.text.toString(), "c", 1000)
                        }

                        if (holder.idView.text == "풋살" && holder.idView.isChecked) {
                            event = "d풋살"
                            datachange(holder.idView.text.toString(), "d", 1000)
                        }
                    }

                }
                val temp3 = snapshot.child("b축구").children
                for (ds in temp3) {
                    items3.add(ds.key.toString().substring(1, 3))
                }
                adapter3 = TabItemRecyclerViewAdapter2(items3)
                binding.recyclerView3.adapter = adapter3
                adapter3.itemClickListener = object :
                    TabItemRecyclerViewAdapter2.OnItemClickListener {
                    override fun OnItemClick(holder: TabItemRecyclerViewAdapter2.ViewHolder, view: View) {
                        data.clear()
                        datass.clear()
                        datass2.clear()
                        datass3.clear()

                        if (holder.idView.text == "서울" && holder.idView.isChecked) {
                            area = "a서울"
                            datachange(holder.idView.text.toString(), "a", 9999)
                        }
                        if (holder.idView.text == "경기" && holder.idView.isChecked) {
                            area = "b경기"
                            datachange(holder.idView.text.toString(), "b", 9999)
                        }
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }



    private fun init() {

        binding.openBtn.setOnClickListener {
            val intent = Intent(context, RoomActivity::class.java)
            startActivity(intent)
        }

        //var datas = rdb.getReference("sportsData/18일/1축구")



//        mydatas.child("R18일").child("b축구").child("a서울").child("data4").setValue(MatchData("축구",1000,"","건국대학교","","RALO팀","","11:11",true))
//        mydatas.child("a27일").child("b축구").child("a서울").child("data1").setValue(MatchData("축구",1000,"","건국대학교","","RALO팀","","11:11",true))
//        mydatas.child("a27일").child("b축구").child("a서울").child("data2").setValue(MatchData("축구",1100,"","한양대학교","","PAKA팀","","11:11",true))
//        mydatas.child("a27일").child("b축구").child("a서울").child("data3").setValue(MatchData("축구",1130,"","동국대학교","","DOPA팀","","11:11",true))
//        mydatas.child("a27일").child("b축구").child("a서울").child("datanum").setValue(3)
//
//        mydatas.child("a27일").child("b축구").child("b경기").child("data1").setValue(MatchData("축구",1000,"","건국대학교","","RALO팀","","11:11",true))
//        mydatas.child("a27일").child("b축구").child("b경기").child("data2").setValue(MatchData("축구",1100,"","한양대학교","","PAKA팀","","11:11",true))
//        mydatas.child("a27일").child("b축구").child("b경기").child("data3").setValue(MatchData("축구",1130,"","동국대학교","","DOPA팀","","11:11",true))
//        mydatas.child("a27일").child("b축구").child("b경기").child("datanum").setValue(3)
//
//        mydatas.child("a27일").child("c농구").child("a서울").child("data1").setValue(MatchData("농구",1000,"","건국대학교","","RALO팀","","5:5",true))
//        mydatas.child("a27일").child("c농구").child("a서울").child("data2").setValue(MatchData("농구",1100,"","한양대학교","","PAKA팀","","5:5",true))
//        mydatas.child("a27일").child("c농구").child("a서울").child("data3").setValue(MatchData("농구",1130,"","동국대학교","","DOPA팀","","5:5",true))
//        mydatas.child("a27일").child("c농구").child("a서울").child("datanum").setValue(3)
//
//        mydatas.child("a27일").child("c농구").child("b경기").child("data1").setValue(MatchData("농구",1000,"","건국대학교","","RALO팀","","5:5",true))
//        mydatas.child("a27일").child("c농구").child("b경기").child("data2").setValue(MatchData("농구",1100,"","한양대학교","","PAKA팀","","5:5",true))
//        mydatas.child("a27일").child("c농구").child("b경기").child("data3").setValue(MatchData("농구",1130,"","동국대학교","","DOPA팀","","5:5",true))
//        mydatas.child("a27일").child("c농구").child("b경기").child("datanum").setValue(3)
//
//        mydatas.child("a27일").child("d풋살").child("a서울").child("data1").setValue(MatchData("풋살",1000,"","건국대학교","","RALO팀","","5:5",true))
//        mydatas.child("a27일").child("d풋살").child("a서울").child("data2").setValue(MatchData("풋살",1100,"","한양대학교","","PAKA팀","","5:5",true))
//        mydatas.child("a27일").child("d풋살").child("a서울").child("data3").setValue(MatchData("풋살",1130,"","동국대학교","","DOPA팀","","5:5",true))
//        mydatas.child("a27일").child("d풋살").child("a서울").child("datanum").setValue(3)
//
//        mydatas.child("a27일").child("d풋살").child("b경기").child("data1").setValue(MatchData("풋살",1000,"","건국대학교","","RALO팀","","5:5",true))
//        mydatas.child("a27일").child("d풋살").child("b경기").child("data2").setValue(MatchData("풋살",1100,"","한양대학교","","PAKA팀","","5:5",true))
//        mydatas.child("a27일").child("d풋살").child("b경기").child("data3").setValue(MatchData("풋살",1130,"","동국대학교","","DOPA팀","","5:5",true))
//        mydatas.child("a27일").child("d풋살").child("b경기").child("datanum").setValue(3)

//


    }
    fun datachange(title:String,num:String,checknum:Int) {


        if(checknum==1000 && event =="a전체") {
            givemeAllData(day,area)
        }
        else if(checknum==9999 && event =="a전체") {
            givemeAllData(day,area)
        }
        else if(checknum==99999 && event=="a전체") {
            givemeAllData(day,area)
        }
        else {

            if(checknum==1000) aaa = mydatas.child(day).child(num + title).child(area).ref
            if(checknum==9999) aaa = mydatas.child(day).child(event).child(num + title).ref
            if(checknum==99999) aaa = mydatas.child(num + title).child(event).child(area).ref

            aaa.orderByChild("time").startAt(900.0).addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    for(ds in snapshot.children){
                        var temp = MatchData(ds.child("matchTitle").value.toString(),
                                ds.child("time").value.toString().toInt(),
                                ds.child("mainImg").value.toString(),
                                ds.child("group").value.toString(),
                                ds.child("group2").value.toString(),
                                ds.child("team").value.toString(),
                                ds.child("team2").value.toString(),
                                ds.child("num").value.toString(),
                                ds.child("accept").value.toString().toBoolean()
                        )
                        datass.add(temp)
                    }


                    data.add(datass)

                    adapter4init(data)


                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", "error")

                }
            })


        }




    }

    private fun adapter4init(data: ArrayList<ArrayList<MatchData>>) {
        adapter4 = MatchingAdapter(data)
        binding.matchRecycler.adapter = adapter4

        adapter4.itemClickListener = object : MatchingAdapter.OnItemClickListener {
            override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View, data: ArrayList<ArrayList<MatchData>>, position:Int) {
                val intent = Intent(context, AllActivity::class.java)
                intent.putExtra("data",day)
                if(event=="a전체") {
                    if(holder.matchTitle.text=="축구"){
                        intent.putExtra("data2","b축구")
                    }
                    if(holder.matchTitle.text=="농구"){
                        intent.putExtra("data2","c농구")
                    }
                    if(holder.matchTitle.text=="풋살"){
                        intent.putExtra("data2","d풋살")
                    }
                }
                else {
                    intent.putExtra("data2",event)
                }
                intent.putExtra("data3",area)
                startActivity(intent)
                activity!!.finish()

            }
        }
        adapter4.itemClickListener2 = object : MatchingAdapter.OnItemClickListener {
            override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View, data: ArrayList<ArrayList<MatchData>>, position:Int) {
                closedMatch(holder,1,data,position)
            }
        }
        adapter4.itemClickListener3 = object : MatchingAdapter.OnItemClickListener {
            override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View, data: ArrayList<ArrayList<MatchData>>, position:Int) {
                closedMatch(holder,2,data,position)

            }
        }
        adapter4.itemClickListener4 = object : MatchingAdapter.OnItemClickListener {
            override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View, data: ArrayList<ArrayList<MatchData>>, position:Int) {
                closedMatch(holder,3,data,position)

            }
        }

    }
}






