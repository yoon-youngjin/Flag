package com.example.flag

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
    lateinit var dialogView:View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAllBinding.inflate(layoutInflater)
        dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.custom_dialog,binding.root,false)
        day = intent.getStringExtra("data").toString()
        event = intent.getStringExtra("data2").toString()
        area = intent.getStringExtra("data3").toString()

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

        mydatas.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child(day).child(event).child(area).children

                for(ds in temp) {
                    if(ds.key.toString()=="datanum") continue


                    items.add( MatchData(ds.key.toString(),ds.child("time").value.toString(),
                        ds.child("mainImg").value.toString(),
                        ds.child("group").value.toString(),
                        ds.child("team").value.toString(),
                        ds.child("num").value.toString(),
                        ds.child("accept").value.toString().toBoolean()))
                }
                adapter = AllMatchAdapter(items)
                binding.recycle.adapter = adapter

                adapter.itemClickListener = object :AllMatchAdapter.OnItemClickListener {

                    override fun OnItemClick(holder: AllMatchAdapter.ViewHolder, view: View,position:Int) {
                        if (dialogView.getParent() != null) {
                            (dialogView.getParent() as ViewGroup).removeView(dialogView)
                        }

                        val mBuilder = AlertDialog.Builder(this@AllActivity)
                            .setView(dialogView)
                            .setCancelable(false)
                            .show()

                        val groupname = dialogView.findViewById<TextView>(R.id.groupname)
                        val teamname = dialogView.findViewById<TextView>(R.id.teamname)
                        val time = dialogView.findViewById<TextView>(R.id.time)
                        val sportsname = dialogView.findViewById<TextView>(R.id.sportsname)

                        sportsname.text = binding.matchtitle.text.toString()
                        groupname.text = holder.group.text.toString()
                        teamname.text = holder.team.text.toString()
                        time.text = holder.time.text.toString()

                        val okButton = dialogView.findViewById<Button>(R.id.yesBtn)
                        val noButton = dialogView.findViewById<Button>(R.id.noBtn)

                        okButton.setOnClickListener {
                            var event = ""

                            if(sportsname.text == "축구") {
                                event = "1축구"
                            }
                            if(sportsname.text == "농구") {
                                event = "2농구"
                            }
                            if(sportsname.text == "풋살") {
                                event = "3풋살"
                            }

                            mydatas.child(day).child(event).child(area).child("data"+(position+1).toString()).child("accept").setValue(false)
                            holder.matchBtn.isClickable = false
                            holder.matchBtn.text = "마감"
                            holder.matchBtn.setTextColor(Color.GRAY)
                            holder.matchBtn.background = ContextCompat.getDrawable(this@AllActivity,R.drawable.radiobtn)
                            holder.matchBtn.backgroundTintList = ContextCompat.getColorStateList(this@AllActivity,R.color.lightgrey)



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
                }


            }
            override fun onCancelled(error: DatabaseError) {

            }
        })






    }
}