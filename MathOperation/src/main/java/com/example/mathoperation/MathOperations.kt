package com.example.mathoperation
import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MathOperations {

    // Helper method for business logic
    fun calculateVatAndTotal(amount: Double): Pair<Double, Double> {
        val vat = amount * 0.01  // 1% VAT
        val inclusiveVat = amount * 0.015  // 1.5% Inclusive VAT
        val totalAmount = amount + vat + inclusiveVat  // Total amount to be deducted
        return Pair(vat, inclusiveVat)
    }

    // Business logic related to showing different dialogs or calculations
    fun showSummaryDialog(context: Context, businessName: String, userName: String, itemsPurchased: String, totalAmount: Double, onNext: (Double) -> Unit) {
        val (vat, inclusiveVat) = calculateVatAndTotal(totalAmount)

        val summary = """
            Business: $businessName
            User: $userName
            Items Purchased: $itemsPurchased
            Total Amount: $$totalAmount
        """.trimIndent()

        // Proceed to UI portion (refactor in Dialogs class)
        Dialogs.showSummaryDialog(context, businessName, userName, itemsPurchased, totalAmount, onNext)
    }

    fun showPasscodeDialog(context: Context, amount: Double, onSubmit: (String) -> Unit) {
        val (vat, inclusiveVat) = calculateVatAndTotal(amount)

        // Proceed to UI portion (refactor in Dialogs class)
        Dialogs.showPasscodeDialog(context, amount, onSubmit)
    }

    fun showPaymentStatus(context: Context, isSuccess: Boolean) {
        // Proceed to UI portion (refactor in Dialogs class)
        Dialogs.showPaymentStatus(context, isSuccess)
    }

    fun showInsufficientBalance(context: Context) {
        // Proceed to UI portion (refactor in Dialogs class)
        Dialogs.showInsufficientBalance(context)
    }

    // Additional helper methods can go here if needed
}
