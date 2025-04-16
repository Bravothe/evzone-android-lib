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
import android.view.View
import android.view.animation.AnimationUtils
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

        // Header Layout
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

        // Dark Blue Clickable Section
        val darkBlueSection = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor("#0058FF".toColorInt())
            }
        }

        val amountUsdText = TextView(context).apply {
            text = "THE RECEIVER ACCEPTS MONEY IN USD YOU ARE ABOUT TO SEND AN EQUIVALENT OF USD 5"
            textSize = 14f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 10)
        }

        darkBlueSection.addView(amountUsdText)

        // Toggleable Light Blue Section (Rate Info)
        val rateInfoSection = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor("#D9EFFF".toColorInt())
            }
            visibility = View.GONE // Initially hidden
        }

        val rateText = TextView(context).apply {
            text = "Rate: UGX 1 = USD 0.000027\nAMOUNT REQUIRED: UGX 1000"
            textSize = 14f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
        }

        val noteText = TextView(context).apply {
            text = "(Please note: current rates and charges apply)"
            textSize = 12f
            setTextColor(Color.RED)
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 0)
        }

        rateInfoSection.addView(rateText)
        rateInfoSection.addView(noteText)

        // Toggle Animation for Rate Info Section
        val slideDown = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        val slideUp = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)

        darkBlueSection.setOnClickListener {
            if (rateInfoSection.visibility == View.GONE) {
                rateInfoSection.visibility = View.VISIBLE
                rateInfoSection.startAnimation(slideDown)
            } else {
                rateInfoSection.startAnimation(slideUp)
                rateInfoSection.visibility = View.GONE
            }
        }

        // Merchant Info
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

        // Passcode Input
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

        // Transaction Description (Light Blue Background, Always Visible)
        val transactionDescriptionSection = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(20, 20, 20, 20)
            gravity = Gravity.CENTER_VERTICAL
            background = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                cornerRadius = 20f
                setColor("#D9EFFF".toColorInt())
            }
        }

        val infoIcon = ImageView(context).apply {
            setImageResource(R.drawable.ic) // Add ic_info drawable (blue circle with white "i")
            layoutParams = LinearLayout.LayoutParams(40, 40)
            setPadding(0, 0, 10, 0)
        }

        val transactionDescription = TextView(context).apply {
            text = "You are making a payment to $merchantName.\nAmount UGX $amount will be deducted from your wallet, including $tax tax and $walletFee wallet fee."
            textSize = 14f
            setTextColor(Color.BLACK)
        }

        transactionDescriptionSection.addView(infoIcon)
        transactionDescriptionSection.addView(transactionDescription)

        // Buttons
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

        // Add all views to the main layout in the correct order
        layout.addView(headerLayout)
        layout.addView(darkBlueSection)
        layout.addView(rateInfoSection)
        layout.addView(merchantText)
        layout.addView(passcodeLabel)
        layout.addView(passcodeLayout)
        layout.addView(transactionDescriptionSection)
        layout.addView(buttonsLayout)

        // Apply the custom theme with rounded corners and animations
        dialog = AlertDialog.Builder(context, R.style.CustomDialogTheme)
            .setView(layout)
            .setCancelable(false)
            .create()

        dialog.show()
    }
}