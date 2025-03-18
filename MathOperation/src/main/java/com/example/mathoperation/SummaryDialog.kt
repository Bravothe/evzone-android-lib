package com.example.mathoperation.dialogs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mathoperation.R

object SummaryDialog {

    private fun getDialogHeader(context: Context): LinearLayout {
        return LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
    }

    fun showSummaryDialog(context: Context, businessName: String, userName: String, itemsPurchased: String, totalAmount: Double, onNext: (Double) -> Unit) {
        val summary = """
            Business: $businessName
            User: $userName
            Items Purchased: $itemsPurchased
            Total Amount: $$totalAmount
        """.trimIndent()

        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(getDialogHeader(context))
            val summaryTextView = TextView(context).apply {
                text = summary
                gravity = Gravity.CENTER
                textSize = 16f
            }
            addView(summaryTextView)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("Next") { dialog, _ ->
                dialog.dismiss()
                onNext(totalAmount)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}
