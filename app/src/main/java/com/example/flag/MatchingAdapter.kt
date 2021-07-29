package com.example.flag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchingAdapter(val data: ArrayList<ArrayList<ArrayList<MatchData>>>) : RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val matchTitle : TextView = view.findViewById(R.id.matchtitle)
        val firstView : CustomView = view.findViewById(R.id.firstitem)
        val secondView : CustomView = view.findViewById(R.id.seconditem)
        val thirdView : CustomView = view.findViewById(R.id.thirditem)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.matchitems, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val item1 :ArrayList<MatchData> = data.get(position).get(0)

        val item2 :ArrayList<MatchData> = data.get(position).get(1)
        val item3 :ArrayList<MatchData> = data.get(position).get(2)


        holder.firstView.timeTitle.text = item1[position].time
        holder.firstView.mainImg.setImageResource(item1[position].mainImg)
        holder.firstView.schoolTitle.text = item1[position].group
        holder.firstView.teamTitle.text = item1[position].team
        holder.firstView.numberText.text = item1[position].num

        holder.secondView.timeTitle.text = item2[position].time
        holder.secondView.mainImg.setImageResource(item2[position].mainImg)
        holder.secondView.schoolTitle.text = item2[position].group
        holder.secondView.teamTitle.text = item2[position].team
        holder.secondView.numberText.text = item2[position].num

        holder.thirdView.timeTitle.text = item3[position].time
        holder.thirdView.mainImg.setImageResource(item3[position].mainImg)
        holder.thirdView.schoolTitle.text = item3[position].group
        holder.thirdView.teamTitle.text = item3[position].team
        holder.thirdView.numberText.text = item3[position].num





    }

    override fun getItemCount(): Int = data!!.size
}