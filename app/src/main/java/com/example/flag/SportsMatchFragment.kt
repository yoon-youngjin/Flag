package com.example.flag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.databinding.FragmentSportsMatchBinding


class SportsMatchFragment : Fragment() {
    lateinit var adapter: TabItemRecyclerViewAdapter
    lateinit var adapter2: TabItemRecyclerViewAdapter
    lateinit var adapter3: TabItemRecyclerViewAdapter
    lateinit var adapter4 : MatchingAdapter
    lateinit var binding:FragmentSportsMatchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportsMatchBinding.inflate(layoutInflater,container,false)
        init()
        return binding.root
         }

    private fun init() {

        binding.recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView2.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerView3.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.matchRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

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

        val items3:ArrayList<String> = ArrayList()
        items3.add("서울")
        items3.add("경기")
        items3.add("인천")
        items3.add("대전")
        items3.add("충북")
        items3.add("대구/경산")

        val data:ArrayList<ArrayList<MatchData>> = ArrayList()
        val item1:ArrayList<MatchData> = ArrayList()
        val item2:ArrayList<MatchData> = ArrayList()
        val item3:ArrayList<MatchData> = ArrayList()
        item1.add(MatchData("10:00",R.drawable.house,"건국대학교","Ralo팀","11vs11"))
        item2.add(MatchData("10:00",R.drawable.house,"건국대학교","Ralo팀","11vs11"))
        item3.add(MatchData("10:00",R.drawable.house,"","",""))

        data.add(item1)
        data.add(item2)
        data.add(item3)

        Log.i("data1",data.toString())



        adapter = TabItemRecyclerViewAdapter(items)
        adapter2 = TabItemRecyclerViewAdapter(items2)
        adapter3 = TabItemRecyclerViewAdapter(items3)
        adapter4 = MatchingAdapter(data)
        binding.recyclerView.adapter = adapter
        binding.recyclerView2.adapter = adapter2
        binding.recyclerView3.adapter = adapter3
        binding.matchRecycler.adapter = adapter4

//        adapter.itemClickListener = object : TabItemRecyclerViewAdapter.OnItemClickListener {
//            override fun OnItemClick(holder: TabItemRecyclerViewAdapter.ViewHolder, view: View) {
//                holder.idView.setBackgroundColor(Color.LTGRAY)
//            }
//        }

    }


}