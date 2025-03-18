package com.example.mathoperation.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mathoperation.R

object SummaryDialog {
    private fun getDialogHeader(context: Context): LinearLayout {
        return LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
    }

    private fun createLabelValuePair(context: Context, label: String, value: String): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 10, 0, 10)  // Add some vertical padding between rows

            // Layout for label text
            val labelTextView = TextView(context).apply {
                text = label
                textSize = 16f
                gravity = Gravity.START
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) // Takes up the left side
                setPadding(20, 20, 20, 20)  // Add padding for better spacing inside the label
            }

            // Layout for value text
            val valueTextView = TextView(context).apply {
                text = value
                textSize = 16f
                gravity = Gravity.END
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f) // Takes up the right side
                setPadding(20, 20, 20, 20)  // Add padding for better spacing inside the value
            }

            addView(labelTextView)
            addView(valueTextView)
        }
    }

    fun showSummaryDialog(context: Context, businessName: String, userName: String, itemsPurchased: String, totalAmount: Double, onNext: (Double) -> Unit) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(30, 30, 30, 30)  // Add padding to the entire dialog layout

            // Create a list of label-value pairs
            val labelValuePairs = listOf(
                "Business" to businessName,
                "User" to userName,
                "Items Purchased" to itemsPurchased,
                "Total Amount" to "$$totalAmount"
            )

            // Create a container LinearLayout for the summary, with a light blue background
            val summaryContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(20, 20, 20, 20)  // Add padding inside the container

                // Create the background for the whole summary container
                val backgroundDrawable = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 20f
                    setColor(Color.parseColor("#ADD8E6"))  // Light blue background color
                }

                // Set the background for the container
                background = backgroundDrawable

                // Add each label-value pair to the container
                labelValuePairs.forEach { (label, value) ->
                    addView(createLabelValuePair(context, label, value))
                }
            }

            // Add the summary container to the dialog layout
            addView(getDialogHeader(context))
            addView(summaryContainer)

        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("Continue") { dialog, _ ->
                dialog.dismiss()
                onNext(totalAmount)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}
