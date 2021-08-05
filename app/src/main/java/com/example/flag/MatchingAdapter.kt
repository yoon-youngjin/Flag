package com.example.flag

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchingAdapter(val data: ArrayList<ArrayList<ArrayList<MatchData>>>) : RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {
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

        Log.i("check","cchchchch")




        val item1 :ArrayList<MatchData> = data.get(position).get(0)

        val item2 :ArrayList<MatchData> = data.get(position).get(1)
        val item3 :ArrayList<MatchData> = data.get(position).get(2)
        holder.matchTitle.text = item1[position].matchTitle
        if(holder.matchTitle.text=="축구") {
            holder.matchImg.setImageResource(R.drawable.football)

        }
        if(holder.matchTitle.text=="농구") {
            holder.matchImg.setImageResource(R.drawable.basketball)

        }

        holder.firstView.timeTitle.text = item1[position].time

        holder.firstView.schoolTitle.text = item1[position].group
        if(item1[position].group=="건국대학교") {
            holder.firstView.mainImg.setImageResource(R.drawable.img33)
        }
        if(item1[position].group=="한양대학교") {
            holder.firstView.mainImg.setImageResource(R.drawable.img44)
        }
        if(item1[position].group=="동국대학교") {
            holder.firstView.mainImg.setImageResource(R.drawable.img55)
        }

        holder.firstView.teamTitle.text = item1[position].team
        holder.firstView.numberText.text = item1[position].num

        holder.secondView.timeTitle.text = item2[position].time
        holder.secondView.schoolTitle.text = item2[position].group
        if(item2[position].group=="건국대학교") {
            holder.secondView.mainImg.setImageResource(R.drawable.img33)
        }
        if(item2[position].group=="한양대학교") {
            holder.secondView.mainImg.setImageResource(R.drawable.img44)
        }
        if(item2[position].group=="동국대학교") {
            holder.secondView.mainImg.setImageResource(R.drawable.img55)
        }
        holder.secondView.teamTitle.text = item2[position].team
        holder.secondView.numberText.text = item2[position].num

        holder.thirdView.timeTitle.text = item3[position].time

        holder.thirdView.schoolTitle.text = item3[position].group
        if(item3[position].group=="건국대학교") {
            holder.thirdView.mainImg.setImageResource(R.drawable.img33)
        }
        if(item3[position].group=="한양대학교") {
            holder.thirdView.mainImg.setImageResource(R.drawable.img44)
        }
        if(item3[position].group=="동국대학교") {
            holder.thirdView.mainImg.setImageResource(R.drawable.img55)
        }
        holder.thirdView.teamTitle.text = item3[position].team
        holder.thirdView.numberText.text = item3[position].num
//
//        holder.data_bind(holder,position)




    }

    override fun getItemCount(): Int = data!!.size
}