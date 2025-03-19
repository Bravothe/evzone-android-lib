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
            val userName = "John Doe"
            val itemsPurchased = "1x Laptop, 2x Phone Chargers"
            val totalAmount = 5000.0
            val currency = "UGX"
            val walletid = "W-256-765235532"  // Pass walletid here

            // Start the payment process with currency and walletid included
            MathOperation.startPayment(this, businessName, userName, itemsPurchased, currency, totalAmount, walletid)
        }
    }
}
