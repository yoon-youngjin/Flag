package com.example.flag.view.activity

import android.app.AlertDialog
import android.content.Intent
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
import com.example.flag.R
import com.example.flag.adapter.AllMatchAdapter
import com.example.flag.data.MatchData
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

    override fun onBackPressed() {
        val intent = Intent(this, MatchActivity::class.java)
        intent.putExtra("matchevent","sports")
        startActivity(intent)
        super.onBackPressed()
    }

    fun init() {
        binding.matchtitle.text = event.substring(1)
        if(event.substring(1)=="??????") {
            binding.matchImg2.setImageResource(R.drawable.football)
        }

        else if(event.substring(1)=="??????") {
            binding.matchImg2.setImageResource(R.drawable.basketball)
        }
        else {
            binding.matchImg2.setImageResource(R.drawable.football)
        }
        binding.recycle.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)




        val items:ArrayList<MatchData> = ArrayList()

        mydatas.child(day).child(event).child(area).orderByChild("time").startAt(1000.0).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children) {
                    if(ds.key.toString()=="datanum") continue
                    items.add( MatchData(ds.key.toString(),ds.child("time").value.toString().toInt(),
                            ds.child("mainImg").value.toString(),
                            ds.child("group").value.toString(),
                            ds.child("group2").value.toString(),
                            ds.child("team").value.toString(),
                            ds.child("team2").value.toString(),
                            ds.child("num").value.toString(),
                            ds.child("accept").value.toString().toBoolean())
                    )
                }
                adapter = AllMatchAdapter(items)
                binding.recycle.adapter = adapter


                adapter.itemClickListener = object : AllMatchAdapter.OnItemClickListener {

                    override fun OnItemClick(holder: AllMatchAdapter.ViewHolder, view: View, position:Int, data:ArrayList<MatchData>) {
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

                            if(sportsname.text == "??????") {
                                event = "b??????"
                            }
                            if(sportsname.text == "??????") {
                                event = "c??????"
                            }
                            if(sportsname.text == "??????") {
                                event = "d??????"
                            }

                            mydatas.child(day).child(event).child(area).child("data"+(position+1).toString()).child("accept").setValue(false)
                            holder.matchBtn.text = "??????"
                            holder.matchBtn.setTextColor(Color.GRAY)
                            holder.matchBtn.background = ContextCompat.getDrawable(this@AllActivity,
                                R.drawable.radiobtn
                            )
                            holder.matchBtn.backgroundTintList = ContextCompat.getColorStateList(this@AllActivity,
                                R.color.lightgrey
                            )
//                            data.get(position).accept = false
//                            adapter.notifyDataSetChanged()



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
                TODO("Not yet implemented")
            }
        })


    }
}