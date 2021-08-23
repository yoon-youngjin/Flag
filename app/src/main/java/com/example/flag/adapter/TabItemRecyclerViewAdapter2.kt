package com.example.flag.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.flag.R

class TabItemRecyclerViewAdapter2(val data: ArrayList<String>) : RecyclerView.Adapter<TabItemRecyclerViewAdapter2.ViewHolder>() {
    private var mSelectedItem = -1



    interface OnItemClickListener {
        fun OnItemClick(holder: ViewHolder, view: View)
    }
    var itemClickListener: OnItemClickListener? = null


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: ToggleButton = view.findViewById(R.id.tabicon)

        fun bind_data(holder: ViewHolder, position: Int, selectedPosition:Int) {
            val date = holder.idView

            date.text = data[position]

            if(selectedPosition==-1&&position==0)
                holder.idView.isChecked = true
            else
                if(selectedPosition==position){
                    holder.idView.setTextColor(Color.BLACK)
                    holder.idView.isChecked = true
                }


                else{
                    holder.idView.setTextColor(Color.GRAY)
                    holder.idView.isChecked = false
                }


            idView.setOnClickListener {
                itemClickListener!!.OnItemClick(this,it)
                mSelectedItem = adapterPosition
                notifyDataSetChanged()
            }


        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tabitem2, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind_data(holder,position,mSelectedItem)

    }

    override fun getItemCount(): Int = data!!.size



}