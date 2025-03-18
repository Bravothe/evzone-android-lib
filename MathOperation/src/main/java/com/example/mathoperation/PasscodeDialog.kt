package com.example.mathoperation.dialogs

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mathoperation.R

object PasscodeDialog {

    private fun getDialogHeader(context: Context): LinearLayout {
        return LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
    }

    fun showPasscodeDialog(
        context: Context,
        amount: Int,
        attempts: Int,
        maxAttempts: Int,
        onSubmit: (String) -> Unit
    ) {
        val serviceFeePercentage = 5.0
        val vatPercentage = 15.0

        val serviceFee = amount * (serviceFeePercentage / 100)
        val vat = amount * (vatPercentage / 100)

        val totalAmount = amount + serviceFee + vat

        val inputPasscode = EditText(context).apply {
            hint = "Enter Passcode"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
            gravity = Gravity.CENTER
            setPadding(20, 20, 20, 20)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            isEnabled = attempts < maxAttempts
        }

        val attemptsMessage = TextView(context).apply {
            textSize = 14f
            setPadding(0, 10, 0, 10)
            gravity = Gravity.CENTER
            text = if (attempts >= maxAttempts) {
                "You are locked out! Try again later."
            } else {
                "You have ${maxAttempts - attempts} attempt(s) left."
            }
            setTextColor(Color.RED)
        }

        val message = "A total of $$totalAmount will be deducted from your Evzone Pay Wallet, including Service Fee: $$serviceFee and VAT: $$vat. Enter the passcode to approve the Transaction."

        val messageText = TextView(context).apply {
            text = message
            gravity = Gravity.CENTER
            textSize = 16f
        }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 10)
            addView(getDialogHeader(context))
            addView(messageText)
            addView(inputPasscode)
            addView(attemptsMessage)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setPositiveButton("Submit") { dialog, _ ->
                val passcode = inputPasscode.text.toString()
                if (passcode.isNotEmpty() && attempts < maxAttempts) {
                    onSubmit(passcode)
                    dialog.dismiss()
                } else if (attempts >= maxAttempts) {
                    Toast.makeText(context, "You are locked out!", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}
