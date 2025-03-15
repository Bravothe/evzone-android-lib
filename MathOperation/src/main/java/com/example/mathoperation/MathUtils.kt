package com.example.mathoperation

import android.content.Context
import com.example.mathoperation.Dialogs.showLoadingDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MathOperation {

    var walletBalance: Double = 1000.0
    private var passcodeAttempts = 0  // Track the number of passcode attempts
    private const val MAX_ATTEMPTS = 3  // Maximum allowed attempts
    private const val CORRECT_PASSCODE = "1234"  // Correct passcode for validation
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
        Dialogs.showPasscodeDialog(context, amount) { passcode ->
            if (isPasscodeCorrect(passcode)) {
                processPayment(context, amount)
            } else {
                passcodeAttempts++
                if (passcodeAttempts >= MAX_ATTEMPTS) {
                    lockUserOut()
                }
            }
        }
    }

    private fun processPayment(context: Context, amount: Double) {
        if (walletBalance >= amount) {
            walletBalance -= amount
            Dialogs.showPaymentStatus(context, true)
        } else {
            Dialogs.showInsufficientBalance(context)
        }
    }

    private fun isPasscodeCorrect(passcode: String): Boolean {
        return passcode == CORRECT_PASSCODE
    }

    private fun lockUserOut() {
        isLockedOut = true
        CoroutineScope(Dispatchers.Main).launch {
            delay(30 * 60 * 1000)  // Lockout for 30 minutes
            isLockedOut = false
        }
    }
}
