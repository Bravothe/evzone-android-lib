package com.example.mathoperation

import android.content.Context

object MathOperations {

    // Business logic related to showing different dialogs or calculations
    fun showSummaryDialog(
        context: Context,
        businessName: String,
        userName: String,
        itemsPurchased: String,
        currency: String,
        totalAmount: Double,
        businessLogoUrl: String,
        onNext: (Double) -> Unit
    ) {
        SummaryDialog.showSummaryDialog(context, businessName, userName, itemsPurchased, currency, totalAmount, businessLogoUrl, onNext)
    }

    fun showPaymentStatus(
        context: Context,
        isSuccess: Boolean,
        remainingAttempts: Int = 0 // Default value to avoid errors
    ) {
        PaymentStatusDialog.showPaymentStatus(context, isSuccess, remainingAttempts)
    }

    fun showInsufficientBalance(context: Context) {
        InsufficientBalanceDialog.showInsufficientBalance(context)
    }
}