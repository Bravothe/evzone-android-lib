package com.example.mathoperation.dialogs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mathoperation.R

object InsufficientBalanceDialog {

    private fun getDialogHeader(context: Context): LinearLayout {
        return LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
    }

    fun showInsufficientBalance(context: Context) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(getDialogHeader(context))

            val insufficientBalanceMessage = TextView(context).apply {
                text = "Your wallet balance is too low to complete this payment. Thank You"
                gravity = Gravity.CENTER
                textSize = 16f
            }

            addView(insufficientBalanceMessage)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setIcon(R.drawable.error)
            .create()

        dialog.show()
    }
}

