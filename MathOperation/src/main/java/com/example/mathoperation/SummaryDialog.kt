package com.example.mathoperation.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.mathoperation.R

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
                layoutParams =
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val valueTextView = TextView(context).apply {
                text = value
                textSize = 16f
                setTextColor(if (isTotal) Color.parseColor("#009900") else Color.BLACK)
                typeface = if (isTotal) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                gravity = Gravity.END
                layoutParams =
                    LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            addView(labelTextView)
            addView(valueTextView)
        }
    }

    fun showSummaryDialog(
        context: Context, businessName: String, userName: String, itemsPurchased: String,
        currency: String, totalAmount: Double, onNext: (Double) -> Unit
    ) {
        // Create dialog layout
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
                    val headerLayout =
                        LayoutInflater.from(context).inflate(R.layout.dialog_header, this, false)

                    // You can also change the logo if needed
                    val logoImageView = headerLayout.findViewById<ImageView>(R.id.logoImage)
                    logoImageView.setImageResource(R.drawable.evzone)  // Dynamically set the logo if needed

                    // Add the header layout to the dialog
                    addView(headerLayout)

                    // Existing content
                    val logo = ImageView(context).apply {
                        setImageResource(R.drawable.anb)  // Replace with actual logo
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
                        text = "$currency $totalAmount"
                        textSize = 24f
                        setTextColor(Color.parseColor("#009900"))
                        typeface = Typeface.DEFAULT_BOLD
                        gravity = Gravity.CENTER
                    }

                    val detailsContainer = LinearLayout(context).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(20, 20, 20, 20)

                        val backgroundDrawable = GradientDrawable().apply {
                            shape = GradientDrawable.RECTANGLE
                            cornerRadius = 15f
                            setColor(Color.parseColor("#F5F5F5"))
                        }
                        background = backgroundDrawable

                        val details = listOf(
                            "Type" to "Booking",
                            "To" to userName,
                            "Particulars" to itemsPurchased,
                            "Billed Currency" to currency,
                            "Total Billing" to "$currency$totalAmount"
                        )

                        details.forEach { (label, value) ->
                            addView(
                                createLabelValuePair(
                                    context,
                                    label,
                                    value,
                                    label == "Total Billing"
                                )
                            )
                        }
                    }

                    // Confirm button
                    val confirmButton = Button(context).apply {
                        text = "Confirm"
                        setTextColor(Color.WHITE)
                        textSize = 18f
                        setBackgroundColor(Color.parseColor("#007BFF"))
                        setPadding(20, 20, 20, 20)
                        setOnClickListener {
                            onNext(totalAmount)  // Pass totalAmount to onNext callback
                            dialog.dismiss()      // Dismiss the dialog here
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

        dialog = AlertDialog.Builder(context)
            .setView(dialogLayout)
            .setCancelable(false)  // Ensure dialog is not dismissible outside of confirm action
            .create()

        // Show the dialog
        dialog.show()
    }
}