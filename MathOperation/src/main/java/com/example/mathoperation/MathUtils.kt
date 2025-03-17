package com.example.mathoperation

import android.content.Context
import android.widget.Toast
import com.example.mathoperation.Dialogs.showLoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MathOperation {

    var walletBalance: Double = 1000.0
    private var passcodeAttempts = 0  // Track the number of passcode attempts
    private const val MAX_ATTEMPTS = 3  // Maximum allowed attempts
    const val CORRECT_PASSCODE = 1234 // Correct passcode for validation
    private var isLockedOut = false  // Flag to indicate if the user is locked out

    fun startPayment(context: Context, businessName: String, userName: String, itemsPurchased: String, totalAmount: Double) {
        showLoadingDialog(context) {
            showProductSummary(context, businessName, userName, itemsPurchased, totalAmount)
        }
    }

    private fun showProductSummary(context: Context, businessName: String, userName: String, itemsPurchased: String, totalAmount: Double) {
        Dialogs.showSummaryDialog(context, businessName, userName, itemsPurchased, totalAmount) { amount ->
            showAmountDeductionDialog(context, amount)
        }
    }

    private fun showAmountDeductionDialog(context: Context, amount: Double) {
        Dialogs.showPasscodeDialog(context,
            amount.toInt(), passcodeAttempts, MAX_ATTEMPTS.toDouble()
        ) { passcode ->
            if (isLockedOut) {
                // Inform user they are locked out and prevent further attempts
                Toast.makeText(context, "You are locked out! Please try again later.", Toast.LENGTH_LONG).show()
            } else if (isPasscodeCorrect(passcode)) {
                passcodeAttempts = 0  // Reset attempts on success
                processPayment(context, amount)
            } else {
                passcodeAttempts++
                val remainingAttempts = MAX_ATTEMPTS - passcodeAttempts

                if (passcodeAttempts >= MAX_ATTEMPTS) {
                    lockUserOut()  // Lock the user out if max attempts are reached
                } else {
                    MathOperations.showPaymentStatus(context, false, remainingAttempts) // Show remaining attempts
                }
            }
        }
    }

    private fun processPayment(context: Context, amount: Double) {
        if (walletBalance >= amount) {
            walletBalance -= amount
            MathOperations.showPaymentStatus(context, true)  // Payment success
        } else {
            MathOperations.showInsufficientBalance(context)  // Insufficient balance
        }
    }

    private fun isPasscodeCorrect(passcode: String): Boolean {
        // Convert the passcode to Int and compare
        return passcode.toIntOrNull() == CORRECT_PASSCODE
    }

    private fun lockUserOut() {
        isLockedOut = true
        CoroutineScope(Dispatchers.IO).launch {
            delay(30 * 60 * 1000)  // Lockout for 30 minutes
            isLockedOut = false  // Reset the lockout after the delay
        }
    }
}
