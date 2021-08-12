package com.example.flag

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class CustomView : LinearLayout {
    lateinit var timeTitle: TextView
    lateinit var mainImg: ImageView
    lateinit var schoolTitle: TextView
    lateinit var teamTitle: TextView
    lateinit var numberText: TextView
    lateinit var matchBtn: Button

    constructor(context: Context) :super(context) {
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context)
        getAttrs(attrs)

    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init(context)
        getAttrs(attrs,defStyleAttr)
    }
    private fun init(context:Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.customview,this,false)
        addView(view)

        timeTitle = view.findViewById(R.id.timeText)
        mainImg = view.findViewById(R.id.mainImg)
        schoolTitle = view.findViewById(R.id.eventTitle)
        teamTitle = view.findViewById(R.id.num)
        numberText = view.findViewById(R.id.numberText)
        matchBtn = view.findViewById(R.id.matchingBtn)
    }
    private fun getAttrs(attrs:AttributeSet?){
        //아까 만들어뒀던 속성 attrs 를 참조함
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomViewData)
        setTypeArray(typedArray)
    }
    private fun getAttrs(attrs:AttributeSet?, defStyle:Int){
        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.CustomViewData,defStyle,0)
        setTypeArray(typedArray)
    }
    private fun setTypeArray(typedArray : TypedArray){
        //레이아웃의 배경, LoginButton 이름으로 만든 attrs.xml 속성중 bgColor 를 참조함
        val timetext = typedArray.getText(R.styleable.CustomViewData_timeTitleText)
        timeTitle.text = timetext

        //이미지의 배경, LoginButton 이름으로 만든 attrs.xml 속성중 imgColor 를 참조함
        val imgResId = typedArray.getResourceId(R.styleable.CustomViewData_itemImg,R.drawable.ic_launcher_foreground)
        mainImg.setImageResource(imgResId)

        val schoolText = typedArray.getText(R.styleable.CustomViewData_schoolText)
        schoolTitle.text = schoolText

        //텍스트 색, LoginButton 이름으로 만든 attrs.xml 속성중 textColor 를 참조함
//        val textColor = typedArray.getColor(R.styleable.LoginButton_textColor,0)
//        tvName.setTextColor(textColor)

        //텍스트 내용, LoginButton 이름으로 만든 attrs.xml 속성중 text 를 참조함
        val text = typedArray.getText(R.styleable.CustomViewData_numberText)
        numberText.text = text

        val teamText = typedArray.getText(R.styleable.CustomViewData_teamText)
        teamTitle.text = teamText

        val btnClick = typedArray.getBoolean(R.styleable.CustomViewData_btnClick,true)
        if(matchBtn.isClickable == false) {
            matchBtn.text = "마감"
        }
        matchBtn.isClickable = btnClick

        typedArray.recycle()
    }

//    init {
//        val v = View.inflate(context, R.layout.customview, this)
//        timeTitle = v.findViewById(R.id.timeText)
//        mainImg = v.findViewById(R.id.mainImg)
//        schoolTitle = v.findViewById(R.id.schoolTitle)
//        teamTitle = v.findViewById(R.id.teamTitle)
//        numberText = v.findViewById(R.id.numberText)
//        matchBtn = v.findViewById(R.id.matchingBtn)
//        context.theme.obtainStyledAttributes(
//            attrs,
//            R.styleable.CustomViewData,
//            0, 0
//        ).apply {
//            try {
//                settimeTitleText(getString(R.styleable.CustomViewData_itemTitleText))
//                setschoolTitle(getString(R.styleable.CustomViewData_itemTitleText))
//                setteamTitleText(getString(R.styleable.CustomViewData_itemTitleText))
//                setnumberText(getString(R.styleable.CustomViewData_itemTitleText))
//
//            }
//            finally {
//                recycle()
//            }
//        }
//    }








}
