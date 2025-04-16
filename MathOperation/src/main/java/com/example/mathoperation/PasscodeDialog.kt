package com.example.mathoperation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt

object PasscodeDialog {
    private lateinit var dialog: AlertDialog

    @SuppressLint("SetTextI18n")
    fun showPasscodeDialog(
        context: Context,
        merchantName: String,
        transactionId: String,
        amount: String,
        tax: String,
        walletFee: String,
        onSubmit: (String) -> Unit
    ) {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 40)
            setBackgroundColor(Color.WHITE)
        }

        val headerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, 0, 0, 20)
        }

        val logo = ImageView(context).apply {
            setImageResource(R.drawable.evzone)
            layoutParams = LinearLayout.LayoutParams(80, 80)
        }

        val title = TextView(context).apply {
            text = "EVzone Pay"
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val closeButton = ImageView(context).apply {
            setImageResource(R.drawable.gray)
            layoutParams = LinearLayout.LayoutParams(50, 50)
            setOnClickListener { dialog.dismiss() }
        }

        headerLayout.addView(logo)
        headerLayout.addView(title)
        headerLayout.addView(closeButton)

        val merchantText = TextView(context).apply {
            val spannable = SpannableString("Merchant Info:\n$merchantName \n$transactionId \t\t\t\t\t$amount")
            val startMerchantInfo = 0
            val endMerchantInfo = "Merchant Info:".length
            val softGreen = "#4CAF50".toColorInt()

            spannable.setSpan(ForegroundColorSpan(softGreen), startMerchantInfo, endMerchantInfo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(StyleSpan(Typeface.BOLD), startMerchantInfo, endMerchantInfo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(AbsoluteSizeSpan(24, true), startMerchantInfo, endMerchantInfo, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val startAmount = spannable.length - amount.length
            val endAmount = spannable.length
            spannable.setSpan(StyleSpan(Typeface.BOLD), startAmount, endAmount, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            text = spannable
            textSize = 18f
            setTextColor(Color.BLACK)
            gravity = Gravity.START
            setPadding(0, 0, 50, 0)
        }

        val passcodeLabel = TextView(context).apply {
            text = "Enter Passcode "
            textSize = 16f
            setTextColor(Color.BLACK)
            gravity = Gravity.START
            setPadding(20, 20, 20, 10)
        }

        val passcodeLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(10, 10, 10, 10)
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 30)
            }
            background = context.getDrawable(R.drawable.passcode_input_border)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = 5f
            }
        }

        val passcodeInput = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            setPadding(20, 20, 20, 20)
            maxLines = 1
            minHeight = 100
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            gravity = Gravity.CENTER
            background = null
            filters = arrayOf(InputFilter.LengthFilter(6))
        }

        val toggleVisibility = ImageView(context).apply {
            setImageResource(R.drawable.ic_eye_off)
            layoutParams = LinearLayout.LayoutParams(80, 80)
            setPadding(10, 10, 10, 10)
            setOnClickListener {
                val selection = passcodeInput.selectionStart
                if (passcodeInput.inputType == (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD)) {
                    passcodeInput.inputType = InputType.TYPE_CLASS_NUMBER
                    setImageResource(R.drawable.ic_eye_on)
                } else {
                    passcodeInput.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                    setImageResource(R.drawable.ic_eye_off)
                }
                passcodeInput.setSelection(selection)
            }
        }

        passcodeLayout.addView(passcodeInput)
        passcodeLayout.addView(toggleVisibility)

        val infoBox = TextView(context).apply {
            text = "You are making a payment to $merchantName.\nAmount UGX $amount will be deducted from your wallet, including $tax tax and $walletFee wallet fee."
            textSize = 14f
            setTextColor(Color.BLACK)
            setPadding(20, 20, 20, 20)
            gravity = Gravity.CENTER
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor(Color.parseColor("#D9EFFF"))
            }
        }

        val buttonsLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 20, 0, 0)
        }

        val confirmButton = Button(context).apply {
            text = "Confirm"
            textSize = 16f
            setTextColor(Color.WHITE)
            setPadding(20, 20, 20, 20)
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor(ContextCompat.getColor(context, R.color.blue))
            }
            isAllCaps = false
            setOnClickListener {
                val passcode = passcodeInput.text.toString()
                if (passcode.length != 6) {
                    Toast.makeText(context, "Passcode must be 6 digits.", Toast.LENGTH_SHORT).show()
                } else {
                    onSubmit(passcode)
                    dialog.dismiss()
                }
            }
        }

        val backButton = Button(context).apply {
            text = "Back"
            textSize = 16f
            setTextColor(Color.RED)
            setBackgroundColor(Color.TRANSPARENT)
            setPadding(20, 20, 20, 20)
            setOnClickListener { dialog.dismiss() }
            isAllCaps = false
        }

        buttonsLayout.addView(confirmButton)
        buttonsLayout.addView(backButton)

        layout.addView(headerLayout)
        layout.addView(merchantText)
        layout.addView(passcodeLabel)
        layout.addView(passcodeLayout)
        layout.addView(infoBox)
        layout.addView(buttonsLayout)

        // Apply the custom theme with rounded corners and animations
        dialog = AlertDialog.Builder(context, R.style.CustomDialogTheme)
            .setView(layout)
            .setCancelable(false)
            .create()

        dialog.show()
    }
}