package com.example.flag.view.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.R
import com.example.flag.adapter.TabItemRecyclerViewAdapter2
import com.example.flag.adapter.TeamAdapter
import com.example.flag.data.TeamData
import com.example.flag.databinding.FragmentSportsTeamBinding
import com.example.flag.view.activity.addTeamActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SportsTeamFragment : Fragment() {
    lateinit var binding: FragmentSportsTeamBinding
    lateinit var adapter2: TeamAdapter
    lateinit var adapter: TabItemRecyclerViewAdapter2
    var group: String ="건국대학교"
    var check :Boolean = false
    var datas = ArrayList<TeamData>()
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("teamData")
    val myusers = rdb.getReference("users")
    val auth = FirebaseAuth.getInstance()
    val database = Firebase.database.reference
    lateinit var dialogView:View
    val uid = auth.currentUser?.uid.toString()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSportsTeamBinding.inflate(layoutInflater,container,false)
        dialogView = inflater.inflate(R.layout.custom_dialog2, container, false)
        init()
//        updataData()
        return binding.root

    }

//    private fun updataData() {
//        var item = "b축구"
//        for(i in 1..3) {
//            if(i==1)  item = "b축구"
//            else if(i==2) item = "c농구"
//            else if(i==3) item = "d풋살"
//
//            val uid = database.push().key.toString()
//            mydatas.child(item).child(uid).setValue(TeamData("Ralo팀", "서울특별시 송파구", "0승 0무 0패", "", "", "건국대학교", uid, true))
//            val uid2 = database.push().key.toString()
//            mydatas.child(item).child(uid2).setValue(TeamData("Paka팀", "서울특별시 영등포구", "0승 0무 0패", "", "", "한양대학교", uid2, true))
//            val uid3 = database.push().key.toString()
//            mydatas.child(item).child(uid3).setValue(TeamData("Dopa팀", "서울특별시 강남구", "0승 0무 0패", "", "", "건국대학교", uid3, true))
//
//        }
//        }

    fun init() {

        binding.openBtn2.setOnClickListener {
            val intent = Intent(context, addTeamActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        database.child("users").child(uid).get().addOnSuccessListener {
            group = it.child("school").value.toString()
            dataChange("축구", check)
            check = true
            binding.textView14.text = group
        }

        binding.recyclerView2.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        val items: ArrayList<String> = ArrayList()
        mydatas.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val key = ds.key!!.substring(1)
                    items.add(key)
                }
                adapter = TabItemRecyclerViewAdapter2(items)
                binding.recyclerView2.adapter = adapter

                adapter.itemClickListener = object :
                    TabItemRecyclerViewAdapter2.OnItemClickListener {
                    override fun OnItemClick(
                        holder: TabItemRecyclerViewAdapter2.ViewHolder,
                        view: View
                    ) {
                        datas.clear()
                        dataChange(holder.idView.text.toString(),check)
                    }
                }
            }


            override fun onCancelled(error: DatabaseError) {
            }
        })

    }

    private fun dataChange(data: String, check: Boolean) {

        binding.teamRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        var item: String = ""
        if (data == "축구") item = "b축구"
        else if (data == "농구") item = "c농구"
        else if (data == "풋살") item = "d풋살"



            mydatas.child(item).orderByChild("school").equalTo(group).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    datas.clear()

                    for (ds in snapshot.children) {

                        val temp = TeamData(ds.child("teamTitle").value.toString(),
                                ds.child("area").value.toString(),
                                ds.child("record").value.toString(),
                                "", "", group, ds.child("uid").value.toString(), ds.child("accept").value.toString().toBoolean())
                        datas.add(temp)
                    }


                    if (check == false) {
                        adapter2 = TeamAdapter(datas)
                        binding.teamRecycler.adapter = adapter2
                    } else {
                        adapter2.notifyDataSetChanged()
                    }

                    adapter2.itemClickListener = object : TeamAdapter.OnItemClickListener {
                        override fun OnItemClick(
                            holder: TeamAdapter.ViewHolder,
                            view: View,
                            position: Int,
                            data: ArrayList<TeamData>
                        ) {
                            checkSamePeople(item,holder,snapshot)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })


    }

    private fun checkSamePeople(item: String, holder: TeamAdapter.ViewHolder, snapshot: DataSnapshot) {
        var member: String? = null

        mydatas.child(item).child(holder.uid).get().addOnSuccessListener {
            member = it.child("member").value.toString()
            if(member==uid) {
                Toast.makeText(context,"이미 가입한 멤버입니다.",Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }
            else showDlg(holder,item,snapshot)
        }


    }



    private fun showDlg(item: TeamAdapter.ViewHolder, item1: String, snapshot: DataSnapshot) {
        val mBuilder = AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(false)
                .show()

        val teamname = dialogView.findViewById<TextView>(R.id.teamname)
        val area = dialogView.findViewById<TextView>(R.id.area)
        val score = dialogView.findViewById<TextView>(R.id.score)

        val okButton = dialogView.findViewById<Button>(R.id.yesBtn)
        val noButton = dialogView.findViewById<Button>(R.id.noBtn)

        teamname.text = item.teamTitle.text
        area.text = item.area.text
        score.text = item.record.text


        okButton.setOnClickListener {
            goApply(item1, snapshot.child(item.uid).key.toString())

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

    private fun goApply(item: String, key: String) {
        mydatas.child(item).child(key).child("member").setValue(uid)
        myusers.child(uid).child("team").setValue(key)
    }

}