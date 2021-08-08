package com.example.flag

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivityRoomBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RoomActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoomBinding
    lateinit var adapter: ArrayAdapter<String>
    var datanum:Int = 0
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("sportsData")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
//        updateData()
    }


    fun initData() {

        var day2 = 1
        var event = "1축구"
        var area2 = "1서울"
        var time2 = ""
        var num = ""
        var group2 = ""
        var team2 = binding.teamEditText.text


        val area = resources.getStringArray(R.array.area_name)
        val adapter4 = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,area)

        binding.spinner4.adapter = adapter4
        binding.spinner4.setSelection(0)

        binding.spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> area2 = (position+1).toString() + "서울"
                    1 -> area2 = (position+1).toString() + "경기"
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


            val group = resources.getStringArray(R.array.group_name)

        adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,group)
        binding.autoText.setAdapter(adapter)

        binding.autoText.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s!!.length>=1) {
                    binding.deleteBtn2.visibility = View.VISIBLE
                }
                else {
                    binding.deleteBtn2.visibility = View.INVISIBLE
                }
                group2 = s.toString()
                Log.i("s",s.toString())

            }

            override fun afterTextChanged(s: Editable?) {


            }
        })

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
        binding.deleteBtn2.setOnClickListener {
            binding.autoText.text.clear()
        }

        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,ArrayList<String>())

        adapter.add("축구")
        adapter.add("농구")
        adapter.add("풋살")

        binding.spinner.adapter = adapter
        binding.spinner.setSelection(0)



        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position) {
                    0 -> {
                        event = (position+1).toString() +"축구"
                        num = "11:11"


                    }
                    1 -> {
                        event = (position+1).toString() +"농구"
                        num = "5:5"
                    }
                    2 -> {
                        event = (position+1).toString() +"풋살"
                        num = "5:5"
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        val day = resources.getStringArray(R.array.day_array)
        val adapter2 = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,day)

        binding.spinner2.adapter = adapter2
        binding.spinner2.setSelection(0)

        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                day2 = position + 1

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



        val time = resources.getStringArray(R.array.time_array)
        val adapter3 = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,time)

        binding.spinner3.adapter = adapter3
        binding.spinner3.setSelection(0)

        Log.i("get",binding.spinner3.selectedItem.toString())

        binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                time2 = binding.spinner3.selectedItem.toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }



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

        val data =
            mydatas.child(day2.toString() + day2.toString() + "일").child(event).child(area2)


        data.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                datanum = snapshot.child("datanum").value.toString().toInt() + 1
                Log.i("datanum",datanum.toString())

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.btn.setOnClickListener {

            if (binding.autoText.text.isEmpty() || binding.teamEditText.text.isEmpty()) {
                Toast.makeText(this, "전부 입력하세요.", Toast.LENGTH_SHORT).show()

            } else {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("방을 생성하시겠습니까?")
                builder.setPositiveButton("네") { _, _ ->
                    data.child("data" + datanum.toString()).setValue(
                            MatchData(
                                    event.substring(1),
                                    time2,
                                    "",
                                    group2.toString(),
                                    team2.toString(),
                                    num,
                                    false
                            )
                    )
                    data.child("datanum").setValue(datanum)
                    val intent = Intent(this,MatchActivity::class.java)
                    intent.putExtra("matchevent","sports")
                    startActivity(intent)

                }
                builder.setNegativeButton("아니요") { _, _ ->
                }.show()




            }
        }



    }


}