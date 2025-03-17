Sure! Here's the Markdown format you requested, with a Table of Contents and corresponding sections. I'll keep it in a bullet-point structure for the Table of Contents and ensure the headers are marked correctly for anchor links:


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
- Secure payment processing using the Evzone Africa digital wallet.
- Customizable payment UI components.
- Comprehensive error handling and validation.
- Lightweight and optimized for performance on Android devices.

## Prerequisites

Before integrating the Evzone Pay SDK, ensure you have the following:

- Android Studio (latest stable version recommended).
- Minimum SDK version: API 21 (Android 5.0 Lollipop).
- A registered Evzone Africa merchant account to obtain necessary credentials (e.g., API keys).
- Kotlin 1.5+ configured in your project.
- Access to the private GitHub repository hosting the SDK (requires a JitPack auth token).

## Installation

### Step 1: Add the Dependency

In your app-level `build.gradle`, add the Evzone Pay SDK dependency. Replace `TAG` with the specific release tag (e.g., `1.0.0`) or commit hash from the repository:

```kotlin
dependencies {
    implementation 'com.github.Bravothe:payment-library:TAG'
}

### Step 2: Configure JitPack Authentication

Since the repository is private, you need to configure authentication using a JitPack auth token.

#### Add the Token to `gradle.properties`

Add your JitPack auth token to `gradle.properties` (create the file if it doesn’t exist):

```properties
authToken=jp_jm7m9rr1gk98ej0s9fnn9nftd0
```

#### Update `settings.gradle.kts`

In your project-level `settings.gradle.kts`, configure the JitPack repository to use the `authToken` as the username:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            credentials {
                username = providers.gradleProperty("authToken").get()
            }
        }
    }
}
```

### Step 3: Sync Project

Sync your project with Gradle files to download the library. If you encounter authentication issues, verify your `authToken` and repository access.

## Usage

### Basic Integration

To integrate Evzone Pay into your application, follow these steps:

1. Initialize the SDK with your merchant credentials.
2. Launch the payment process, passing the required parameters: `businessName`, `username`, `itemsPurchased`, and `totalAmount`.
3. Handle the payment result using the provided callback.

Here is an example of how to initiate the payment process:

```kotlin
package com.example.payment

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.evzoneafrica.payment.EvzonePay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPay: Button = findViewById(R.id.btnPay)

        btnPay.setOnClickListener {
            val businessName = "Awesome Store"
            val username = "John Doe"
            val itemsPurchased = "1x Laptop, 2x Phone Chargers"
            val totalAmount = 5000.0

            // Start the payment process
            EvzonePay.startPayment(this, businessName, username, itemsPurchased, totalAmount)
        }
    }
}
```

## API Reference

### `EvzonePay` Class

- **Constructor**:
  - `merchantId` (String, required): Your Evzone Africa merchant ID.
  - `apiKey` (String, required): Your Evzone Africa API key.
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
- **Invalid Merchant ID**: Double-check your merchant credentials with Evzone Africa.
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
- Contact support at support@evzoneafrica.com.
```

### Key Changes:
1. The **Table of Contents** has been structured as a clickable list.
2. The section headers (`## Features`, `## Prerequisites`, etc.) match the anchor links in the Table of Contents.
3. The formatting follows a consistent structure with headings and code examples to ensure clarity.

When rendered (on GitHub, for example), the links in the Table of Contents will navigate to the corresponding sections in the document.

Let me know if this works!
