package com.example.flag.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flag.data.MatchData
import com.example.flag.R

class AllMatchAdapter(val data: ArrayList<MatchData>) : RecyclerView.Adapter<AllMatchAdapter.ViewHolder>() {
    lateinit var context: Context
    interface OnItemClickListener {



        fun OnItemClick(holder: ViewHolder, view: View, position: Int, data: ArrayList<MatchData>)
    }
    var itemClickListener: OnItemClickListener? = null



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val time: TextView = view.findViewById(R.id.timeText)
        val img: ImageView = view.findViewById(R.id.mainImg)
        val group: TextView = view.findViewById(R.id.eventTitle)
        val team: TextView = view.findViewById(R.id.num)
        val matchBtn: Button = view.findViewById(R.id.matchingBtn)
        val num: TextView = view.findViewById(R.id.numberText)


        init {
            matchBtn.setOnClickListener {
                itemClickListener!!.OnItemClick(this,it,adapterPosition,data)
            }
        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context= parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.customview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.time.text = data[position].time.toString()
        holder.group.text = data[position].group
        holder.team.text = data[position].team
        holder.num.text = data[position].num
        holder.matchBtn.isClickable = data[position].accept
        if(data[position].accept==false) {
            holder.matchBtn.text = "마감"
            holder.matchBtn.setTextColor(Color.GRAY)
            holder.matchBtn.background = ContextCompat.getDrawable(context, R.drawable.radiobtn)
            holder.matchBtn.backgroundTintList = ContextCompat.getColorStateList(context,
                R.color.lightgrey
            )

        }
        else {
            holder.matchBtn.text = "신청가능"
        }

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