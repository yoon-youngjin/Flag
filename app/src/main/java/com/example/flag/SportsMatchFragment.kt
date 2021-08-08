package com.example.flag

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.databinding.FragmentSportsMatchBinding
import com.google.firebase.database.*


class SportsMatchFragment : Fragment() {
    lateinit var adapter: TabItemRecyclerViewAdapter
    lateinit var adapter2: TabItemRecyclerViewAdapter2
    lateinit var adapter3: TabItemRecyclerViewAdapter2
    lateinit var adapter4 : MatchingAdapter
    lateinit var aaa :DatabaseReference
    lateinit var dialogView:View
    lateinit var binding:FragmentSportsMatchBinding
    var rdb:FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")

    var area :String = "1서울"
    var event:String = "0전체"
    var day:String = "11일"



    var data = ArrayList<ArrayList<MatchData>>()
    var datass = ArrayList<MatchData>()
    var datass2 = ArrayList<MatchData>()
    var datass3 = ArrayList<MatchData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportsMatchBinding.inflate(layoutInflater,container,false)
        dialogView = inflater.inflate(R.layout.custom_dialog, container, false)

        givemeAllData(day,area)

        initData()
        init()
        return binding.root
         }

    private fun givemeAllData(day:String,area:String) {

        var i =0

        mydatas.addListenerForSingleValueEvent(object : ValueEventListener
        {

            override fun onDataChange(snapshot: DataSnapshot) {

                val data3 = snapshot.child(day)

                for(ds in data3.children) {

                    val data1 = ds.child(area).child("data1")
                    val temp1 = MatchData(ds.key.toString().substring(1),
                            data1.child("time").value.toString(),
                            data1.child("mainImg").value.toString(),
                            data1.child("group").value.toString(),
                            data1.child("team").value.toString(),
                            data1.child("num").value.toString(),
                            data1.child("accept").value.toString().toBoolean()

                    )

                    val data2 = ds.child(area).child("data2")
                    val temp2 = MatchData(ds.key.toString().substring(1),
                            data2.child("time").value.toString(),
                            data2.child("mainImg").value.toString(),
                            data2.child("group").value.toString(),
                            data2.child("team").value.toString(),
                            data2.child("num").value.toString(),
                        data2.child("accept").value.toString().toBoolean()
                    )

                    val data3 = ds.child(area).child("data3")
                    val temp3 = MatchData(ds.key.toString().substring(1),
                            data3.child("time").value.toString(),
                            data3.child("mainImg").value.toString(),
                            data3.child("group").value.toString(),
                            data3.child("team").value.toString(),
                            data3.child("num").value.toString(),
                        data3.child("accept").value.toString().toBoolean()
                    )


                    if(i==0) {
                        datass.add(temp1)
                        datass.add(temp2)
                        datass.add(temp3)
                        data.add(datass)
                    }
                    if(i==1) {
                        datass2.add(temp1)
                        datass2.add(temp2)
                        datass2.add(temp3)
                        data.add(datass2)
                    }
                    if(i==2) {
                        datass3.add(temp1)
                        datass3.add(temp2)
                        datass3.add(temp3)
                        data.add(datass3)
                    }

                    i++


                }



                adapter4 = MatchingAdapter(data)


                binding.matchRecycler.adapter = adapter4

                adapter4.itemClickListener = object : MatchingAdapter.OnItemClickListener {
                    override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View) {
                        val intent = Intent(context,AllActivity::class.java)

                        intent.putExtra("data",day)
                        if(event=="0전체") {
                            if(holder.matchTitle.text=="축구"){
                                intent.putExtra("data2","1축구")
                            }
                            if(holder.matchTitle.text=="농구"){
                                intent.putExtra("data2","2농구")
                            }
                            if(holder.matchTitle.text=="풋살"){
                                intent.putExtra("data2","3풋살")
                            }

                        }
                        else {
                            intent.putExtra("data2",event)
                        }

                        intent.putExtra("data3",area)


                        startActivity(intent)

                    }
                }
                adapter4.itemClickListener2 = object : MatchingAdapter.OnItemClickListener {
                    override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View) {
//                        val mBuilder = AlertDialog.Builder(context)
//                            .setView(dialogView)
//                            .setCancelable(false)
//                            .setTitle("단어 추가")
//                        mBuilder.create()
//                            .show()
//
//                        val groupname = dialogView.findViewById<TextView>(R.id.groupname)
//
//                        val teamname = dialogView.findViewById<TextView>(R.id.teamname)
//                        val time = dialogView.findViewById<TextView>(R.id.time)
//                        val sportsname = dialogView.findViewById<TextView>(R.id.sportsname)
//
//                        groupname.text = holder.firstView.schoolTitle.toString()
//                        teamname.text = holder.firstView.teamTitle.toString()
//                        sportsname.text = holder.matchTitle.toString()
//                        time.text = holder.firstView.timeTitle.toString()
//
//
//
//                        val okButton = dialogView.findViewById<Button>(R.id.yesBtn)
//                        val noButton = dialogView.findViewById<Button>(R.id.noBtn)
//
//                        okButton.setOnClickListener {
//                        }

                        var event = ""

                        if(holder.matchTitle.text == "축구") {
                            event = "1축구"
                        }
                        if(holder.matchTitle.text == "농구") {
                            event = "2농구"
                        }
                        if(holder.matchTitle.text == "풋살") {
                            event = "3풋살"
                        }





                        mydatas.child(day).child(event).child(area).child("data1").child("accept").setValue(false)

                        holder.firstView.matchBtn.isClickable = false
                        holder.firstView.matchBtn.text = "마감"

                    }
                }
                adapter4.itemClickListener3 = object : MatchingAdapter.OnItemClickListener {
                    override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View) {
                        var event = ""

                        if(holder.matchTitle.text == "축구") {
                            event = "1축구"
                        }
                        if(holder.matchTitle.text == "농구") {
                            event = "2농구"
                        }
                        if(holder.matchTitle.text == "풋살") {
                            event = "3풋살"
                        }

                        mydatas.child(day).child(event).child(area).child("data2").child("accept").setValue(false)

                        holder.secondView.matchBtn.isClickable = false
                        holder.secondView.matchBtn.text = "마감"

                    }
                }
                adapter4.itemClickListener4 = object : MatchingAdapter.OnItemClickListener {
                    override fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View) {
                        var event = ""

                        if(holder.matchTitle.text == "축구") {
                            event = "1축구"
                        }
                        if(holder.matchTitle.text == "농구") {
                            event = "2농구"
                        }
                        if(holder.matchTitle.text == "풋살") {
                            event = "3풋살"
                        }

                        mydatas.child(day).child(event).child(area).child("data3").child("accept").setValue(false)

                        holder.thirdView.matchBtn.isClickable = false
                        holder.thirdView.matchBtn.text = "마감"

                    }
                }


            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("error","error")

            }
        })
    }

    private fun initData() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView2.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView3.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        binding.matchRecycler.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)



        val items:ArrayList<String> = ArrayList()
        val items2: ArrayList<String> = ArrayList()
        val items3: ArrayList<String> = ArrayList()

        mydatas.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val temp4 = snapshot.children

                for(ds in temp4) {
                    items.add(ds.key.toString().substring(1))
                }
                adapter = TabItemRecyclerViewAdapter(items)

                binding.recyclerView.adapter = adapter
                items2.add("전체")
                val temp2 = snapshot.child("11일").children
                for (ds in temp2) {
                    items2.add(ds.key.toString().substring(1))
                }
                adapter2 = TabItemRecyclerViewAdapter2(items2)
                binding.recyclerView2.adapter = adapter2

                val temp = snapshot.child("11일").child("1축구").children
                for (ds in temp) {
                    items3.add(ds.key.toString().substring(1, 3))
                }
                adapter3 = TabItemRecyclerViewAdapter2(items3)
                binding.recyclerView3.adapter = adapter3

                adapter.itemClickListener = object : TabItemRecyclerViewAdapter.OnItemClickListener {

                    override fun OnItemClick(holder: TabItemRecyclerViewAdapter.ViewHolder, view: View, position:Int) {

                        data.clear()
                        datass.clear()
                        datass2.clear()
                        datass3.clear()


                        if (holder.idView.text == "1일" && holder.idView.isChecked) {
                            day = "11일"
                            datachange(holder.idView.text.toString(), 1, 99999)
                        }
                        if (holder.idView.text == "2일" && holder.idView.isChecked) {
                            day = "22일"
                            datachange(holder.idView.text.toString(), 2, 99999)
                        }
                    }

                }
                adapter2.itemClickListener = object : TabItemRecyclerViewAdapter2.OnItemClickListener {
                    override fun OnItemClick(holder: TabItemRecyclerViewAdapter2.ViewHolder, view: View) {
                        data.clear()
                        datass.clear()
                        datass2.clear()
                        datass3.clear()


                        if (holder.idView.text == "전체" && holder.idView.isChecked) {
                            event = "0전체"
                            Log.i("check2","check2")
                            datachange(holder.idView.text.toString(), 1, 1000)
                        }

                        if (holder.idView.text == "축구" && holder.idView.isChecked) {
                            event = "1축구"
                            datachange(holder.idView.text.toString(), 1, 1000)
                        }
                        if (holder.idView.text == "농구" && holder.idView.isChecked) {
                            event = "2농구"
                            datachange(holder.idView.text.toString(), 2, 1000)
                        }

                        if (holder.idView.text == "풋살" && holder.idView.isChecked) {
                            event = "3풋살"
                            datachange(holder.idView.text.toString(), 3, 1000)
                        }
                    }

                }
                val temp3 = snapshot.child("1축구").children
                for (ds in temp3) {
                    items3.add(ds.key.toString().substring(1, 3))
                }
                adapter3 = TabItemRecyclerViewAdapter2(items3)
                binding.recyclerView3.adapter = adapter3
                adapter3.itemClickListener = object : TabItemRecyclerViewAdapter2.OnItemClickListener {
                    override fun OnItemClick(holder: TabItemRecyclerViewAdapter2.ViewHolder, view: View) {
                        data.clear()
                        datass.clear()
                        datass2.clear()
                        datass3.clear()

                        if (holder.idView.text == "서울" && holder.idView.isChecked) {
                            area = "1서울"
                            datachange(holder.idView.text.toString(), 1, 9999)
                        }
                        if (holder.idView.text == "경기" && holder.idView.isChecked) {
                            area = "2경기"
                            datachange(holder.idView.text.toString(), 2, 9999)
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
            val intent = Intent(context,RoomActivity::class.java)
            startActivity(intent)

        }




        //var datas = rdb.getReference("sportsData/18일/1축구")

//        mydatas.child("11일").child("3풋살").child("2경기").child("data1").setValue(MatchData("풋살","10:00","","건국대학교","RALO팀","5:5",false))
//        mydatas.child("11일").child("3풋살").child("2경기").child("data2").setValue(MatchData("풋살","11:00","","한양대학교","PAKA팀","5:5",false))
//        mydatas.child("11일").child("3풋살").child("2경기").child("data3").setValue(MatchData("풋살","11:30","","동국대학교","DOPA팀","5:5",false))

//        mydatas.child("11일").child("1축구").child("2경기").child("data1").setValue(MatchData("10:00",R.drawable.img33,"건국대학교","RALO팀","11:11",false))
//        mydatas.child("11일").child("1축구").child("2경기").child("data2").setValue(MatchData("11:00",R.drawable.img44,"한양대학교","PAKA팀","11:11",false))
//        mydatas.child("11일").child("1축구").child("2경기").child("data3").setValue(MatchData("11:30",R.drawable.img55,"동국대학교","DOPA팀","11:11",false))

//        mydatas.child("11일").child("2농구").child("1서울").child("data1").setValue(MatchData("10:00",R.drawable.img33,"건국대학교","RALO팀","5:5",false))
//        mydatas.child("11일").child("2농구").child("1서울").child("data2").setValue(MatchData("11:00",R.drawable.img44,"한양대학교","PAKA팀","5:5",false))
//        mydatas.child("11일").child("2농구").child("1서울").child("data3").setValue(MatchData("11:30",R.drawable.img55,"동국대학교","DOPA팀","5:5",false))
//
//        mydatas.child("11일").child("2농구").child("2경기").child("data1").setValue(MatchData("10:00",R.drawable.img33,"건국대학교","RALO팀","5:5",false))
//        mydatas.child("11일").child("2농구").child("2경기").child("data2").setValue(MatchData("11:00",R.drawable.img44,"한양대학교","PAKA팀","5:5",false))
//        mydatas.child("11일").child("2농구").child("2경기").child("data3").setValue(MatchData("11:30",R.drawable.img55,"동국대학교","DOPA팀","5:5",false))
//
//        mydatas.child("22일").child("1축구").child("1서울").child("data1").setValue(MatchData("10:00",R.drawable.img33,"건국대학교","RALO팀","11:11",false))
//        mydatas.child("22일").child("1축구").child("1서울").child("data2").setValue(MatchData("11:00",R.drawable.img44,"한양대학교","PAKA팀","11:11",false))
//        mydatas.child("22일").child("1축구").child("1서울").child("data3").setValue(MatchData("11:30",R.drawable.img55,"동국대학교","DOPA팀","11:11",false))
//
//        mydatas.child("22일").child("1축구").child("2경기").child("data1").setValue(MatchData("10:00",R.drawable.img33,"건국대학교","RALO팀","11:11",false))
//        mydatas.child("22일").child("1축구").child("2경기").child("data2").setValue(MatchData("11:00",R.drawable.img44,"한양대학교","PAKA팀","11:11",false))
//        mydatas.child("22일").child("1축구").child("2경기").child("data3").setValue(MatchData("11:30",R.drawable.img55,"동국대학교","DOPA팀","11:11",false))
//
//        mydatas.child("22일").child("2농구").child("1서울").child("data1").setValue(MatchData("10:00",R.drawable.img33,"건국대학교","RALO팀","5:5",false))
//        mydatas.child("22일").child("2농구").child("1서울").child("data2").setValue(MatchData("11:00",R.drawable.img44,"한양대학교","PAKA팀","5:5",false))
//        mydatas.child("22일").child("2농구").child("1서울").child("data3").setValue(MatchData("11:30",R.drawable.img55,"동국대학교","DOPA팀","5:5",false))
//
//        mydatas.child("22일").child("2농구").child("2경기").child("data1").setValue(MatchData("10:00",R.drawable.img33,"건국대학교","RALO팀","5:5",false))
//        mydatas.child("22일").child("2농구").child("2경기").child("data2").setValue(MatchData("11:00",R.drawable.img44,"한양대학교","PAKA팀","5:5",false))
//        mydatas.child("22일").child("2농구").child("2경기").child("data3").setValue(MatchData("11:30",R.drawable.img55,"동국대학교","DOPA팀","5:5",false))
//
//
//





    }
    fun datachange(title:String,num:Int,checknum:Int) {

        if(checknum==1000 && event =="0전체") {

            givemeAllData(day,area)
        }
        else if(checknum==9999 && event =="0전체") {
            givemeAllData(day,area)
        }
        else if(checknum==99999 && event=="0전체") {
            givemeAllData(day,area)
        }
        else {

            if(checknum==1000) aaa = mydatas.child(day).child(num.toString() + title).child(area)
            if(checknum==9999) aaa = mydatas.child(day).child(event).child(num.toString() + title)
            if(checknum==99999) aaa = mydatas.child(num.toString() + title).child(event).child(area)

            aaa.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    val data1 = snapshot.child("data1")
                    val temp1 = MatchData(event.substring(1),data1.child("time").value.toString(), data1.child("mainImg").value.toString(), data1.child("group").value.toString(), data1.child("team").value.toString(),
                            data1.child("num").value.toString(), data1.child("accept").value.toString().toBoolean())

                    val data2 = snapshot.child("data2")
                    val temp2 = MatchData(event.substring(1),data2.child("time").value.toString(), data2.child("mainImg").value.toString(), data2.child("group").value.toString(), data2.child("team").value.toString(),
                            data2.child("num").value.toString(), data2.child("accept").value.toString().toBoolean())

                    val data3 = snapshot.child("data3")
                    val temp3 = MatchData(event.substring(1),data3.child("time").value.toString(), data3.child("mainImg").value.toString(), data3.child("group").value.toString(), data3.child("team").value.toString(),
                            data2.child("num").value.toString(), data3.child("accept").value.toString().toBoolean())


                    datass.add(temp1)
                    datass.add(temp2)
                    datass.add(temp3)
                    data.add(datass)


                    adapter4 = MatchingAdapter(data)


                    binding.matchRecycler.adapter = adapter4


                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", "error")

                }
            })


        }




    }
}






