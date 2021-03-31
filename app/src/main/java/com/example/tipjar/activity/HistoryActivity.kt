package com.example.tipjar.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tipjar.R
import com.example.tipjar.adapter.TipHistoryAdapter
import com.example.tipjar.database.viewmodel.TipViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_custom.view.*
import kotlinx.android.synthetic.main.toolbar_design_logo.*
import kotlinx.android.synthetic.main.toolbar_design_logo.view.*

@RequiresApi(Build.VERSION_CODES.O)
//@AndroidEntryPoint
class HistoryActivity: BaseActivity() {
//    private lateinit var mTipHistoryViewModel: TipHistoryViewModel

    private val mTipHistoryViewModel by viewModels<TipViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        initToolbar()
        initEvent()
        initValue()
    }

    private fun initEvent() {
        ivBack.setOnClickListener(onClicked)
    }

    private fun initToolbar() {
        toolbar.customToolbar.setCloseCurrentActivity(this)
        toolbar.customToolbar.setMenuRightDisplay(false)
        toolbar.customToolbar.setImageTitleDisplay(true)
        toolbar.customToolbar.setHideToolbarUnderLine(false)

        toolbar.customToolbar.tvTitle.text = getString(R.string.saved_payment);
    }

    private fun initValue() {
        // Recyclerview
        val adapter = TipHistoryAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.rlvHistory)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        // UserViewModel
//        mTipHistoryViewModel = ViewModelProvider(this).get(TipHistoryViewModel::class.java)
//        mTipHistoryViewModel.readAllData.observe(this, Observer { tip ->
//            adapter.setData(tip)
//        })

        mTipHistoryViewModel.readAllData().observe(this, Observer {
            adapter.setData(it)
        })
    }

    private val onClicked = View.OnClickListener {
        when(it.id) {
            R.id.ivBack -> {
                print("llBack clicked")
                closeActivitySlideLeft(this)
            }
        }
    }
}