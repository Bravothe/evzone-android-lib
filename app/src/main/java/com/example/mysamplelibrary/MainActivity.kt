package com.example.mysamplelibrary

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mathoperation.MathOperation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPay: Button = findViewById(R.id.btnShowMessage)

        btnPay.setOnClickListener {
            val businessName = "Xtrymz Technologies"
            val userName = "rrr"
            val itemsPurchased = "1x Laptop, 2x Phone Chargers"
            val totalAmount = 500.0
            val currency = "UGX"
            val walletid = "W-765235532"
            val businessLogoUrl = "https://res.cloudinary.com/dlfa42ans/image/upload/v1743854843/logo5_ljusns.png" // Replace with actual logo URL

            // Start the payment process with businessLogoUrl included
            MathOperation.startPayment(
                this,
                businessName,
                userName,
                itemsPurchased,
                currency,
                totalAmount,
                walletid,
                businessLogoUrl
            )
        }
    }
}