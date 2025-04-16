package com.example.mathoperation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import kotlinx.coroutines.*

object MathOperation {
    var walletBalance: Double = 100000.0
    private var passcodeAttempts = 0
    private const val MAX_ATTEMPTS = 3
    const val CORRECT_PASSCODE = 123456
    private var isLockedOut = false

    // EVzone login URL and redirect URI
    private const val EXTERNAL_LOGIN_URL = "https://accounts.evzone.app/"
    private const val REDIRECT_URI = "com.example.mathoperation://callback"

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
            // Redirect to EVzone for login
            val loginUrl = "$EXTERNAL_LOGIN_URL?redirect_uri=$REDIRECT_URI&client_id=YOUR_CLIENT_ID"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(loginUrl))
            context.startActivity(intent)
        } else {
            LoadingDialog.showLoadingDialog(context) {
                showProductSummary(context, businessName, userName, itemsPurchased, currency, totalAmount, walletid)
            }
        }
    }

    // Function to handle the callback from EVzone and capture login status
    fun handleLoginCallback(context: Context, data: Intent?, onUserAuthenticated: (String) -> Unit) {
        data?.data?.let { uri ->
            if (uri.toString().startsWith(REDIRECT_URI)) {
                // Extract login status and username from the callback URI
                val status = uri.getQueryParameter("status") // e.g., "success" or "failed"
                val username = uri.getQueryParameter("username")
                val error = uri.getQueryParameter("error")

                if (status == "success" && username != null) {
                    // Login was successful, proceed with the payment process
                    Toast.makeText(context, "Login successful for $username", Toast.LENGTH_SHORT).show()
                    onUserAuthenticated.invoke(username)
                } else if (error != null) {
                    // Login failed, show an error message
                    Toast.makeText(context, "Login failed: $error", Toast.LENGTH_LONG).show()
                } else {
                    // Unexpected response, handle as a failure
                    Toast.makeText(context, "Login failed: Unable to verify login status", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Invalid callback URI", Toast.LENGTH_LONG).show()
            }
        } ?: run {
            Toast.makeText(context, "No callback data received", Toast.LENGTH_LONG).show()
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