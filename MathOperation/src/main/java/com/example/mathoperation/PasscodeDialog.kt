package com.example.mathoperation.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import com.example.mathoperation.R

object PasscodeDialog {

    // Function to create a circular input field for each digit
    private fun createPasscodeCircle(context: Context): EditText {
        return EditText(context).apply {
            isSingleLine = true
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
            gravity = Gravity.CENTER
            textSize = 18f
            layoutParams = LinearLayout.LayoutParams(100, 100).apply {
                marginStart = 10
                marginEnd = 10
            }
            setTextColor(Color.BLACK)
            background = context.getDrawable(R.drawable.circle_border)  // Ensure you have circle_border.xml in your drawable folder
            maxLines = 1
            isFocusable = true
            isEnabled = true
            setPadding(0, 0, 0, 0)
        }
    }

    // Function to change the border color of the circles to green
    private fun setBorderColorGreen(passcodeInputs: List<EditText>, context: Context) {
        passcodeInputs.forEach {
            val drawable: Drawable? = context.getDrawable(R.drawable.circle_border_filled) // Create a drawable with a green border
            it.background = drawable
        }
    }

    // Method to show the passcode dialog
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

        // Create a linear layout to hold the circles
        val passcodeLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(20, 20, 20, 20)

            // Add 5 input circles for passcode
            val passcodeInputs = mutableListOf<EditText>()
            for (i in 1..5) {
                val passcodeCircle = createPasscodeCircle(context)
                passcodeInputs.add(passcodeCircle)
                addView(passcodeCircle)
            }

            // TextWatcher for auto-filling the passcode
            passcodeInputs.forEachIndexed { index, passcodeCircle ->
                passcodeCircle.addTextChangedListener(object : android.text.TextWatcher {
                    override fun afterTextChanged(s: android.text.Editable?) {
                        if (s?.length == 1 && index < passcodeInputs.size - 1) {
                            passcodeInputs[index + 1].requestFocus()
                        }

                        // Check if all fields are filled, and change the border color to green
                        if (passcodeInputs.all { it.text.isNotEmpty() }) {
                            setBorderColorGreen(passcodeInputs, context)
                        } else {
                            // Reset to the original border color if not all are filled
                            passcodeInputs.forEach {
                                it.background = context.getDrawable(R.drawable.circle_border)
                            }
                        }
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
        }

        // Message text
        val attemptsMessage = TextView(context).apply {
            textSize = 14f
            setPadding(0, 10, 0, 10)
            gravity = Gravity.CENTER
            text = if (attempts >= maxAttempts) {
                "You are locked out! Try again after 30 minutes."
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

            // Create a shape drawable with rounded corners and light blue background
            val shapeDrawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f  // Adjust this value to control the roundness of the corners
                setColor(Color.parseColor("#ADD8E6"))  // Light blue background color
            }

            // Set the shape drawable as the background of the TextView
            background = shapeDrawable

            setPadding(20, 20, 20, 20)  // Adjust padding to suit your layout
        }



        // Now build the layout for the dialog
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 10)
            addView(getDialogHeader(context))
            addView(messageText)
            addView(passcodeLayout)  // Add the passcode input layout
            addView(attemptsMessage)
        }

        // Dialog creation with proper handling of passcode inputs
        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setPositiveButton("Confirm") { dialog, _ ->
                if (attempts >= maxAttempts) {
                    Toast.makeText(context, "You are locked out!", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }

                val passcode = passcodeLayout.children.joinToString("") { (it as EditText).text.toString() }

                if (passcode.length != 5) {
                    Toast.makeText(context, "Passcode must be 5 digits.", Toast.LENGTH_SHORT).show()
                } else if (passcode.isNotEmpty()) {
                    onSubmit(passcode)
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()

        dialog.show()
    }

    // Helper method to load the custom header (this can be adjusted based on your UI needs)
    private fun getDialogHeader(context: Context): LinearLayout {
        val header = LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as? LinearLayout
        return header ?: LinearLayout(context)  // Return a default layout if inflation fails
    }
}
