package com.example.tipjar.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tipjar.R
import com.example.tipjar.database.entity.TipHistory
import kotlinx.android.synthetic.main.dialog_custom_layout.*
import kotlinx.android.synthetic.main.row_history.view.*

@RequiresApi(Build.VERSION_CODES.O)
class TipHistoryAdapter: RecyclerView.Adapter<TipHistoryAdapter.TipHistoryResultHolder>() {
    private var tipHistoryList = emptyList<TipHistory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipHistoryResultHolder {
        val rootview = LayoutInflater.from(parent.context).inflate(R.layout.row_history, parent, false)
        return TipHistoryResultHolder(rootview)
    }

    override fun getItemCount(): Int {
        return tipHistoryList.size
    }

    override fun onBindViewHolder(holder: TipHistoryResultHolder, position: Int) {
        holder.setData(tipHistoryList[position])
    }

    fun setData(tip: List<TipHistory>){
        this.tipHistoryList = tip
        notifyDataSetChanged()
    }

    inner class TipHistoryResultHolder(private val view: View): RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun setData(tipHistory: TipHistory) {

            view.tvDate.text = tipHistory.timestamp
            view.tvAmount.text = tipHistory.amount.toAmount()
            view.tvTip.text = tipHistory.tip.toTip()
            if(tipHistory.photo.isNotEmpty()) {
                tipHistory.photo.let {
                    Glide.with(view.context)
                            .load(Base64.decode(it, Base64.DEFAULT))
                            .into(view.ivPhoto)
                }
            }

            view.rowLayout.setOnClickListener {
                dialogTipReview(view.context, tipHistory).show()
            }
        }

        private fun dialogTipReview(context: Context, tipHistory: TipHistory): Dialog{
            val customDialog = Dialog(context)

            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            customDialog.setContentView(R.layout.dialog_custom_layout)

            if(tipHistory.photo.isNotEmpty()) {
                Glide.with(context)
                    .load(Base64.decode(tipHistory.photo, Base64.DEFAULT))
                    .into(customDialog.tvTipPhoto)
            }
            else {
                customDialog.cvImageContainer.visibility = View.GONE
            }

            customDialog.tvDate.text = tipHistory.timestamp
            customDialog.tvAmount.text = tipHistory.amount.toAmount()
            customDialog.tvTip.text = tipHistory.tip.toTip()
            customDialog.window!!.setBackgroundDrawableResource(R.color.colorClear)
            customDialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)

            return customDialog
        }

        private fun String.toAmount(): String {
            val amount = if(this.isEmpty()) "0.0" else this
            return "\$$amount"
        }

        private fun String.toTip(): String {
            val tip = if(this.isEmpty()) "0.0" else this
            return "Tip: \$$tip"
        }
    }
}