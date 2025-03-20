package com.example.mathoperation

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object LoadingDialog {

    // Helper method to apply custom theme and animations
    private fun createStyledDialog(context: Context): AlertDialog {
        return AlertDialog.Builder(context, R.style.CustomDialogTheme)
            .create()
    }

    fun showLoadingDialog(context: Context, onFinish: () -> Unit) {
        val loadingDialog = createStyledDialog(context).apply {
            val loadingLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(32, 32, 32, 32) // Add some padding for better spacing

                // Add the company logo
                val logoImage = ImageView(context).apply {
                    Glide.with(context)
                        .load(R.drawable.evzone)
                        .error(R.drawable.evzone)
                        .into(this)
                    layoutParams = LinearLayout.LayoutParams(200, 200)
                }
                addView(logoImage)

                // Add the company name text with specific formatting
                val companyNameText = TextView(context).apply {
                    val companyText = "EVzone Pay"
                    text = companyText
                    textSize = 32f
                    gravity = Gravity.CENTER
                    setTypeface(null, android.graphics.Typeface.BOLD)

                    // Apply colored spans
                    val spannableText = SpannableString(companyText)
                    spannableText.setSpan(
                        ForegroundColorSpan(Color.parseColor("#4CAF50")),
                        0,
                        6,
                        0
                    ) // Softer green for "EVzone"
                    spannableText.setSpan(
                        ForegroundColorSpan(Color.parseColor("#FFA500")),
                        7,
                        companyText.length,
                        0
                    ) // Orange for "Pay"
                    text = spannableText

                    // Fade-in/fade-out animation
                    val fadeInOutAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f, 0f).apply {
                        duration = 1000
                        repeatCount = ObjectAnimator.INFINITE
                        repeatMode = ObjectAnimator.RESTART
                    }
                    fadeInOutAnimator.start()
                }
                addView(companyNameText)
            }
            setView(loadingLayout)
            setCancelable(false)
        }

        loadingDialog.show()

        // Dismiss after delay and trigger onFinish
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000) // 3-second delay
            loadingDialog.dismiss()
            onFinish()
        }
    }
}