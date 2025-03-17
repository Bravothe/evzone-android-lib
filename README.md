Below is an updated README in Markdown format for your "Evzone Pay" Android SDK, incorporating the specific JitPack integration instructions you provided, including the use of an `authToken` in `gradle.properties` and `settings.gradle.kts`. The README reflects the requirement to pass `businessName`, `username`, `itemsPurchased`, and `totalAmount` as parameters, and it is tailored for your Kotlin-based SDK hosted on JitPack from a private repository.

```markdown
# Evzone Pay

![Evzone Pay Logo](https://github.com/Bravothe/payment-library/blob/main/src/assets/logo.jpg?raw=true)

**Evzone Pay** is an Android SDK designed to simplify the integration of the Evzone Africa digital wallet payment system into e-commerce Android mobile applications. Built with **Kotlin** in **Android Studio**, this lightweight and customizable library enables developers to seamlessly integrate secure payment processing for their customers.

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
The Evzone Pay SDK is hosted on JitPack from a private GitHub repository. Follow these steps to add it to your Android project:

### Step 1: Add the Dependency
In your app-level `build.gradle`, add the Evzone Pay SDK dependency. Replace `TAG` with the specific release tag (e.g., `1.0.0`) or commit hash from the repository:

```gradle
dependencies {
    implementation 'com.github.Bravothe:payment-library:TAG'
}
```

### Step 2: Configure JitPack Authentication
Since the repository is private, you need to configure authentication using a JitPack auth token.

#### Add the Token to `gradle.properties`
Add your JitPack auth token to `gradle.properties` (create the file if it doesn’t exist):

```properties
authToken=jp_jm7m9rr1gk98ej0s9fnn9nftd0
```

Replace `jp_jm7m9rr1gk98ej0s9fnn9nftd0` with the actual token provided by JitPack for your account.

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

### Step 3: (Optional) Approve JitPack Application on GitHub
If this is your first time using JitPack with a private repository, you may need to approve the JitPack application on GitHub:
1. Go to your GitHub account settings.
2. Navigate to "Applications" > "Authorized OAuth Apps".
3. Ensure JitPack is listed and has access to your private repository.

### Step 4: Sync Project
Sync your project with Gradle files to download the library. If you encounter authentication issues, verify your `authToken` and repository access.

## Usage
### Basic Integration
1. Initialize the Evzone Pay SDK with your merchant credentials.
2. Launch the payment process by passing the required parameters: `businessName`, `username`, `itemsPurchased`, and `totalAmount`.
3. Handle the payment result in a callback.

Here’s an example in Kotlin:

```kotlin
package com.example.login
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

### Notes
- Replace `your_merchant_id` and `your_api_key` with credentials provided by Evzone Africa.
- The `businessName`, `username`, `itemsPurchased`, and `totalAmount` parameters are required for each payment request.
- Ensure the `totalAmount` is a valid `Double` value representing the total cost of the purchased items.

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
2. User clicks "Pay Now" to initiate payment.
3. The `startPayment` method is called with `businessName`, `username`, `itemsPurchased`, and `totalAmount`.
4. Upon success, the `onSuccess` callback is triggered with a transaction ID.

For more examples, refer to the [sample app](https://github.com/Bravothe/payment-library/tree/main/sample) in the repository.

## Configuration
The SDK supports basic customization:
- **UI Customization**: Modify the payment UI by overriding layout resources (details in the sample app).
- **Currency**: Currently supports the default currency tied to your merchant account. Contact support for multi-currency support.

## Troubleshooting
- **"Authentication Failed"**: Verify your `authToken` in `gradle.properties` and ensure it has access to the private repository on JitPack.
- **"Invalid Merchant ID or API Key"**: Verify your credentials with Evzone Africa support.
- **Payment UI Not Displaying**: Ensure the `context` passed to `EvzonePay` is valid and that permissions (e.g., Internet) are granted.
- **"Missing Required Parameters"**: Ensure `businessName`, `username`, `itemsPurchased`, and `totalAmount` are provided and not null/empty.
- **Payment Not Processing**: Check your internet connection and ensure Evzone Africa servers are operational.

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a branch (`git checkout -b feature/your-feature`).
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

### Key Updates:
1. **JitPack Integration**: The installation section now reflects the specific steps you provided:
   - Adding the `authToken` to `$HOME/.gradle/gradle.properties`.
   - Configuring `settings.gradle.kts` to use the `authToken` as the username.
   - Including an optional step to approve the JitPack application on GitHub.
2. **Simplified Authentication**: The README uses the `authToken` approach you specified, omitting the need for a GitHub username and personal access token pair, as your method relies solely on the JitPack-provided token.
3. **Troubleshooting**: Added a note about authentication failures related to the `authToken` to help developers debug JitPack-specific issues.

### Notes:
- **Auth Token**: The example `authToken` (`jp_jm7m9rr1gk98ej0s9fnn9nftd0`) is included as a placeholder. Replace it with the actual token provided by JitPack for your repository. In practice, developers should keep this token secure and not expose it in public documentation.
- **Kotlin DSL**: The `settings.gradle.kts` example uses Kotlin DSL syntax as per your provided snippet. If some developers use Groovy (`settings.gradle`), they may need to adapt the configuration accordingly:
  ```gradle
  dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
      repositories {
          google()
          mavenCentral()
          maven {
              url 'https://jitpack.io'
              credentials {
                  username = gradleProperty('authToken')
              }
          }
      }
  }
  ```
- **Assumptions**: The README assumes your SDK package is `com.evzoneafrica.payment` and includes a `PaymentResultCallback` interface. Adjust these details to match your actual implementation.

This README provides a clear and detailed guide for integrating your SDK, including the specific JitPack authentication steps you provided. Let me know if you need further adjustments!
