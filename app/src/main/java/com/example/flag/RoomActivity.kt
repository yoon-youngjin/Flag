package com.example.flag

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivityRoomBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RoomActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoomBinding
    lateinit var adapter: ArrayAdapter<String>
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
//        updateData()
    }

//    private fun updateData() {
//        var day = 0
//        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                Log.i("day123","check")
//
//                day = position + 1
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }
//        Log.i("day",day.toString())
////        mydatas.child(day)
//
//    }

    fun initData() {
        var datanum = ""
        var day2 = 0
        var event = ""
        var area2 = ""
        var time2 = "15:00" // update
        var num = "11:11" //update
        var group2 = binding.autoText.text
        var team2 = binding.teamEditText.text





        val area = resources.getStringArray(R.array.area_name)
        val adapter4 = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,area)

        binding.spinner4.adapter = adapter4

        binding.spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> area2 = (position+1).toString() + "서울"
                    1 -> area2 = (position+1).toString() + "경기"
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


            val group = resources.getStringArray(R.array.group_name)
        adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,group)
        binding.autoText.setAdapter(adapter)

        binding.teamEditText.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length>=1) {
                    binding.deleteBtn.visibility = View.VISIBLE
                }
                else binding.deleteBtn.visibility = View.INVISIBLE

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.deleteBtn.setOnClickListener {
            binding.teamEditText.text.clear()
        }

        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,ArrayList<String>())

        adapter.add("축구")
        adapter.add("농구")
        adapter.add("풋살")

        binding.spinner.adapter = adapter



        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position) {
                    0 -> {
                        event = (position+1).toString() +"축구"
                    }
                    1 -> {
                        event = (position+1).toString() +"농구"
                    }
                    2 -> {
                        event = (position+1).toString() +"풋살"
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        val day = resources.getStringArray(R.array.day_array)
        val adapter2 = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,day)

        binding.spinner2.adapter = adapter2

        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                day2 = position + 1

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        val time = resources.getStringArray(R.array.time_array)
        val adapter3 = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,time)

        binding.spinner3.adapter = adapter3



//        binding.areaText.addTextChangedListener(object:TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                if(s!!.length>=1) {
//                    binding.deleteBtn2.visibility = View.VISIBLE
//                }
//                else binding.deleteBtn2.visibility = View.INVISIBLE
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })

//        binding.deleteBtn2.setOnClickListener {
//            binding.teamEditText.text.clear()
//        }

        binding.btn.setOnClickListener {
            val data = mydatas.child(day2.toString()+day2.toString()+"일").child(event).child(area2)
            data.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
//                    datanum = snapshot.child("datanum").value.toString().toInt() + 1
                    datanum = "data5"
                    data.child(datanum).setValue(MatchData(event.substring(1),time2,"",group2.toString(),team2.toString(),num,false)
                    )
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }






    }
}