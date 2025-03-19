package com.example.mathoperation

import android.content.Context
import android.widget.Toast
import com.example.mathoperation.dialogs.PasscodeDialog
import com.example.mathoperation.dialogs.SummaryDialog
import com.example.mathoperation.dialogs.Dialogs
import kotlinx.coroutines.*

object MathOperation {
    var walletBalance: Double = 1000.0
    private var passcodeAttempts = 0
    private const val MAX_ATTEMPTS = 3
    const val CORRECT_PASSCODE = 123456
    private var isLockedOut = false

    fun startPayment(
        context: Context,
        businessName: String,
        userName: String,
        itemsPurchased: String,
        currency: String,
        totalAmount: Double
    ) {
        LoadingDialog.showLoadingDialog(context) {
            showProductSummary(context, businessName, userName, itemsPurchased, currency, totalAmount)
        }
    }

    private fun showProductSummary(
        context: Context, businessName: String, userName: String, itemsPurchased: String,
        currency: String, totalAmount: Double
    ) {
        SummaryDialog.showSummaryDialog(context, businessName, userName, itemsPurchased, currency, totalAmount) { amount ->
            showAmountDeductionDialog(context, amount)
        }
    }

    private fun showAmountDeductionDialog(context: Context, amount: Double) {
        PasscodeDialog.showPasscodeDialog(
            context, "Merchant Name", "TXN123456", amount.toString(), "UGX 500", "UGX 100"
        ) { passcode ->
            if (isLockedOut) {
                Toast.makeText(context, "You are locked out! Please try after 30 minutes.", Toast.LENGTH_LONG).show()
            } else if (isPasscodeCorrect(passcode)) {
                passcodeAttempts = 0
                processPayment(context, amount)
            } else {
                passcodeAttempts++
                if (passcodeAttempts >= MAX_ATTEMPTS) lockUserOut()
                else Dialogs.showPaymentStatus(context, false)
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
        return passcode.padStart(5, '0') == CORRECT_PASSCODE.toString()
    }

    private fun lockUserOut() {
        isLockedOut = true
        CoroutineScope(Dispatchers.Main).launch {
            delay(30 * 60 * 1000)
            isLockedOut = false
        }
    }
}
