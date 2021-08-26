package com.example.flag.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flag.R
import com.example.flag.data.TeamData

class TeamApplyAdapter(val data: ArrayList<TeamData>) : RecyclerView.Adapter<TeamApplyAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var view1:View

    interface OnItemClickListener {

        fun OnItemClick(holder: ViewHolder, view: View, position: Int, data: ArrayList<TeamData>)
    }
    var itemClickListener: OnItemClickListener? = null



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val layout : LinearLayout = view.findViewById(R.id.layout)
        val teamTitle: TextView = view.findViewById(R.id.teamT)
        val area: TextView = view.findViewById(R.id.area)
        val record: TextView = view.findViewById(R.id.score)
        val addBtn: Button = view.findViewById(R.id.addBtn2)
        val teamImg: ImageView = view.findViewById(R.id.teamImg)
        var uid:String = ""





        init {
            addBtn.setOnClickListener {
                itemClickListener!!.OnItemClick(this,it,adapterPosition,data)
            }
        }


    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context= parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.customview2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(data[position].teamImg).into(holder.teamImg)

        holder.uid = data[position].uid

        holder.teamTitle.text = data[position].teamTitle
        holder.area.text =data[position].area
        holder.record.text = data[position].record

        if(data[position].accept==true) {
            holder.addBtn.text = "모집중"
            holder.addBtn.background = ContextCompat.getDrawable(context,
                    R.drawable.radiobtn
            )
            holder.addBtn.backgroundTintList = ContextCompat.getColorStateList(context,
                    R.color.lightgrey)
        }
        if(data[position].accept==false) {
            holder.addBtn.text = "모집가능"
            holder.addBtn.background = ContextCompat.getDrawable(context,
                    R.drawable.radiobtn
            )
            holder.addBtn.backgroundTintList = ContextCompat.getColorStateList(context,
                    R.color.mainblue)
        }







    }

    override fun getItemCount(): Int = data!!.size



}