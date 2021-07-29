package com.example.flag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.databinding.FragmentSportsMatchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SportsMatchFragment : Fragment() {
    lateinit var adapter: TabItemRecyclerViewAdapter
    lateinit var adapter2: TabItemRecyclerViewAdapter
    lateinit var adapter3: TabItemRecyclerViewAdapter
    lateinit var adapter4 : MatchingAdapter
    lateinit var binding:FragmentSportsMatchBinding
    var rdb:FirebaseDatabase = FirebaseDatabase.getInstance()
    var datas = rdb.getReference("sportsData/18일/축구")
    var data =arrayListOf<ArrayList<ArrayList<MatchData>>>()
    var datass = ArrayList<ArrayList<MatchData>>()
    val item1:ArrayList<MatchData> = ArrayList()
    val item2:ArrayList<MatchData> = ArrayList()
    val item3:ArrayList<MatchData> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportsMatchBinding.inflate(layoutInflater,container,false)
        initData()

        init()

        return binding.root
         }

    private fun initData() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView2.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView3.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        binding.matchRecycler.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,false)
        val items3:ArrayList<String> = ArrayList()



        datas.addValueEventListener(object : ValueEventListener
        {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val temp = snapshot.children
                    for (ds in temp) {
                        items3.add(ds.key.toString().substring(1,3))
                    }

                    adapter3 = TabItemRecyclerViewAdapter(items3)
                    binding.recyclerView3.adapter = adapter3

                    adapter3.itemClickListener = object : TabItemRecyclerViewAdapter.OnItemClickListener {
                        override fun OnItemClick(holder: TabItemRecyclerViewAdapter.ViewHolder, view: View) {
                            data.clear()
                            datass.clear()
                            item1.clear()
                            item2.clear()
                            item3.clear()

                            if(holder.idView.text=="서울" && holder.idView.isChecked) {
                                datachange(holder.idView.text.toString(),1)
                            }
                            if(holder.idView.text=="경기" && holder.idView.isChecked) {
                                datachange(holder.idView.text.toString(),2)
                            }
                        }

                    }



                    val data1 = snapshot.child("1서울").child("data1")
                    val temp1 = MatchData(
                        data1.child("time").value.toString(),
                        R.drawable.flag,
                        data1.child("group").value.toString(),
                        data1.child("team").value.toString(),
                        "11:11",
                        false

                    )
                    item1.add(temp1)
                    val data2 = snapshot.child("1서울").child("data2")
                    val temp2 = MatchData(
                        data2.child("time").value.toString(),
                        R.drawable.flag,
                        data2.child("group").value.toString(),
                        data2.child("team").value.toString(),
                        "11:11",
                        false
                    )
                    item2.add(temp2)
                    val data3 = snapshot.child("1서울").child("data3")
                    val temp3 = MatchData(
                        data3.child("time").value.toString(),
                        R.drawable.flag,
                        data3.child("group").value.toString(),
                        data3.child("team").value.toString(),
                        "11:11",
                        false
                    )

                    item3.add(temp3)
                    datass.add(item1)
                    datass.add(item2)
                    datass.add(item3)
                    data.add(datass)

                    adapter4 = MatchingAdapter(data)
                    binding.matchRecycler.adapter = adapter4

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("error","error")

            }
        })

    }

    private fun init() {

//        datas.child("1서울").child("data1").setValue(MatchData("10:00",R.drawable.flag,"건국대학교","RALO팀","11:11",false))
//        datas.child("1서울").child("data2").setValue(MatchData("11:00",R.drawable.flag,"홍익대학교","PAKA팀","11:11",false))
//        datas.child("1서울").child("data3").setValue(MatchData("11:30",R.drawable.flag,"동국대학교","DOPA팀","11:11",false))
//
//        datas.child("2경기").child("data1").setValue(MatchData("10:00",R.drawable.flag,"건국대학교","RALO팀","11:11",false))
//        datas.child("2경기").child("data2").setValue(MatchData("11:00",R.drawable.flag,"홍익대학교","PAKA팀","11:11",false))
//        datas.child("2경기").child("data3").setValue(MatchData("11:30",R.drawable.flag,"동국대학교","DOPA팀","11:11",false))
//
//




        val items:ArrayList<String> = ArrayList()
        items.add("18일\n 월")
        items.add("19일\n 화")
        items.add("20일\n 수")
        items.add("21일\n 목")
        items.add("22일\n 금")
        val items2:ArrayList<String> = ArrayList()
        items2.add("축구")
        items2.add("농구")
        items2.add("풋살")



        adapter = TabItemRecyclerViewAdapter(items)
        adapter2 = TabItemRecyclerViewAdapter(items2)

        binding.recyclerView.adapter = adapter
        binding.recyclerView2.adapter = adapter2



    }
    fun datachange(title:String,num:Int) {
        datas.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data1 = snapshot.child(num.toString()+title).child("data1")
                val temp1 = MatchData(data1.child("time").value.toString(),R.drawable.flag,data1.child("group").value.toString(),data1.child("team").value.toString(),
                    "11:11",false)
                item1.add(temp1)
                val data2 = snapshot.child(num.toString()+title).child("data2")
                val temp2 = MatchData(data2.child("time").value.toString(),R.drawable.flag,data2.child("group").value.toString(),data2.child("team").value.toString(),
                    "11:11",false)
                item2.add(temp2)
                val data3 = snapshot.child(num.toString()+title).child("data3")
                val temp3 = MatchData(data3.child("time").value.toString(),R.drawable.flag,data3.child("group").value.toString(),data3.child("team").value.toString(),
                    "change",false)

                item3.add(temp3)
                datass.add(item1)
                datass.add(item2)
                datass.add(item3)
                data.add(datass)


                adapter4.notifyDataSetChanged()



            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("error","error")

            }
        })
    }




}