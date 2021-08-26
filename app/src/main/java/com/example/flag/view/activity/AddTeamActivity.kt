package com.example.flag.view.activity

import android.R
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.data.TeamData
import com.example.flag.databinding.ActivityAddTeamBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.ArrayList

class addTeamActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    var rdb: FirebaseDatabase = FirebaseDatabase.getInstance()
    var mydatas = rdb.getReference("teamData")
    var myuser = rdb.getReference("users")
    val database = Firebase.database.reference
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var storageReference: StorageReference? = null

    var group: String ="건국대학교"
    lateinit var event: String
    lateinit var area: String
    lateinit var team:String
    val uid = auth.currentUser?.uid.toString()
    lateinit var binding:ActivityAddTeamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTeamBinding.inflate(layoutInflater)
        storageReference = FirebaseStorage.getInstance().reference
        setContentView(binding.root)

        init()
        initsetData()
    }
    override fun onBackPressed() {
        val intent = Intent(this, TeamActivity::class.java)
        intent.putExtra("teamevent", "sports")
        startActivity(intent)
        super.onBackPressed()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data
            binding!!.addteamImage.setImageURI(filePath)
        }
    }
    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    private fun initsetData() {

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        event = "b축구"
                    }
                    1 -> {
                        event = "c농구"
                    }
                    2 -> {
                        event = "d풋살"
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.area.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length >= 1) {
                    binding.deleteBtn2.visibility = View.VISIBLE
                } else {
                    binding.deleteBtn2.visibility = View.INVISIBLE
                }
                area = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.teamEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length >= 1) {
                    binding.deleteBtn.visibility = View.VISIBLE
                } else {
                    binding.deleteBtn.visibility = View.INVISIBLE
                }
                team = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.deleteBtn.setOnClickListener {
            binding.teamEditText.text.clear()
        }
        binding.deleteBtn2.setOnClickListener {
            binding.area.text.clear()
        }
        binding.chooseBtn.setOnClickListener {
            launchGallery()
        }
        binding.btn.setOnClickListener {
            setData( event, area, team)
        }

    }

    private fun setData(event: String, area: String, team: String) {
        val teamuid = database.push().key.toString()
        val data =
            mydatas.child(event).child(teamuid)
        val user = myuser.child(uid).child("team").child(event)

        if(area.isEmpty() || team.isEmpty()) {
            Toast.makeText(this, "전부 입력하세요.", Toast.LENGTH_SHORT).show()
        }
        else {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("팀을 생성하시겠습니까?")
            builder.setPositiveButton("네") { _, _ ->

                uploadData(data,teamuid)

                user.get().addOnSuccessListener {
                    if(it.value.toString()=="null") {
                        user.setValue(teamuid)
                    }
                    else {
                        val temp = it.value.toString() + "/" + teamuid
                        user.setValue(temp)
                    }
                }

                val intent = Intent(this, TeamActivity::class.java)
                intent.putExtra("teamevent", "sports")
                startActivity(intent)

            }
            builder.setNegativeButton("아니요") { _, _ ->
            }.show()
        }

    }

    private fun uploadData(data: DatabaseReference, teamuid: String) {
        if (filePath != null) {
            val na = UUID.randomUUID().toString()
            val ref = storageReference?.child("uploads/" + na)
            val uploadTask = ref?.putFile(filePath!!)?.addOnSuccessListener {

                storageReference?.child("uploads/" + na)?.downloadUrl?.addOnSuccessListener {
                    data.setValue(
                            TeamData(team,area,"0승 0무 0패",it.toString(),uid,group,teamuid,false)
                    )
                }?.addOnFailureListener {
                    Log.d("acees token", "acees token get fail")
                }
            }
        }
    }

    fun init() {

        database.child("users").child(uid).get().addOnSuccessListener {
            group = it.child("school").value.toString()
            binding.schoolText.text = group
        }

        val adapter = ArrayAdapter<String>(this, R.layout.simple_dropdown_item_1line, ArrayList<String>())

        adapter.add("축구")
        adapter.add("농구")
        adapter.add("풋살")

        binding.spinner.adapter = adapter

    }
}