package com.example.mathoperation

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import java.text.DecimalFormat
import androidx.core.graphics.toColorInt

object SummaryDialog {

    private lateinit var dialog: AlertDialog

    private fun createLabelValuePair(
        context: Context,
        label: String,
        value: String,
        isTotal: Boolean = false
    ): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 12, 0, 12)

            val labelTextView = TextView(context).apply {
                text = label
                textSize = 16f
                setTextColor(Color.DKGRAY)
                typeface = Typeface.DEFAULT_BOLD
                gravity = Gravity.START
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val valueTextView = TextView(context).apply {
                text = value
                textSize = 16f
                setTextColor(if (isTotal) "#009900".toColorInt() else Color.BLACK)
                typeface = if (isTotal) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                gravity = Gravity.END
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            addView(labelTextView)
            addView(valueTextView)
        }
    }

    fun showSummaryDialog(
        context: Context, businessName: String, userName: String, itemsPurchased: String,
        currency: String, totalAmount: Double, onNext: (Double) -> Unit
    ) {
        val dialogLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
            gravity = Gravity.CENTER

            val cardContainer = CardView(context).apply {
                setCardBackgroundColor(Color.WHITE)
                radius = 25f
                elevation = 12f
                setPadding(30, 30, 30, 30)

                val innerLayout = LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER

                    // Inflate the header layout
                    val headerLayout = LayoutInflater.from(context).inflate(R.layout.dialog_header, this, false)
                    val logoImageView = headerLayout.findViewById<ImageView>(R.id.logoImage)
                    logoImageView.setImageResource(R.drawable.evzone)

                    addView(headerLayout)

                    val logo = ImageView(context).apply {
                        setImageResource(R.drawable.anb) // Replace with actual logo if needed
                        layoutParams = LinearLayout.LayoutParams(120, 120)
                    }

                    val title = TextView(context).apply {
                        text = businessName
                        textSize = 22f
                        setTextColor(Color.BLACK)
                        typeface = Typeface.DEFAULT_BOLD
                        gravity = Gravity.CENTER
                    }

                    val totalBillingText = TextView(context).apply {
                        text = "Total Billing"
                        textSize = 18f
                        setTextColor(Color.DKGRAY)
                        gravity = Gravity.CENTER
                        setPadding(0, 5, 0, 5)
                    }

                    val totalBillingAmount = TextView(context).apply {
                        val formattedAmount = DecimalFormat("#,###").format(totalAmount)
                        text = "$currency $formattedAmount"
                        textSize = 24f
                        setTextColor("#009900".toColorInt())
                        typeface = Typeface.DEFAULT_BOLD
                        gravity = Gravity.CENTER
                    }

                    val detailsContainer = LinearLayout(context).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(20, 20, 20, 20)

                        val title = TextView(context).apply {
                            text = "Transaction's Details"
                            textSize = 18f
                            setTextColor(Color.BLACK)
                            setTypeface(null, Typeface.BOLD)
                            setPadding(0, 0, 0, 20)
                        }
                        addView(title)

                        val formattedAmount = DecimalFormat("#,###").format(totalAmount)
                        val details = listOf(
                            "Type" to "Booking",
                            "To" to userName,
                            "Particulars" to itemsPurchased,
                            "Billed Currency" to currency,
                            "Total Billing" to "$currency $formattedAmount"
                        )

                        details.forEach { (label, value) ->
                            addView(createLabelValuePair(context, label, value, label == "Total Billing"))
                        }
                    }

                    val confirmButton = Button(context).apply {
                        text = "Continue"
                        setTextColor(Color.WHITE)
                        textSize = 18f
                        setPadding(20, 20, 20, 20)
                        background = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 20f
                            setColor("#007BFF".toColorInt())
                        }
                        isAllCaps = false
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            bottomMargin = 30
                        }
                        setOnClickListener {
                            onNext(totalAmount)
                            dialog.dismiss()
                        }
                    }

                    addView(logo)
                    addView(title)
                    addView(totalBillingText)
                    addView(totalBillingAmount)
                    addView(detailsContainer)
                    addView(confirmButton)
                }
                addView(innerLayout)
            }
            addView(cardContainer)
        }

        // Apply the custom theme with rounded corners and animations
        dialog = AlertDialog.Builder(context, R.style.CustomDialogTheme)
            .setView(dialogLayout)
            .setCancelable(false)
            .create()

        dialog.show()
    }
}