package com.example.mathoperation.dialogs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mathoperation.R

object InsufficientBalanceDialog {

    fun showInsufficientBalance(context: Context) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(60, 40, 60, 40)
        }

        // Create a FrameLayout for absolute positioning of the close button
        val headerContainer = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // Horizontal header layout (Logo + Title)
        val headerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
            }
        }

        val logo = ImageView(context).apply {
            setImageResource(R.drawable.evzone)
            val size = (40 * context.resources.displayMetrics.density).toInt()
            layoutParams = LinearLayout.LayoutParams(size, size).apply {
                setMargins(0, 0, 10, 0)
            }
        }

        val title = TextView(context).apply {
            text = "EVzone Pay"
            textSize = 18f
            setTextColor(context.getColor(R.color.black))
            gravity = Gravity.CENTER_VERTICAL
        }

        // Close button positioned in the top-right corner
        val closeButton = ImageView(context).apply {
            setImageResource(R.drawable.cancel)
            val size = (24 * context.resources.displayMetrics.density).toInt()
            layoutParams = FrameLayout.LayoutParams(size, size).apply {
                gravity = Gravity.END or Gravity.TOP
                setMargins(0, 10, 10, 0)
            }
        }

        // Add views to header layout
        headerLayout.addView(logo)
        headerLayout.addView(title)

        // Add header layout & close button to frame container
        headerContainer.addView(headerLayout)
        headerContainer.addView(closeButton)

        // Warning Icon
        val warningIcon = ImageView(context).apply {
            setImageResource(R.drawable.cross)
            val size = (60 * context.resources.displayMetrics.density).toInt()
            layoutParams = LinearLayout.LayoutParams(size, size)
        }

        // Title Text
        val insufficientTitle = TextView(context).apply {
            text = "Insufficient Funds"
            textSize = 20f
            setTextColor(context.getColor(R.color.orange))
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 10)
        }

        // Message Text
        val message = TextView(context).apply {
            text = "The account did not have sufficient funds to cover the transaction amount at the time of the transaction."
            textSize = 16f
            setTextColor(context.getColor(R.color.black))
            gravity = Gravity.CENTER
            setPadding(20, 10, 20, 20)
        }

        // Add Funds Button
        val addFundsButton = Button(context).apply {
            text = "Add Funds"
            setBackgroundColor(context.getColor(R.color.blue))
            setTextColor(context.getColor(R.color.white))
            textSize = 16f
            setPadding(20, 10, 20, 10)
        }

        // Add views to the main layout
        dialogLayout.addView(headerContainer) // Header (with close button)
        dialogLayout.addView(warningIcon)
        dialogLayout.addView(insufficientTitle)
        dialogLayout.addView(message)
        dialogLayout.addView(addFundsButton)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setCancelable(false) // Prevent accidental dismiss
            .create()

        // Set close button functionality after dialog is initialized
        closeButton.setOnClickListener { dialog.dismiss() }
        addFundsButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}
