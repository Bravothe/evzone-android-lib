package com.example.mathoperation

import android.content.Context
import com.example.mathoperation.MathOperation.CORRECT_PASSCODE

data class VatDetails(val vat: Double, val inclusiveVat: Double, val totalAmount: Double)

object MathOperations {

    // Helper method for business logic
    fun calculateVatAndTotal(amount: Double): VatDetails {
        val vat = amount * 0.01  // 1% VAT
        val inclusiveVat = amount * 0.015  // 1.5% Inclusive VAT
        val totalAmount = amount + vat + inclusiveVat  // Total amount to be deducted
        return VatDetails(vat, inclusiveVat, totalAmount)  // Return vat, inclusiveVat, and totalAmount
    }

    // Business logic related to showing different dialogs or calculations
    fun showSummaryDialog(
        context: Context,
        businessName: String,
        userName: String,
        itemsPurchased: String,
        totalAmount: Double,
        onNext: (Double) -> Unit
    ) {
        Dialogs.showSummaryDialog(context, businessName, userName, itemsPurchased, totalAmount, onNext)
    }

    fun showPasscodeDialog(
        context: Context,
        amount: Int,
        maxAttempts: Double,
        onSubmit: (String) -> Unit
    ) {
        // Instead of passing the correct passcode around, use the global constant CORRECT_PASSCODE
        Dialogs.showPasscodeDialog(context, amount, CORRECT_PASSCODE, maxAttempts, onSubmit)
    }

    fun showPaymentStatus(
        context: Context,
        isSuccess: Boolean,
        remainingAttempts: Int = 0 // Default value to avoid errors
    )
    {
        Dialogs.showPaymentStatus(context, isSuccess, remainingAttempts)
    }

    fun showInsufficientBalance(context: Context) {
        Dialogs.showInsufficientBalance(context)
    }
}
