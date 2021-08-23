package com.example.flag.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flag.CustomView
import com.example.flag.R
import com.example.flag.data.MatchData

class MatchingAdapter(var data: ArrayList<ArrayList<MatchData>>) : RecyclerView.Adapter<MatchingAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun OnItemClick(holder: ViewHolder, view: View, data: ArrayList<ArrayList<MatchData>>, position: Int)
    }
    lateinit var context:Context
    var itemClickListener: OnItemClickListener? = null
    var itemClickListener2: OnItemClickListener? = null
    var itemClickListener3: OnItemClickListener? = null
    var itemClickListener4: OnItemClickListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val mainLayout : LinearLayout = view.findViewById(R.id.Layout)
        val matchTitle : TextView = view.findViewById(R.id.matchtitle)
        val matchImg: ImageView = view.findViewById(R.id.matchImg)
        val allbtn : Button = view.findViewById(R.id.allbtn)
        val firstView : CustomView = view.findViewById(R.id.firstitem)
        val secondView : CustomView = view.findViewById(R.id.seconditem)
        val thirdView : CustomView = view.findViewById(R.id.thirditem)

        init {
            allbtn.setOnClickListener {
                itemClickListener!!.OnItemClick(this,it,data,adapterPosition)
            }
            firstView.matchBtn.setOnClickListener {
                itemClickListener2!!.OnItemClick(this,it,data,adapterPosition)
            }

            secondView.matchBtn.setOnClickListener {
                itemClickListener3!!.OnItemClick(this,it,data,adapterPosition)
            }
            thirdView.matchBtn.setOnClickListener {
                itemClickListener4!!.OnItemClick(this,it,data,adapterPosition)
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
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.matchitems, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.firstView.visibility = View.GONE
        holder.secondView.visibility = View.GONE
        holder.thirdView.visibility = View.GONE


        dataSet(holder,data.get(position).size,position)



    }

    private fun dataSet(holder: ViewHolder, i: Int, position: Int) {
        if(i!=0) {

            if (i >= 1) {
                holder.firstView.visibility = View.VISIBLE
                val item1: MatchData = data.get(position).get(0)
                holder.matchTitle.text = item1.matchTitle
                if (holder.matchTitle.text == "축구") {
                    holder.matchImg.setImageResource(R.drawable.football)
                }
                if (holder.matchTitle.text == "농구") {
                    holder.matchImg.setImageResource(R.drawable.basketball)

                }
                if (holder.matchTitle.text == "풋살") {
                    holder.matchImg.setImageResource(R.drawable.football)

                }

                if(item1.time.toString().length==3) {
                    holder.firstView.timeTitle.text = item1.time.toString().substring(0,1) +":" +item1.time.toString().substring(1,3)
                }
                else {
                    holder.firstView.timeTitle.text = item1.time.toString().substring(0,2) +":" +item1.time.toString().substring(2,4)
                }

                holder.firstView.schoolTitle.text = item1.group
                if (item1.group == "건국대학교") {
                    holder.firstView.mainImg.setImageResource(R.drawable.img33)
                }
                if (item1.group == "한양대학교") {
                    holder.firstView.mainImg.setImageResource(R.drawable.img44)
                }
                if (item1.group == "동국대학교") {
                    holder.firstView.mainImg.setImageResource(R.drawable.img55)
                }

                holder.firstView.teamTitle.text = item1.team
                holder.firstView.numberText.text = item1.num
                holder.firstView.matchBtn.isClickable = item1.accept
                if (item1.accept == false) {
                    holder.firstView.matchBtn.text = "마감"
                    holder.firstView.matchBtn.setTextColor(Color.GRAY)
                    holder.firstView.matchBtn.background = ContextCompat.getDrawable(context,
                        R.drawable.radiobtn
                    )
                    holder.firstView.matchBtn.backgroundTintList = ContextCompat.getColorStateList(context,
                        R.color.lightgrey
                    )

                } else {
                    holder.firstView.matchBtn.text = "신청가능"
                }
            }
            if (i >= 2) {
                holder.secondView.visibility = View.VISIBLE
                val item2: MatchData = data.get(position).get(1)
                if(item2.time.toString().length==3) {
                    holder.secondView.timeTitle.text = item2.time.toString().substring(0,1) +":" +item2.time.toString().substring(1,3)
                }
                else {
                    holder.secondView.timeTitle.text = item2.time.toString().substring(0,2) +":" +item2.time.toString().substring(2,4)
                }

                holder.secondView.schoolTitle.text = item2.group
                if (item2.group == "건국대학교") {
                    holder.secondView.mainImg.setImageResource(R.drawable.img33)
                }
                if (item2.group == "한양대학교") {
                    holder.secondView.mainImg.setImageResource(R.drawable.img44)
                }
                if (item2.group == "동국대학교") {
                    holder.secondView.mainImg.setImageResource(R.drawable.img55)
                }
                holder.secondView.teamTitle.text = item2.team
                holder.secondView.numberText.text = item2.num
                holder.secondView.matchBtn.isClickable = item2.accept

                if (item2.accept == false) {
                    holder.secondView.matchBtn.text = "마감"
                    holder.secondView.matchBtn.setTextColor(Color.GRAY)
                    holder.secondView.matchBtn.background = ContextCompat.getDrawable(context,
                        R.drawable.radiobtn
                    )
                    holder.secondView.matchBtn.backgroundTintList = ContextCompat.getColorStateList(context,
                        R.color.lightgrey
                    )
                }
            }
            if (i >= 3) {
                holder.thirdView.visibility = View.VISIBLE
                val item3: MatchData = data.get(position).get(2)


                if(item3.time.toString().length==3) {
                    holder.thirdView.timeTitle.text = item3.time.toString().substring(0,1) +":" +item3.time.toString().substring(1,3)
                }
                else {
                    holder.thirdView.timeTitle.text = item3.time.toString().substring(0,2) +":" +item3.time.toString().substring(2,4)
                }

                holder.thirdView.schoolTitle.text = item3.group
                if (item3.group == "건국대학교") {
                    holder.thirdView.mainImg.setImageResource(R.drawable.img33)
                }
                if (item3.group == "한양대학교") {
                    holder.thirdView.mainImg.setImageResource(R.drawable.img44)
                }
                if (item3.group == "동국대학교") {
                    holder.thirdView.mainImg.setImageResource(R.drawable.img55)
                }
                holder.thirdView.teamTitle.text = item3.team
                holder.thirdView.numberText.text = item3.num
                holder.thirdView.matchBtn.isClickable = item3.accept
                if (item3.accept == false) {
                    holder.thirdView.matchBtn.text = "마감"
                    holder.thirdView.matchBtn.setTextColor(Color.GRAY)
                    holder.thirdView.matchBtn.background = ContextCompat.getDrawable(context,
                        R.drawable.radiobtn
                    )
                    holder.thirdView.matchBtn.backgroundTintList = ContextCompat.getColorStateList(context,
                        R.color.lightgrey
                    )
                }
            }

        }
        else {
            holder.mainLayout.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = data!!.size
}