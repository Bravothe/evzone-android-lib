package com.example.mathoperation
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog

object InsufficientBalanceDialog {

    fun showInsufficientBalance(context: Context) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(60, 40, 60, 40)
        }

        val headerContainer = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

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

        val closeButton = ImageView(context).apply {
            setImageResource(R.drawable.gray)
            val size = (24 * context.resources.displayMetrics.density).toInt()
            layoutParams = FrameLayout.LayoutParams(size, size).apply {
                gravity = Gravity.END or Gravity.TOP
                setMargins(0, 10, 10, 0)
            }
        }

        headerLayout.addView(logo)
        headerLayout.addView(title)
        headerContainer.addView(headerLayout)
        headerContainer.addView(closeButton)

        val warningIcon = ImageView(context).apply {
            setImageResource(R.drawable.cross)
            val size = (60 * context.resources.displayMetrics.density).toInt()
            layoutParams = LinearLayout.LayoutParams(size, size)
        }

        val insufficientTitle = TextView(context).apply {
            text = "Insufficient Funds"
            textSize = 20f
            setTextColor(context.getColor(R.color.orange))
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 10)
        }

        val message = TextView(context).apply {
            text = "The account did not have sufficient funds to cover the transaction amount at the time of the transaction."
            textSize = 16f
            setTextColor(context.getColor(R.color.black))
            gravity = Gravity.CENTER
            setPadding(20, 10, 20, 20)
        }

        val addFundsButton = Button(context).apply {
            text = "Add Funds"
            setTextColor(context.getColor(R.color.white))
            textSize = 16f
            setPadding(20, 10, 20, 10)
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor(context.getColor(R.color.blue))
            }
            isAllCaps = false
        }

        dialogLayout.addView(headerContainer)
        dialogLayout.addView(warningIcon)
        dialogLayout.addView(insufficientTitle)
        dialogLayout.addView(message)
        dialogLayout.addView(addFundsButton)

        // Apply the custom theme with rounded corners and animations
        val dialog = AlertDialog.Builder(context, R.style.CustomDialogTheme)
            .setView(dialogLayout)
            .setCancelable(false)
            .create()

        closeButton.setOnClickListener { dialog.dismiss() }
        addFundsButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }
}