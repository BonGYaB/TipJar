package com.example.tipjar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.tipjar.R
import com.example.tipjar.activity.BaseActivity
import kotlinx.android.synthetic.main.toolbar_design_logo.view.*

class ToolbarCustom: LinearLayout {

    private var toolBarTitle = ""
    private var rightMenuIconOne = 0
    private var displayLeftMenu = true

    private lateinit var activity: BaseActivity

    constructor(context: Context?) : super(context) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_design_logo, this);
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context?, attrs: AttributeSet?) {

        val data = context!!.theme.obtainStyledAttributes(attrs,
            R.styleable.CustomToolbar, 0, 0)
        try {
//            toolBarTitle = data.getString(R.styleable.CustomToolbar_toolbarTitle)!!
            rightMenuIconOne = data.getResourceId(R.styleable.CustomToolbar_rightMenuIconOne, 0)
            displayLeftMenu = data.getBoolean(R.styleable.CustomToolbar_displayLeftMenu, true)

        } finally {
            data.recycle()
        }

        LayoutInflater.from(context).inflate(R.layout.toolbar_design_logo, this);
        llBack.setOnClickListener(onClicked)
    }

    fun setHideBackButton(isHide: Boolean) {

        llBack.visibility = View.VISIBLE

        if (isHide) {
            llBack.visibility = View.GONE
        }
    }

    fun setImageTitleDisplay(isHide: Boolean) {
        if (isHide) {
            ivTitle.visibility = View.GONE
            tvTitle.visibility = View.VISIBLE
        }
    }

    fun setHideToolbarUnderLine(isHide: Boolean) {
        if (isHide) {
            vToolbarUnderline.visibility = View.GONE
        }
        else {
            vToolbarUnderline.visibility = View.VISIBLE
        }
    }

    fun setTineColor(color: Int) {
        ivBack.setColorFilter(ContextCompat.getColor(context, color), android.graphics.PorterDuff.Mode.SRC_IN)
        tvTitle.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setCloseCurrentActivity(activity: BaseActivity) {
        this.activity = activity
    }

    fun setOnCloseClicked(onClicked: OnClickListener) {
        llBack.setOnClickListener(onClicked)
    }

    fun setMenuRightImage(restId: Int) {
        ivMenuRight.setImageResource(restId)
    }

    fun setMenuRightDisplay(isDisplay: Boolean) {
        ivMenuRight.visibility = View.GONE

        if (isDisplay) {
            ivMenuRight.visibility = View.VISIBLE
        }
    }

    fun setOnMenuRightClicked(onClicked: OnClickListener) {
        ivMenuRight.setOnClickListener(onClicked)
    }

    private val onClicked = OnClickListener {
        this.activity.finish()
        this.activity.overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity)
    }
}