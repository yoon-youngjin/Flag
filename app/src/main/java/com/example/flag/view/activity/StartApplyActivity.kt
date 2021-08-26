package com.example.flag.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    lateinit var dialogView:View

    var auth:FirebaseAuth =FirebaseAuth.getInstance()
    var database: DatabaseReference = Firebase.database.reference
    lateinit var binding:ActivityStartApplyBinding
    lateinit var adapter: TeamApplyAdapter
    var items:ArrayList<TeamData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityStartApplyBinding.inflate(layoutInflater)
        dialogView = LayoutInflater.from(applicationContext).inflate(R.layout.custom_dialog2,binding.root,false)
        setContentView(binding.root)
        var event:String = intent.getStringExtra("event").toString()
        initRecyclerView(binding!!.recycle)
        initData(event)
        init(event)


    }

    private fun initRecyclerView(recycle: RecyclerView) {
        recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = TeamApplyAdapter(items)
        recycle.adapter = adapter

    }

    override fun onBackPressed() {
        val intent = Intent(this, TeamActivity::class.java)
        startActivity(intent)
        finish()
        super.onBackPressed()
    }
    private fun initData(event:String) {

        val uid = auth.currentUser?.uid.toString()
        database.child("users").child(uid).child("team").child(event).get().addOnSuccessListener {

            if(it.value.toString()=="null") return@addOnSuccessListener
            initEvent(event)
            for(ds in it.value.toString().split("/")) {

                mydatas.child(event).child(ds).get().addOnSuccessListener {

                    val temp = TeamData(it.child("teamTitle").value.toString(),
                        it.child("area").value.toString(),
                        it.child("record").value.toString(),
                        it.child("teamImg").value.toString(),
                        it.child("member").value.toString(),
                        it.child("school").value.toString(),
                        it.child("uid").value.toString(),
                        it.child("accept").value.toString().toBoolean())
                    items.add(temp)

                    adapter.notifyDataSetChanged()

                }

            }
        }



    }

    private fun showDlg(item: TeamApplyAdapter.ViewHolder,data: ArrayList<TeamData>,position:Int,check:Boolean,event:String){
        if (dialogView.getParent() != null) {
            (dialogView.getParent() as ViewGroup).removeView(dialogView)
        }
        val mBuilder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .show()

        val teamname = dialogView.findViewById<TextView>(R.id.teamname)
        val area = dialogView.findViewById<TextView>(R.id.area)
        val score = dialogView.findViewById<TextView>(R.id.score)
        val editText = dialogView.findViewById<TextView>(R.id.myEdit)
        if(check==false) {
            editText.text = "팀원모집을 시작하시겠습니까?"
        }
        else {
            editText.text = "팀원모집을 그만하시겠습니까?"
        }

        val okButton = dialogView.findViewById<Button>(R.id.yesBtn)
        val noButton = dialogView.findViewById<Button>(R.id.noBtn)

        teamname.text = item.teamTitle.text
        area.text = item.area.text
        score.text = item.record.text


        okButton.setOnClickListener {
            if(check==false) {
                mydatas.child(event).child(item.uid).child("accept").setValue(true)
                data.get(position).accept= true
                adapter.notifyDataSetChanged()
            }
            else {
                mydatas.child(event).child(item.uid).child("accept").setValue(false)
                data.get(position).accept= false
                adapter.notifyDataSetChanged()
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

    private fun initEvent(event: String) {

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

    }

    fun init(event: String) {
        adapter.itemClickListener = object :TeamApplyAdapter.OnItemClickListener {
            override fun OnItemClick(
                holder: TeamApplyAdapter.ViewHolder,
                view: View,
                position: Int,
                data: ArrayList<TeamData>
            ) {
                if(holder.addBtn.text.equals("모집가능")) {
                    showDlg(holder,data,position,false,event)
                }
                else {
                    showDlg(holder,data,position,true,event)
                }

            }
        }
    }
}