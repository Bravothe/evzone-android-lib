package com.example.mathoperation

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColorInt

object PaymentStatusDialog {
    private const val TAG = "PaymentStatusDialog"

    private fun getDialogHeader(context: Context): LinearLayout {
        return LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
    }
    fun showPaymentStatus(context: Context, isSuccess: Boolean, remainingAttempts: Int) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(40, 20, 40, 20)

            // Set a custom height for the dialog layout
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Width is still match_parent
                (320 * context.resources.displayMetrics.density).toInt() // Adjust the height value here
            )

            val icon = ImageView(context).apply {
                setImageResource(if (isSuccess) R.drawable.scheck else R.drawable.error)
                val iconSize = (48 * context.resources.displayMetrics.density).toInt()
                layoutParams = LinearLayout.LayoutParams(iconSize, iconSize).apply {
                    gravity = Gravity.CENTER
                    setMargins(0, 0, 0, 10)
                }
            }
            addView(icon)

            val detailedMessage = TextView(context).apply {
                text = if (isSuccess) {
                    "Payment Successful"
                } else {
                    "Wrong Passcode. Try again"
                }
                gravity = Gravity.CENTER
                textSize = 16f
                setPadding(0, 20, 0, 20)
            }
            addView(detailedMessage)

            val doneButton = Button(context).apply {
                text = "Done"
                textSize = 14f
                setTextColor(Color.WHITE)
                setPadding(
                    (12 * context.resources.displayMetrics.density).toInt(),
                    (4 * context.resources.displayMetrics.density).toInt(),
                    (12 * context.resources.displayMetrics.density).toInt(),
                    (4 * context.resources.displayMetrics.density).toInt()
                )
                background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 16f
                    setColor(if (isSuccess) "#007BFF".toColorInt() else "#FF9800".toColorInt())
                }
                isAllCaps = false
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    (32 * context.resources.displayMetrics.density).toInt()
                ).apply {
                    gravity = Gravity.CENTER
                }
            }
            addView(doneButton)

            val dialog = AlertDialog.Builder(context, R.style.CustomDialogTheme)
                .setView(this)
                .setCancelable(false)
                .create()

            doneButton.setOnClickListener {
                Log.d(TAG, "Done button clicked")
                dialog.dismiss()
            }

            Log.d(TAG, "Showing PaymentStatusDialog, isSuccess: $isSuccess")
            dialog.show()
        }
    }

}