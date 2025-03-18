package com.example.mathoperation.dialogs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mathoperation.R

object PaymentStatusDialog {

    private fun getDialogHeader(context: Context): LinearLayout {
        return LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
    }

    fun showPaymentStatus(context: Context, isSuccess: Boolean, remainingAttempts: Int) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(40, 20, 40, 20)  // Add some padding around the content

            addView(getDialogHeader(context))  // Add the custom dialog header at the top

            // Add the icon and position it in the center
            val icon = ImageView(context).apply {
                setImageResource(if (isSuccess) R.drawable.check else R.drawable.error)  // Set appropriate icon
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    gravity = Gravity.CENTER  // Center the icon
                }
            }
            addView(icon)

            // Add the detailed message below the icon
            val detailedMessage = TextView(context).apply {
                text = if (isSuccess) "Payment Processed Successfully!" else "Wrong Passcode. Try again."
                gravity = Gravity.CENTER
                textSize = 16f
                setPadding(0, 20, 0, 20)  // Padding below the icon
            }
            addView(detailedMessage)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.show()
    }
}
