package com.example.mathoperation

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide

object Dialogs {

    // Helper method to load the custom header
    private fun getDialogHeader(context: Context): LinearLayout {
        val headerLayout = LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
        return headerLayout
    }

    private fun applyDialogAnimations(dialog: AlertDialog) {
        dialog.window?.setWindowAnimations(R.style.DialogAnimation)
    }

    // Method to show the loading dialog
    fun showLoadingDialog(context: Context, onFinish: () -> Unit) {
        val loadingDialog = AlertDialog.Builder(context).apply {
            val loadingLayout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER

                // Add the company logo
                val logoImage = ImageView(context).apply {
                    Glide.with(context)
                        .load(R.drawable.evzone)  // Replace with your company logo
                        .into(this)
                    layoutParams = LinearLayout.LayoutParams(200, 200)  // Adjust size as needed
                }
                addView(logoImage)

                // Add the blinking company name (without other headers)
                val companyNameText = TextView(context).apply {
                    text = "Evzone Pay"
                    textSize = 24f
                    gravity = Gravity.CENTER
                }
                addView(companyNameText)

                // Blinking effect for the text
                val handler = Handler(Looper.getMainLooper())
                val blinkingRunnable = object : Runnable {
                    var isGreen = true
                    override fun run() {
                        if (isGreen) {
                            companyNameText.setTextColor(Color.GREEN)
                            companyNameText.text = "Evzone Pay"  // Reset text on each blink
                        } else {
                            companyNameText.setTextColor(Color.parseColor("#FFA500")) // Orange color
                            companyNameText.text = "Evzone Pay"  // Reset text on each blink
                        }
                        isGreen = !isGreen
                        handler.postDelayed(this, 500) // Toggle the color every 500ms
                    }
                }

                handler.post(blinkingRunnable)
            }
            setView(loadingLayout)
            setCancelable(false)
        }.create()

        // Apply fade-in/fade-out animations (if needed)
        applyDialogAnimations(loadingDialog)

        loadingDialog.show()

        // Simulate a delay before showing the next dialog
        Handler(Looper.getMainLooper()).postDelayed({
            onFinish()  // Transition to the next dialog after 2 seconds
            loadingDialog.dismiss() // Dismiss loading dialog
        }, 2000) // Delay of 2 seconds before showing the next dialog
    }

    // Method to show the summary dialog
    fun showSummaryDialog(context: Context, businessName: String, userName: String, itemsPurchased: String, totalAmount: Double, onNext: (Double) -> Unit) {
        val summary = """
            Business: $businessName
            User: $userName
            Items Purchased: $itemsPurchased
            Total Amount: $$totalAmount
        """.trimIndent()

        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(getDialogHeader(context))  // Add the custom header
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

        applyDialogAnimations(dialog)

        dialog.show()
    }

    // Method to show passcode dialog
    fun showPasscodeDialog(context: Context, amount: Double, onSubmit: (String) -> Unit) {
        val inputPasscode = EditText(context).apply {
            hint = "Enter Passcode"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
            gravity = Gravity.CENTER
            setPadding(20, 20, 20, 20) // Reduced padding to make it more compact
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                weight = 1f  // Make the EditText expand to fill available space
            }
        }

        // Create an ImageView for the eye icon (visible/hide)
        val eyeIcon = ImageView(context).apply {
            setImageResource(R.drawable.ic_eye_off) // Initially set the "eye off" icon (hidden)
            layoutParams = LinearLayout.LayoutParams(48, 48).apply {  // Set a smaller size for the eye icon
                marginStart = 16  // Add some margin on the left side of the icon
                gravity = Gravity.CENTER_VERTICAL  // Center the icon vertically
            }
            setOnClickListener {
                // Toggle the input type to show or hide the password
                if (inputPasscode.inputType == android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD) {
                    inputPasscode.inputType = android.text.InputType.TYPE_CLASS_NUMBER  // Show password
                    setImageResource(R.drawable.ic_eye_on)  // Change to "eye on" icon (visible)
                } else {
                    inputPasscode.inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD  // Hide password
                    setImageResource(R.drawable.ic_eye_off)  // Change to "eye off" icon (hidden)
                }
                inputPasscode.setSelection(inputPasscode.text.length)  // Keep the cursor at the end after toggle
            }
        }

        // Create a layout for the passcode input field and the eye icon
        val inputLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL  // Align both the EditText and ImageView vertically
            setPadding(20, 0, 20, 0)  // Reduce the padding for better fit
            addView(inputPasscode)  // Add the passcode input field
            addView(eyeIcon)  // Add the eye icon next to the EditText
        }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 10)

            // Add the custom header at the top
            addView(getDialogHeader(context))  // The header stays at the top

            // Calculate VAT and Inclusive VAT based on the amount
            val vat = amount * 0.01  // 1% VAT
            val inclusiveVat = amount * 0.015  // 1.5% Inclusive VAT
            val totalAmount = amount + vat + inclusiveVat  // Total amount to be deducted

            // Create the message with calculated VAT and Inclusive VAT
            val messageText = """
        Amount to be deducted: UGX $amount
        VAT (1.0%): UGX $vat
        Inclusive VAT (1.5%): UGX $inclusiveVat
        Total: UGX $totalAmount
    """.trimIndent()

            // Create a SpannableString to make the amount bold
            val spannableMessage = SpannableString(messageText).apply {
                val amountStartIndex = messageText.indexOf("$amount")
                val amountEndIndex = amountStartIndex + "$amount".length

                // Make the amount bold
                setSpan(StyleSpan(Typeface.BOLD), amountStartIndex, amountEndIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            // Add the payment confirmation message with dynamic values
            val message = TextView(context).apply {
                text = spannableMessage
                gravity = Gravity.START  // Align text to the left for a clean, professional look
                textSize = 16f
                setPadding(0, 20, 0, 20)  // Add padding for better separation
            }
            addView(message)  // Add the message below the header

            // Add the input layout with the passcode field and eye icon
            addView(inputLayout)  // The passcode input field with icon
        }


        val dialog = AlertDialog.Builder(context)
            .setView(layout)  // Set the custom layout
            .setPositiveButton("Submit") { dialog, _ ->
                val passcode = inputPasscode.text.toString()
                if (passcode.isNotEmpty()) {
                    onSubmit(passcode)  // Submit passcode
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Passcode required!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()

        applyDialogAnimations(dialog)

        dialog.show()
    }

    // Method to show payment status dialog
    fun showPaymentStatus(context: Context, isSuccess: Boolean) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER  // Center all elements horizontally

            // Add the custom header at the top
            addView(getDialogHeader(context))  // Header is always at the top

            // Add the detailed message below the header
            val detailedMessage = TextView(context).apply {
                text = if (isSuccess) "Your payment was processed successfully!" else "Something went wrong. Try again."
                gravity = Gravity.CENTER
                textSize = 16f
                setPadding(0, 20, 0, 20)  // Padding to separate it from the header
            }
            addView(detailedMessage)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)  // Set the custom layout
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setIcon(if (isSuccess) R.drawable.check else R.drawable.error) // Set check or error icon
            .create()

        applyDialogAnimations(dialog)

        dialog.show()
    }

    // Method to show insufficient balance dialog
    fun showInsufficientBalance(context: Context) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            addView(getDialogHeader(context))  // Add the custom header
            val insufficientBalanceMessage = TextView(context).apply {
                text = "Your wallet balance is too low to complete this payment."
                gravity = Gravity.CENTER
                textSize = 16f
            }
            addView(insufficientBalanceMessage)
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setIcon(R.drawable.error) // Set your error icon
            .create()

        applyDialogAnimations(dialog)

        dialog.show()
    }
}
