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

            addView(getDialogHeader(context))

            val detailedMessage = TextView(context).apply {
                text = if (isSuccess) "Your payment was processed successfully!" else "Wrong Passcode. Try again."
                gravity = Gravity.CENTER
                textSize = 16f
                setPadding(0, 20, 0, 20)
            }
            addView(detailedMessage)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setIcon(if (isSuccess) R.drawable.check else R.drawable.error)
            .create()

        dialog.show()
    }
}


