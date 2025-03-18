package com.example.mathoperation.dialogs

import android.content.Context
import com.example.mathoperation.LoadingDialog
object Dialogs {

    fun showLoadingDialog(context: Context, onFinish: () -> Unit) {
        LoadingDialog.showLoadingDialog(context, onFinish)
    }

    fun showSummaryDialog(context: Context, businessName: String, userName: String, itemsPurchased: String, totalAmount: Double, onNext: (Double) -> Unit) {
        SummaryDialog.showSummaryDialog(context, businessName, userName, itemsPurchased, totalAmount, onNext)
    }

    fun showPasscodeDialog(context: Context, amount: Int, attempts: Int, maxAttempts: Int, onSubmit: (String) -> Unit) {
        PasscodeDialog.showPasscodeDialog(context, amount, attempts, maxAttempts, onSubmit)
    }

    fun showPaymentStatus(context: Context, isSuccess: Boolean) {
        val remainingAttempts = 0
        PaymentStatusDialog.showPaymentStatus(context, isSuccess, remainingAttempts)
    }

    fun showInsufficientBalance(context: Context) {
        InsufficientBalanceDialog.showInsufficientBalance(context)
    }
}
