package com.example.mathoperation

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

object LoadingDialog {

    // Helper method to load the custom header
    private fun getDialogHeader(context: Context): LinearLayout {
        val headerLayout = LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
        return headerLayout
    }

    // Method to apply custom animations to the dialog
    private fun applyDialogAnimations(dialog: AlertDialog) {
        dialog.window?.setWindowAnimations(R.style.DialogAnimation)
    }

    // Method to show the loading dialog
    fun showLoadingDialog(context: Context, onFinish: () -> Unit) {
        val loadingDialog = AlertDialog.Builder(context).apply {
            val loadingLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER

                // Add the company logo
                val logoImage = ImageView(context).apply {
                    Glide.with(context)
                        .load(R.drawable.evzone)  // Replace with your company logo
                        .error(R.drawable.evzone)  // Fallback logo
                        .into(this)
                    layoutParams = LinearLayout.LayoutParams(200, 200)  // Adjust size as needed
                }
                addView(logoImage)

                // Add the blinking company name
                val companyNameText = TextView(context).apply {
                    text = "Evzone Pay"
                    textSize = 24f
                    gravity = Gravity.CENTER
                }
                addView(companyNameText)

                // Blinking effect for the text
                val handler = Handler(Looper.getMainLooper())
                val blinkingRunnable = object : Runnable {
                    var isGreen = true
                    override fun run() {
                        if (isGreen) {
                            companyNameText.setTextColor(Color.GREEN)
                        } else {
                            companyNameText.setTextColor(Color.parseColor("#FFA500")) // Orange color
                        }
                        isGreen = !isGreen
                        handler.postDelayed(this, 500) // Toggle the color every 500ms
                    }
                }

                handler.post(blinkingRunnable)
            }
            setView(loadingLayout)
            setCancelable(false)
        }.create()

        // Apply fade-in/fade-out animations
        applyDialogAnimations(loadingDialog)

        loadingDialog.show()

        // Simulate a delay before showing the next dialog
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000) // Delay of 2 seconds before showing the next dialog
            onFinish()  // Transition to the next dialog
            loadingDialog.dismiss()
        }
    }
}
