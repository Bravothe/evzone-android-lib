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
            gravity = Gravity.CENTER
            setPadding(40, 20, 40, 20)  // Add some padding around the content

            addView(getDialogHeader(context))  // Add the custom dialog header at the top

            // Add the icon (error icon in this case)
            val icon = ImageView(context).apply {
                setImageResource(R.drawable.low)  // Set the error icon

                // Set fixed width and height for the icon (e.g., 50x50 dp)
                val sizeInDp = 50  // Set the desired size in dp
                val scale = context.resources.displayMetrics.density  // Convert dp to pixels
                val sizeInPx = (sizeInDp * scale).toInt()

                layoutParams = LinearLayout.LayoutParams(sizeInPx, sizeInPx).apply {
                    gravity = Gravity.CENTER  // Center the icon horizontally
                }
            }

            addView(icon)

            // Add the insufficient balance message below the icon
            val insufficientBalanceMessage = TextView(context).apply {
                text = "The account did not have sufficient funds to process the transaction."
                gravity = Gravity.CENTER
                textSize = 16f
                setPadding(0, 20, 0, 20)  // Padding below the icon
            }
            addView(insufficientBalanceMessage)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.show()
    }
}
