package com.example.mathoperation

import android.content.Context

object Dialogs {
    fun showLoadingDialog(context: Context, onFinish: () -> Unit) {
        LoadingDialog.showLoadingDialog(context, onFinish)
    }

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

    fun showPaymentStatus(context: Context, isSuccess: Boolean) {
        PaymentStatusDialog.showPaymentStatus(context, isSuccess, 0)
    }

    fun showInsufficientBalance(context: Context) {
        InsufficientBalanceDialog.showInsufficientBalance(context)
    }
}