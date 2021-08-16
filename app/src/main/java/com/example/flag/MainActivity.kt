package com.example.flag

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase




class MainActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        val uid = auth.currentUser?.uid.toString()

        getUserData(uid)
//        auth.uid?.let { createMatch("21.08.14", "08:00", "futsal", "건국대학교", it, "") }

        getMatchData()
    }

    // 파이어베이스 사용자 데이터 읽기
    private fun getUserData(uid: String) {
        database.child("users").child(uid).get().addOnSuccessListener {
            Log.d("getUserData", "Got value ${it.value}")
            val user = it.getValue<User>()
        }.addOnFailureListener {
            Log.e("getUserData", it.toString())
        }
    }


    // 사용자 데이터 변경
    private fun setUserData(uid: String, field: String, newData: String) {
        database.child("users").child(uid).child(field).setValue(newData).addOnCompleteListener() {
            Log.d("setData", "success")
        }
    }

    // 경기 데이터 생성
//    private fun createMatch(
//        date: String,
//        time: String,
//        event: String,
//        group: String,
//        homeUid: String,
//        awayUid: String
//    ) {
//        val newRef = database.push();
//        val matchId = newRef.key
//        val match = FieldClassification.Match(matchId, date, time, event, group, homeUid, awayUid)
//
//        matchId?.let { database.child("match").child(it).setValue(match) }
//    }

    private fun getMatchData() {
        database.child("match/date").get().addOnSuccessListener {
            Log.i("getMatchData", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("getMatchData", "Error getting data", it)
        }
    }
}