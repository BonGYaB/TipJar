package com.example.tipjar.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.tipjar.R
import com.example.tipjar.database.entity.TipHistory
import com.example.tipjar.database.viewmodel.TipViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_history.*
import kotlinx.android.synthetic.main.toolbar_custom.view.*
import kotlinx.android.synthetic.main.toolbar_design_logo.*
import java.io.ByteArrayOutputStream
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
//@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val TAG: String = "MainActivity"
    private var mPeople = 0
    private var isCheckedTakephoto = false
    private val REQUEST_CAPTURE_IMAGE = 100
    private var imageBitmap: Bitmap? = null
    private var mAmount = ""
    private var mTip = ""
    private val timeStamp = DateTimeFormatter.ofPattern("yyyy MMMM dd").withZone(ZoneOffset.UTC).format(Instant.now())!!

    private lateinit var mTipViewModel: TipViewModel
//    private val mTipViewModel: TipViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTipViewModel = ViewModelProvider(this).get(TipViewModel::class.java)
//        Log.d(TAG,"TipViewModel Instance ${mTipViewModel.getInstance()}")

        initToolbar()
        initEvent()
        initValue()
    }

    private fun initToolbar() {
        toolbar.customToolbar.setMenuRightDisplay(true)
        toolbar.customToolbar.setHideBackButton(true)
        toolbar.customToolbar.setMenuRightImage(R.drawable.ic_history)
        toolbar.customToolbar.setHideToolbarUnderLine(true)
    }

    private fun initEvent() {
        ivMenuRight.setOnClickListener(onClicked)
        buttonSavePayment.setOnClickListener(onClicked)
        iButtonMinus.setOnClickListener(onClicked)
        iButtonPlus.setOnClickListener(onClicked)

        checkboxTakePhoto.setOnClickListener(onClicked)
    }

    private fun initValue() {
        tvPeople.text = mPeople.toString()
//        mTipViewModel = ViewModelProvider(this).get(TipViewModel::class.java)
    }

    private var onClicked = View.OnClickListener {
        when (it.id) {
            R.id.ivMenuRight -> {
                moveToActivitySlideToRight(this@MainActivity, HistoryActivity::class.java)
            }
            R.id.checkboxTakePhoto -> {
                isCheckedTakephoto = !isCheckedTakephoto
            }
            R.id.buttonSavePayment -> {
                savePayment()
            }
            R.id.iButtonPlus -> {
                if (mPeople >= 0) {
                    mPeople++
                    tvPeople.text = mPeople.toString()
                }
            }
            R.id.iButtonMinus -> {
                if (mPeople > 0) {
                    mPeople--
                    tvPeople.text = mPeople.toString()
                }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun savePayment() {
        mAmount = ieAmount.text.toString().trim()
        mTip = ieTip.text.toString().trim()

        if(mAmount.isEmpty()) {
            Toast.makeText(this, getString(R.string.required_amount), Toast.LENGTH_LONG).show()
            return
        }

        if(mTip.isEmpty()) {
            Toast.makeText(this, getString(R.string.required_tip), Toast.LENGTH_LONG).show()
            return
        }

        if(isCheckedTakephoto) {
            val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (pictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE)
            }
            else {
                Toast.makeText(this, getString(R.string.error_camera), Toast.LENGTH_LONG).show()
                return
            }
        }
        else {
            saveTip()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CAPTURE_IMAGE && resultCode === RESULT_OK) {
            if(data!!.extras != null) {
                imageBitmap = data.extras!!.get("data") as Bitmap
                saveTip()
            }
            else {
                Toast.makeText(this, "Something wrong", Toast.LENGTH_LONG).show()
            }
        }
        else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        }
    }

    private fun Bitmap.toBitmapToBase64(): String {
        val outputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun saveTip() {
        var photoAsBase64 = ""
        if(imageBitmap != null) {
            photoAsBase64 = imageBitmap!!.toBitmapToBase64()
        }

        val tip = TipHistory(0, mAmount, mPeople.toString(), mTip, photoAsBase64, timeStamp)
        mTipViewModel.addTip(tip)
        Toast.makeText(this, "The payment is successful", Toast.LENGTH_LONG).show()
    }
}