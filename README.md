
## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [API Reference](#api-reference)
- [Examples](#examples)
- [Configuration](#configuration)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)
- [Support](#support)

## Features

- Easy integration with Android e-commerce apps.
- Secure payment processing using the EVzone digital wallet.
- Customizable payment UI components.
- Comprehensive error handling and validation.
- Lightweight and optimized for performance on Android devices.

## Prerequisites

Before integrating the EVzone Pay SDK, ensure you have the following:

- Android Studio (latest stable version recommended).
- Minimum SDK version: API 24 (Android 5.0 Lollipop).
- A registered EVzone merchant account to obtain necessary credentials (e.g., API keys).
- Kotlin 1.5+ configured in your project.

## Installation

### Step 1: Add the Dependency

In your app-level `build.gradle`, add the EVzone Pay SDK dependency. Replace `TAG` with the specific release tag (e.g., `1.0.0`) or commit hash from the repository:

```kotlin

dependencies {
    implementation 'com.github.Bravothe:payment-library:TAG'
}
```

## Add the JitPack repository to your build file 

In your project-level `settings.gradle.kts`, configure the JitPack repository

```kotlin
dependencies {
	        implementation 'com.github.Bravothe:mobile_lib:Tag'
	}
```

### Step 3: Sync Project

Sync your project with Gradle files to download the library.

## Usage

### Basic Integration

To integrate EVzone Pay into your application, follow these steps:

1. Initialize the SDK with your merchant credentials.
2. Launch the payment process, passing the required parameters: `businessName`, `username`, `itemsPurchased`, and `totalAmount`.
3. Handle the payment result using the provided callback.

Here is an example of how to initiate the payment process:

```kotlin
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
            val businessName = "Awesome Store"
            val userName = "John Doe"
            val itemsPurchased = "1x Laptop, 2x Phone Chargers"
            val totalAmount = 5000.0

            // Start the payment process using
            MathOperation.startPayment(this, businessName, userName, itemsPurchased, totalAmount)
        }
    }
}

```

## API Reference

### `EVzonePay` Class

- **Constructor**:
  - `merchantId` (String, required): Your EVzone merchant ID.
  - `apiKey` (String, required): Your EVzone API key.
  - `context` (Context, required): The Android context (e.g., Activity).

- **Methods**:
  - `startPayment(businessName: String, username: String, itemsPurchased: String, totalAmount: Double, callback: PaymentResultCallback)`: Launches the payment UI and processes the payment with the provided details.

### `PaymentResultCallback` Interface

- `onSuccess(transactionId: String)`: Called when the payment is successful, returning a unique transaction ID.
- `onError(errorMessage: String)`: Called when an error occurs, providing an error message.
- `onCancel()`: Called when the user cancels the payment.

## Examples

### Complete Checkout Flow

1. User adds items to the cart.
2. User clicks "Pay Now" to initiate the payment.
3. The `startPayment` method is invoked with `businessName`, `username`, `itemsPurchased`, and `totalAmount`.
4. Upon success, the `onSuccess` callback is triggered with a transaction ID.

For more examples, refer to the [sample app](https://github.com/Bravothe/payment-library/tree/main/sample).

## Configuration

### UI Customization

You can modify the UI components of the SDK by overriding the default layout resources. For further details, check out the [sample app](https://github.com/Bravothe/payment-library/tree/main/sample).

## Troubleshooting

- **Authentication Failed**: Ensure your `authToken` is correct in `gradle.properties`.
- **Invalid Merchant ID**: Double-check your merchant credentials with EVzone.
- **Payment UI Not Displaying**: Ensure the `context` is valid, and proper permissions are granted.

## Contributing

To contribute to this project:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to your branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Support

If you have questions or issues:

- Open an issue on the [GitHub Issues page](https://github.com/Bravothe/payment-library/issues).
- Contact support at support@evzone.com
