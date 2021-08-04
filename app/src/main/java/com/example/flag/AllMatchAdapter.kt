package com.example.flag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AllMatchAdapter(val data: ArrayList<MatchData>) : RecyclerView.Adapter<AllMatchAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun OnItemClick(holder: ViewHolder, view: View)
    }
    var itemClickListener: OnItemClickListener? = null



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val time: TextView = view.findViewById(R.id.timeText)
        val img: ImageView = view.findViewById(R.id.mainImg)
        val group: TextView = view.findViewById(R.id.schoolTitle)
        val team: TextView = view.findViewById(R.id.teamTitle)
        val num: TextView = view.findViewById(R.id.numberText)


//        init {
//            idView.setOnClickListener {
//                itemClickListener!!.OnItemClick(this,it)
//            }
//        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.customview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.time.text = data[position].time
        holder.group.text = data[position].group
        holder.team.text = data[position].team
        holder.num.text = data[position].num

        if(holder.group.text == "건국대학교") {
            holder.img.setImageResource(R.drawable.img33)
        }
        if(holder.group.text == "한양대학교") {
            holder.img.setImageResource(R.drawable.img44)
        }
        if(holder.group.text == "동국대학교") {
            holder.img.setImageResource(R.drawable.img55)
        }


    }

    override fun getItemCount(): Int = data!!.size



}