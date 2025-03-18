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

                // Add the company name text with specific formatting
                val companyNameText = TextView(context).apply {
                    val companyText = "EVzone Pay"
                    text = companyText
                    textSize = 32f // Increase the font size
                    gravity = Gravity.CENTER
                    setTypeface(null, android.graphics.Typeface.BOLD) // Set the text to bold

                    // Adjust the colors (softer green for "Evzone" and orange for "Pay")
                    val spannableText = SpannableString(companyText)
                    spannableText.setSpan(ForegroundColorSpan(Color.parseColor("#4CAF50")), 0, 6, 0)  // Softer green
                    spannableText.setSpan(ForegroundColorSpan(Color.parseColor("#FFA500")), 7, companyText.length, 0)  // Orange for "Pay"
                    text = spannableText
                }
                addView(companyNameText)

                // Set up the fade-in fade-out animation for the text (appearing and disappearing)
                val fadeInOutAnimator = ObjectAnimator.ofFloat(companyNameText, "alpha", 0f, 1f, 0f)
                fadeInOutAnimator.apply {
                    duration = 1000  // Duration of one complete fade-in and fade-out cycle
                    repeatCount = ObjectAnimator.INFINITE  // Repeat the animation infinitely
                    repeatMode = ObjectAnimator.RESTART  // Restart the fade-in fade-out after each cycle
                }
                fadeInOutAnimator.start()  // Start the fade-in and fade-out animation
            }
            setView(loadingLayout)
            setCancelable(false)
        }.create()

        // Apply fade-in/fade-out animations
        applyDialogAnimations(loadingDialog)

        loadingDialog.show()

        // Simulate a delay before showing the next dialog
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000) // Delay of 2 seconds before showing the next dialog
            onFinish()  // Transition to the next dialog
            loadingDialog.dismiss()
        }
    }
}
