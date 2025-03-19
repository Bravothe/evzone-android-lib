package com.example.mathoperation

import android.content.Context
import android.widget.Toast
import com.example.mathoperation.dialogs.PasscodeDialog
import com.example.mathoperation.dialogs.SummaryDialog
import com.example.mathoperation.dialogs.UsernameErrorDialog
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
        userName: String?,
        itemsPurchased: String,
        currency: String,
        totalAmount: Double,
        walletid: String
    ) {
        if (userName.isNullOrEmpty()) {
            UsernameErrorDialog.showUsernameErrorDialog(context) { enteredUsername ->
                // Use the entered username to restart the payment process
                startPayment(context, businessName, enteredUsername, itemsPurchased, currency, totalAmount, walletid)
            }
        } else {
            LoadingDialog.showLoadingDialog(context) {
                showProductSummary(context, businessName, userName, itemsPurchased, currency, totalAmount, walletid)
            }
        }
    }

    private fun showProductSummary(
        context: Context,
        businessName: String,
        userName: String,
        itemsPurchased: String,
        currency: String,
        totalAmount: Double,
        walletid: String
    ) {
        SummaryDialog.showSummaryDialog(context, businessName, userName, itemsPurchased, currency, totalAmount) { amount ->
            showAmountDeductionDialog(context, amount, walletid, businessName, currency)
        }
    }

    private fun showAmountDeductionDialog(
        context: Context,
        amount: Double,
        walletid: String,
        businessName: String,
        currency: String
    ) {
        val amountWithCurrency = "$currency ${amount.format(2)}"

        PasscodeDialog.showPasscodeDialog(
            context,
            businessName,
            walletid,
            amountWithCurrency,
            "UGX 500",
            "UGX 100"
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

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)
}