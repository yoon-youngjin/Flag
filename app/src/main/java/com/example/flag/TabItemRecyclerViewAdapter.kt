package com.example.flag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView

class TabItemRecyclerViewAdapter(val data: ArrayList<String>) : RecyclerView.Adapter<TabItemRecyclerViewAdapter.ViewHolder>() {




    interface OnItemClickListener {
        fun OnItemClick(holder: ViewHolder, view: View)
    }
    var itemClickListener: OnItemClickListener? = null



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: ToggleButton = view.findViewById(R.id.tabicon)

        init {
            idView.setOnClickListener {
                itemClickListener!!.OnItemClick(this,it)
            }
        }

//        init {
//            idView.setOnClickListener {
//                itemClickListener!!.OnItemClick(this,it)
//            }
//
//        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.tabitem, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position==0) {
            holder.idView.isChecked = true
        }

        holder.idView.text = data[position]

    }

    override fun getItemCount(): Int = data!!.size



}
