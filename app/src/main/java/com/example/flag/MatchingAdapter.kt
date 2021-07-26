package com.example.flag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchingAdapter (val data: ArrayList<ArrayList<MatchData>>) : RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {
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
        holder.matchTitle.text = "축구"
        val item1 :ArrayList<MatchData> = data.get(0)
        holder.firstView.timeTitle.text = item1[position].timeText
        holder.firstView.mainImg.setImageResource(item1[position].mainImg)
        holder.firstView.schoolTitle.text = item1[position].schoolText
        holder.firstView.teamTitle.text = item1[position].teamText
        holder.firstView.numberText.text = item1[position].numberText

        val item2 :ArrayList<MatchData> = data.get(1)
        holder.secondView.timeTitle.text = item2[position].timeText
        holder.firstView.mainImg.setImageResource(item2[position].mainImg)
        holder.secondView.schoolTitle.text = item2[position].schoolText
        holder.secondView.teamTitle.text = item2[position].teamText
        holder.secondView.numberText.text = item2[position].numberText

        val item3 :ArrayList<MatchData> = data.get(2)
        holder.thirdView.timeTitle.text = item3[position].timeText
        holder.firstView.mainImg.setImageResource(item3[position].mainImg)
        holder.thirdView.schoolTitle.text = item3[position].schoolText
        holder.thirdView.teamTitle.text = item3[position].teamText
        holder.thirdView.numberText.text = item3[position].numberText





    }

    override fun getItemCount(): Int = data!!.size
}