To make the Table of Contents in your README fully navigable, you can ensure that the links within the Table of Contents match the proper heading IDs. Markdown automatically creates IDs for headings based on the text of the heading, but it does so in a specific way:

- It converts the text of each heading to lowercase.
- It replaces spaces and special characters (except hyphens) with hyphens (`-`).

For example, if you have a heading like:

```markdown
## Installation
```

It will create a link like this: `#installation`.

### Updated Table of Contents with Links
To ensure that the Table of Contents is navigable, here's your updated README with the correct anchor links.

```markdown
# Evzone Pay SDK

![Evzone Pay Logo](https://github.com/Bravothe/payment-library/blob/main/src/assets/logo.jpg?raw=true)

**Evzone Pay** is a Kotlin-based Android SDK designed to simplify the integration of the **Evzone Africa** digital wallet payment system into e-commerce mobile applications. With this SDK, developers can easily implement secure payment processing for their customers, enabling smooth and efficient financial transactions.

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
- **Easy Integration**: Seamless integration with Android e-commerce apps.
- **Secure Payments**: Payment processing using the Evzone Africa digital wallet.
- **Customizable UI**: Tailor the payment UI components to suit your app's design.
- **Comprehensive Error Handling**: Robust error management to ensure smooth user experience.
- **Optimized for Performance**: Lightweight SDK designed for efficient resource usage on Android devices.

## Prerequisites
Before integrating the Evzone Pay SDK, ensure the following requirements are met:
- **Android Studio** (latest stable version recommended).
- Minimum **SDK Version**: API 21 (Android 5.0 Lollipop).
- A registered **Evzone Africa merchant account** to obtain necessary credentials (API keys).
- **Kotlin 1.5+** configured in your project.
- Access to the private GitHub repository hosting the SDK (requires a JitPack auth token).

## Installation

### Step 1: Add the SDK Dependency
In your app-level `build.gradle`, add the Evzone Pay SDK dependency. Replace `TAG` with the desired release tag (e.g., `1.0.0`) or commit hash from the repository:

```gradle
dependencies {
    implementation 'com.github.Bravothe:payment-library:TAG'
}
```

### Step 2: Configure JitPack Authentication
Since the repository is private, you need to authenticate using a JitPack auth token. 

#### Add the Token to `gradle.properties`
Add your JitPack auth token to your `gradle.properties` (create the file if it doesn’t exist):

```properties
authToken=jp_jm7m9rr1gk98ej0s9fnn9nftd0
```

Replace `jp_jm7m9rr1gk98ej0s9fnn9nftd0` with your actual JitPack auth token.

#### Update `settings.gradle.kts`
In your project-level `settings.gradle.kts`, configure the JitPack repository and pass the `authToken` as the username:

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

### Step 3: (Optional) Approve JitPack Application on GitHub
If this is your first time using JitPack with a private repository, ensure that JitPack is authorized to access your repository:
1. Go to **GitHub Settings** > **Applications** > **Authorized OAuth Apps**.
2. Confirm that JitPack has access to your private repositories.

### Step 4: Sync the Project
Sync your project with the Gradle files to download the SDK. If you face authentication issues, verify that the `authToken` is correct and your repository is accessible.

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

            // Initiating the payment process
            EvzonePay.startPayment(this, businessName, username, itemsPurchased, totalAmount)
        }
    }
}
```

### Notes:
- Replace `your_merchant_id` and `your_api_key` with the credentials provided by Evzone Africa.
- The parameters `businessName`, `username`, `itemsPurchased`, and `totalAmount` are required for initiating each payment.
- Ensure `totalAmount` is a valid `Double` representing the total amount of the items purchased.

## API Reference

### `EvzonePay` Class
- **Constructor**:
  - `merchantId` (String, required): Your Evzone Africa merchant ID.
  - `apiKey` (String, required): Your Evzone Africa API key.
  - `context` (Context, required): The Android context (e.g., Activity).

- **Methods**:
  - `startPayment(businessName: String, username: String, itemsPurchased: String, totalAmount: Double, callback: PaymentResultCallback)`: Starts the payment UI and processes the payment with the provided details.

### `PaymentResultCallback` Interface
- `onSuccess(transactionId: String)`: Triggered when the payment is successful, returning a unique transaction ID.
- `onError(errorMessage: String)`: Triggered when an error occurs, providing an error message.
- `onCancel()`: Triggered when the user cancels the payment.

## Examples

### Complete Checkout Flow:
1. User adds items to the cart.
2. User clicks "Pay Now" to initiate the payment.
3. The `startPayment` method is invoked with `businessName`, `username`, `itemsPurchased`, and `totalAmount`.
4. Upon success, the `onSuccess` callback is triggered with a transaction ID.

For more examples, refer to the [sample app](https://github.com/Bravothe/payment-library/tree/main/sample).

## Configuration

### Customization:
The SDK allows for the following customizations:
- **UI Customization**: Modify the payment UI by overriding layout resources (further details can be found in the sample app).
- **Currency**: The SDK uses the default currency linked to your merchant account. For multi-currency support, please contact support.

## Troubleshooting

- **Authentication Failed**: Ensure the `authToken` in `gradle.properties` is correct and that JitPack has access to your private repository.
- **Invalid Merchant ID or API Key**: Verify your credentials with Evzone Africa support.
- **Payment UI Not Displaying**: Ensure the `context` passed to `EvzonePay` is valid and the necessary permissions (e.g., Internet) are granted.
- **Missing Parameters**: Ensure that `businessName`, `username`, `itemsPurchased`, and `totalAmount` are provided and not null.
- **Payment Not Processing**: Check your internet connection and ensure Evzone Africa’s servers are operational.

## Contributing
We welcome contributions to the Evzone Pay SDK. To contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m "Add your feature"`).
4. Push to your branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

See [CONTRIBUTING.md](CONTRIBUTING.md) for more details.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Support
For questions or issues:
- Open an issue on the [GitHub Issues page](https://github.com/Bravothe/payment-library/issues).
- Contact support at support@evzoneafrica.com.
```

### Key Points:
1. **Table of Contents**: I added the appropriate markdown anchors to ensure each section is linked to the corresponding heading. For example, `[Installation](#installation)` will jump to the `## Installation` section.
2. **Anchor Matching**: The Table of Contents now contains links to the correct section headings (e.g., `#features`, `#prerequisites`, etc.). When users click on any link in the Table of Contents, they will be directed to that section of the document.

This structure should now work perfectly for easy navigation through your README when viewed on GitHub or in any Markdown editor that supports Table of Contents links. Let me know if you need further assistance!
