package com.example.flag.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.R
import com.example.flag.adapter.TeamApplyAdapter
import com.example.flag.data.TeamData
import com.example.flag.databinding.ActivityStartApplyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class StartApplyActivity : AppCompatActivity() {
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("teamData")

    lateinit var myteam:String
    lateinit var auth:FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var binding:ActivityStartApplyBinding
    lateinit var event:String
    lateinit var adapter: TeamApplyAdapter
    val items:ArrayList<TeamData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartApplyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        init()
        initData()

    }

    override fun onBackPressed() {
        val intent = Intent(this, TeamActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
    private fun initData() {
        val uid = auth.currentUser?.uid.toString()
        database.child("users").child(uid).child("team").get().addOnSuccessListener {
            initEvent()
            for(ds in it.value.toString().split("/")) {

                myteam = ds
                mydatas.child(event).child(myteam).get().addOnSuccessListener {
                    val accept = it.child("accept").value
                    if(accept==true) return@addOnSuccessListener

                    val temp = TeamData(it.child("teamTitle").value.toString(),
                        it.child("area").value.toString(),
                        it.child("record").value.toString(),
                        it.child("teamImg").value.toString(),
                        it.child("member").value.toString(),
                        it.child("school").value.toString(),
                        it.child("uid").value.toString(),
                        it.child("accept").value.toString().toBoolean())
                    items.add(temp)

                    adapter = TeamApplyAdapter(items)
                    binding.recycle.adapter = adapter

                    adapter.itemClickListener = object :TeamApplyAdapter.OnItemClickListener {
                        override fun OnItemClick(
                            holder: TeamApplyAdapter.ViewHolder,
                            view: View,
                            position: Int,
                            data: ArrayList<TeamData>
                        ) {
                            //dlg
                            mydatas.child(event).child(holder.uid).child("accept").setValue(true)
                            adapter.removeItem(holder.adapterPosition)
                        }
                    }

                }

            }



        }



    }

    private fun initEvent() {
        event = intent.getStringExtra("event").toString()
        binding.matchtitle.text = event!!.substring(1)
        if(event=="b축구") {
            binding.matchImg2.setImageResource(R.drawable.football)
        }

        else if(event=="c농구") {
            binding.matchImg2.setImageResource(R.drawable.basketball)
        }
        else {
            binding.matchImg2.setImageResource(R.drawable.football)
        }
        Log.i("event",event)
    }

    fun init() {
        binding.recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

//        adapter.itemClickListener = object :TeamApplyAdapter.OnItemClickListener {
//            override fun OnItemClick(
//                holder: TeamApplyAdapter.ViewHolder,
//                view: View,
//                position: Int,
//                data: ArrayList<TeamData>
//            ) {
//                //dlg
//                mydatas.child(event).child(holder.uid).child("accept").setValue(true)
//                data.get(position).accept = false
//                adapter.notifyDataSetChanged()
//            }
//        }





    }
}