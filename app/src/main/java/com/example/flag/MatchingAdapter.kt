package com.example.flag

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchingAdapter(val data: ArrayList<ArrayList<MatchData>>) : RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun OnItemClick(holder: MatchingAdapter.ViewHolder, view: View)
    }
    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {



        val matchTitle : TextView = view.findViewById(R.id.matchtitle)
        val matchImg: ImageView = view.findViewById(R.id.matchImg)
        val allbtn : Button = view.findViewById(R.id.allbtn)
        val firstView : CustomView = view.findViewById(R.id.firstitem)
        val secondView : CustomView = view.findViewById(R.id.seconditem)
        val thirdView : CustomView = view.findViewById(R.id.thirditem)

        init {
            allbtn.setOnClickListener {
                itemClickListener!!.OnItemClick(this,it)
            }
        }

//        fun data_bind(holder:ViewHolder,position: Int) {
//
//            val item1 :ArrayList<MatchData> = data.get(position).get(0)
//
//            val item2 :ArrayList<MatchData> = data.get(position).get(1)
//            val item3 :ArrayList<MatchData> = data.get(position).get(2)
//
//            val mainImg = holder.firstView.mainImg
//            val mainImg2 = holder.secondView.mainImg
//            val mainImg3 = holder.thirdView.mainImg
//
//            Glide.with(itemView).load(item1[position].mainImg).into(mainImg)
//            Glide.with(itemView).load(item2[position].mainImg).into(mainImg2)
//            Glide.with(itemView).load(item3[position].mainImg).into(mainImg3)
//
//        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.matchitems, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val item1 : MatchData = data.get(position).get(0)

        val item2 :MatchData = data.get(position).get(1)
        val item3 :MatchData = data.get(position).get(2)

        holder.matchTitle.text = item1.matchTitle
        if(holder.matchTitle.text=="축구") {
            holder.matchImg.setImageResource(R.drawable.football)

        }
        if(holder.matchTitle.text=="농구") {
            holder.matchImg.setImageResource(R.drawable.basketball)

        }
        if(holder.matchTitle.text=="풋살") {
            holder.matchImg.setImageResource(R.drawable.football)

        }

        holder.firstView.timeTitle.text = item1.time

        holder.firstView.schoolTitle.text = item1.group
        if(item1.group=="건국대학교") {
            holder.firstView.mainImg.setImageResource(R.drawable.img33)
        }
        if(item1.group=="한양대학교") {
            holder.firstView.mainImg.setImageResource(R.drawable.img44)
        }
        if(item1.group=="동국대학교") {
            holder.firstView.mainImg.setImageResource(R.drawable.img55)
        }

        holder.firstView.teamTitle.text = item1.team
        holder.firstView.numberText.text = item1.num

        holder.secondView.timeTitle.text = item2.time
        holder.secondView.schoolTitle.text = item2.group
        if(item2.group=="건국대학교") {
            holder.secondView.mainImg.setImageResource(R.drawable.img33)
        }
        if(item2.group=="한양대학교") {
            holder.secondView.mainImg.setImageResource(R.drawable.img44)
        }
        if(item2.group=="동국대학교") {
            holder.secondView.mainImg.setImageResource(R.drawable.img55)
        }
        holder.secondView.teamTitle.text = item2.team
        holder.secondView.numberText.text = item2.num

        holder.thirdView.timeTitle.text = item3.time

        holder.thirdView.schoolTitle.text = item3.group
        if(item3.group=="건국대학교") {
            holder.thirdView.mainImg.setImageResource(R.drawable.img33)
        }
        if(item3.group=="한양대학교") {
            holder.thirdView.mainImg.setImageResource(R.drawable.img44)
        }
        if(item3.group=="동국대학교") {
            holder.thirdView.mainImg.setImageResource(R.drawable.img55)
        }
        holder.thirdView.teamTitle.text = item3.team
        holder.thirdView.numberText.text = item3.num
//
//        holder.data_bind(holder,position)

Log.i("size",data!!.size.toString())
        Log.i("size2",data!!.toString())


    }

    override fun getItemCount(): Int = data!!.size
}