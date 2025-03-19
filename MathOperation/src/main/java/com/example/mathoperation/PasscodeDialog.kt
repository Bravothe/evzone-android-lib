package com.example.mathoperation.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.mathoperation.R

object PasscodeDialog {
    private lateinit var dialog: AlertDialog

    fun showPasscodeDialog(
        context: Context,
        merchantName: String,
        transactionId: String,
        amount: String,
        tax: String,
        walletFee: String,
        onSubmit: (String) -> Unit
    ) {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 40)
            setBackgroundColor(Color.WHITE)
        }

        val headerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, 0, 0, 20)
        }

        val logo = ImageView(context).apply {
            setImageResource(R.drawable.evzone)
            layoutParams = LinearLayout.LayoutParams(80, 80)
        }

        val title = TextView(context).apply {
            text = "EVzone Pay"
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val closeButton = ImageView(context).apply {
            setImageResource(R.drawable.cancel)
            layoutParams = LinearLayout.LayoutParams(50, 50)
            setOnClickListener { dialog.dismiss() }
        }

        headerLayout.addView(logo)
        headerLayout.addView(title)
        headerLayout.addView(closeButton)

        val merchantText = TextView(context).apply {
            text = "Merchant Info:\n$merchantName\nTransaction ID: $transactionId"
            textSize = 16f
            setTextColor(Color.BLACK)
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.START
        }

        val amountText = TextView(context).apply {
            text = "UGX $amount"
            textSize = 18f
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.BLACK)
            gravity = Gravity.END
        }

        val passcodeLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(10, 10, 10, 10)
            background = GradientDrawable().apply {
                setColor(Color.LTGRAY)
                cornerRadius = 15f
            }
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 30) // Add bottom margin to separate from the info box
            }
        }


        val passcodeInput = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            textSize = 18f
            setPadding(20, 20, 20, 20)
            maxLines = 1
            minHeight = 100  // Ensure height consistency
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val toggleVisibility = ImageView(context).apply {
            setImageResource(R.drawable.ic_eye_off)
            layoutParams = LinearLayout.LayoutParams(80, 80)  // Ensure consistent size
            setPadding(10, 10, 10, 10)
            setOnClickListener {
                val selection = passcodeInput.selectionStart
                if (passcodeInput.inputType == (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)) {
                    passcodeInput.inputType = InputType.TYPE_CLASS_NUMBER
                    setImageResource(R.drawable.ic_eye_on)
                } else {
                    passcodeInput.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                    setImageResource(R.drawable.ic_eye_off)
                }
                passcodeInput.setSelection(selection)
            }
        }

        passcodeLayout.addView(passcodeInput)
        passcodeLayout.addView(toggleVisibility)


        val infoBox = TextView(context).apply {
            text = "You are making a payment to $merchantName.\nAmount UGX $amount will be deducted from your wallet, including $tax tax and $walletFee wallet fee."
            textSize = 14f
            setTextColor(Color.BLACK)
            setPadding(20, 20, 20, 20)
            setBackgroundColor(Color.parseColor("#D9EFFF"))
            gravity = Gravity.CENTER
        }

        val buttonsLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 20, 0, 0)
        }

        val confirmButton = Button(context).apply {
            text = "Confirm"
            textSize = 16f
            setTextColor(Color.WHITE)
            backgroundTintList = ContextCompat.getColorStateList(context, R.color.blue)  // Ensure blue color
            setPadding(20, 20, 20, 20)
            setOnClickListener {
                val passcode = passcodeInput.text.toString()
                if (passcode.length != 6) {
                    Toast.makeText(context, "Passcode must be 5 digits.", Toast.LENGTH_SHORT).show()
                } else {
                    onSubmit(passcode)
                    dialog.dismiss()
                }
            }
        }


        val backButton = Button(context).apply {
            text = "Back"
            textSize = 16f
            setTextColor(Color.RED)
            setBackgroundColor(Color.TRANSPARENT)
            setPadding(20, 20, 20, 20)
            setOnClickListener { dialog.dismiss() }
        }

        buttonsLayout.addView(confirmButton)
        buttonsLayout.addView(backButton)

        layout.addView(headerLayout)
        layout.addView(merchantText)
        layout.addView(amountText)
        layout.addView(passcodeLayout)
        layout.addView(infoBox)
        layout.addView(buttonsLayout)

        dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setCancelable(false)
            .create()

        dialog.show()
    }
}
