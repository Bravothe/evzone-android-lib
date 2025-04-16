package com.example.mathoperation
import android.animation.ObjectAnimator
import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt

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
                setPadding(48, 48, 48, 48) // Increased padding for better spacing

                // Set the increased height of the dialog (800px)
                var layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    800  // Increased height for a taller dialog (in pixels)
                )
                this.layoutParams = layoutParams

                // Add the company logo with adjusted size and margins
                val logoImage = ImageView(context).apply {
                    Glide.with(context)
                        .load(R.drawable.logo1)
                        .error(R.drawable.logo1)
                        .into(this)
                    layoutParams = LinearLayout.LayoutParams(100, 100).apply {
                        setMargins(0, 0, 0, 32) // Add bottom margin for spacing
                    }
                }
                addView(logoImage)

                // Add the company name text with specific formatting
                val companyNameText = TextView(context).apply {
                    val companyText = "EVzone Pay"
                    text = companyText
                    textSize = 34f // Slightly larger text for better visibility
                    gravity = Gravity.CENTER
                    setTypeface(null, android.graphics.Typeface.BOLD)

                    // Apply colored spans
                    val spannableText = SpannableString(companyText)
                    spannableText.setSpan(
                        ForegroundColorSpan("#4CAF50".toColorInt()),
                        0,
                        6,
                        0
                    ) // Softer green for "EVzone"
                    spannableText.setSpan(
                        ForegroundColorSpan("#FFA500".toColorInt()),
                        7,
                        companyText.length,
                        0
                    ) // Orange for "Pay"
                    text = spannableText

                    // Fade-in/fade-out animation
                    val fadeInOutAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f, 0f).apply {
                        duration = 1200 // Slightly slower animation for smoother effect
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