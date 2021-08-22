package com.example.flag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flag.databinding.FragmentSportsTeamBinding
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
    val uid = auth.currentUser?.uid.toString()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSportsTeamBinding.inflate(layoutInflater,container,false)
        init()
//        updataData()
        return binding.root

    }

//    private fun updataData() {
//        mydatas.child("a축구").child("VDVADWD3213f").setValue(TeamData("Ralo팀","서울특별시 송파구","0승 0무 0패","","","건국대학교","VDVADWD3213f",true))
//        mydatas.child("a축구").child("XCVWVSDV3213f").setValue(TeamData("Paka팀","서울특별시 영등포구","0승 0무 0패","","","한양대학교","XCVWVSDV3213f",true))
//        mydatas.child("a축구").child("ASVASVS1432d").setValue(TeamData("Dopa팀","서울특별시 강남구","0승 0무 0패","","","건국대학교","ASVASVS1432d",true))
//
//        mydatas.child("b농구").child("VADBADBE54352").setValue(TeamData("Ralo팀","서울특별시 송파구","0승 0무 0패","","","건국대학교","VADBADBE54352",true))
//        mydatas.child("b농구").child("VADBADBE5435556").setValue(TeamData("Paka팀","서울특별시 영등포구","0승 0무 0패","","","한양대학교","VADBADBE5435556",true))
//        mydatas.child("b농구").child("DSFFSVAQA123").setValue(TeamData("Dopa팀","서울특별시 강남구","0승 0무 0패","","","건국대학교","DSFFSVAQA123",true))
//
//        mydatas.child("c풋살").child("SVVXZCVQ1231").setValue(TeamData("Ralo팀","서울특별시 송파구","0승 0무 0패","","","건국대학교","SVVXZCVQ1231",true))
//        mydatas.child("c풋살").child("SVVXZCVQ123133").setValue(TeamData("Paka팀","서울특별시 영등포구","0승 0무 0패","","","한양대학교","SVVXZCVQ123133",true))
//        mydatas.child("c풋살").child("SVVXZCVQ123141").setValue(TeamData("Dopa팀","서울특별시 강남구","0승 0무 0패","","","건국대학교","SVVXZCVQ123141",true))
//    }

    fun init() {
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

                adapter.itemClickListener = object :TabItemRecyclerViewAdapter2.OnItemClickListener{
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
        Log.i("groupname",group)
        binding.teamRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        var item:String = ""
        if(data == "축구") item = "a축구"
        else if(data == "농구") item = "b농구"
        else if(data == "풋살") item = "c풋살"

        mydatas.child(item).orderByChild("school").equalTo(group).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for(ds in snapshot.children) {

                    val temp = TeamData(ds.child("teamTitle").value.toString(),
                        ds.child("area").value.toString(),
                        ds.child("record").value.toString(),
                        "","","",ds.child("uid").value.toString(),ds.child("accept").value.toString().toBoolean())
                    datas.add(temp)
                }
                if(check == false) {
                    adapter2 = TeamAdapter(datas)
                    binding.teamRecycler.adapter = adapter2
                }
                else {
                    adapter2.notifyDataSetChanged()
                }

                adapter2.itemClickListener = object :TeamAdapter.OnItemClickListener {
                    override fun OnItemClick(
                        holder: TeamAdapter.ViewHolder,
                        view: View,
                        position: Int,
                        data: ArrayList<TeamData>
                    ) {
                        goApply(item,snapshot.child(holder.uid).key.toString())

                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

    private fun goApply(item: String, key: String) {
        mydatas.child(item).child(key).child("member").setValue(uid)
        myusers.child(uid).child("team").setValue(key)
    }

}