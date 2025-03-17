package com.example.mathoperation

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

object Dialogs {

    // Helper method to load the custom header
    private fun getDialogHeader(context: Context): LinearLayout {
        val headerLayout = LayoutInflater.from(context).inflate(R.layout.dialog_header, null) as LinearLayout
        return headerLayout
    }

    // Method to apply custom animations to the dialog
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
                        .error(R.drawable.evzone)  // Fallback logo
                        .into(this)
                    layoutParams = LinearLayout.LayoutParams(200, 200)  // Adjust size as needed
                }
                addView(logoImage)

                // Add the blinking company name
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
                        } else {
                            companyNameText.setTextColor(Color.parseColor("#FFA500")) // Orange color
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

        // Apply fade-in/fade-out animations
        applyDialogAnimations(loadingDialog)

        loadingDialog.show()

        // Simulate a delay before showing the next dialog
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000) // Delay of 2 seconds before showing the next dialog
            onFinish()  // Transition to the next dialog
            loadingDialog.dismiss()
        }
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

    fun showPasscodeDialog(
        context: Context,
        amount: Int,
        attempts: Int,
        maxAttempts: Int,
        onSubmit: (String) -> Unit
    ) {
        val serviceFeePercentage = 5.0  // Example service fee percentage
        val vatPercentage = 15.0  // Example VAT percentage

        // Calculate service fee and VAT
        val serviceFee = amount * (serviceFeePercentage / 100)
        val vat = amount * (vatPercentage / 100)

        val totalAmount = amount + serviceFee + vat

        val inputPasscode = EditText(context).apply {
            hint = "Enter Passcode"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD
            gravity = Gravity.CENTER
            setPadding(20, 20, 20, 20)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            isEnabled = attempts < maxAttempts // Disable input if max attempts reached
        }

        val attemptsMessage = TextView(context).apply {
            textSize = 14f
            setPadding(0, 10, 0, 10)
            gravity = Gravity.CENTER

            if (attempts >= maxAttempts) {
                text = "You are locked out! Try again later."
                setTextColor(Color.RED)
            } else {
                val remaining = maxAttempts - attempts
                text = "You have $remaining attempt(s) left."
                setTextColor(Color.RED)
            }
        }

        // Create the paragraph for the dialog message
        val message = "A total of $$totalAmount will be deducted from your Evzone Pay Wallet, " +
                "including Service Fee: $$serviceFee and VAT: $$vat. Enter the passcode to approve the Transaction."

        val messageText = TextView(context).apply {
            text = message
            gravity = Gravity.CENTER
            textSize = 16f
        }

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 10)
            addView(getDialogHeader(context))  // Add header
            addView(messageText)  // Show the formatted message

            // Add the input passcode and attempts message conditionally
            if (attempts < maxAttempts) {
                addView(inputPasscode)  // Show input only if attempts < max
                addView(attemptsMessage)  // Position attemptsMessage below passcode input
            } else {
                addView(attemptsMessage)  // Show locked-out message if attempts exceeded
            }
        }

        val dialog = AlertDialog.Builder(context)
            .setView(layout)
            .setPositiveButton("Submit") { dialog, _ ->
                val passcode = inputPasscode.text.toString()
                if (passcode.isNotEmpty() && attempts < maxAttempts) {
                    onSubmit(passcode)  // Submit passcode
                    dialog.dismiss()
                } else if (attempts >= maxAttempts) {
                    Toast.makeText(context, "You are locked out!", Toast.LENGTH_LONG).show()  // Only show after attempts are exceeded
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create()

        applyDialogAnimations(dialog)
        dialog.show()
    }




    // Method to show payment status dialog
    fun showPaymentStatus(context: Context, isSuccess: Boolean, remainingAttempts: Int) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER  // Center all elements horizontally

            // Add the custom header at the top
            addView(getDialogHeader(context))  // Header is always at the top

            // Add the detailed message below the header
            val detailedMessage = TextView(context).apply {
                text = if (isSuccess) "Your payment was processed successfully!" else "Wrong Passcode. Try again."
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
                text = "Your wallet balance is too low to complete this payment. Thank You"
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
