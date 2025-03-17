# Evzone Pay

<img src="https://github.com/Bravothe/payment-library/blob/main/src/assets/logo.jpg?raw=true" alt="Evzone Pay Logo" width="200" />

**Evzone Africa** is a library designed to simplify the integration of a digital wallet payment system into e-commerce Android Mobile Platforms. Built with **Kotlin** and **XML**, it provides a seamless way for developers to enable their customers to make payments using the Evzone Africa digital wallet. This package is lightweight, customizable, and developer-friendly.

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

- Easy integration with e-commerce Mobile platforms.
- Support for React-base frontends.
- Secure payment processing with Evzone Africa digital wallet.
- Customizable payment form component.
- Comprehensive error handling and validation.
- Lightweight and optimized for performance.

## Prerequisites

Before you begin, ensure you have the following installed:
- [Node.js](https://nodejs.org/) (v16 or higher)
- [npm](https://www.npmjs.com/) (v8 or higher) or [yarn](https://yarnpkg.com/)
- A registered Evzone Africa merchant account (to obtain necessary credentials).

## Installation
To install the `evzone-africa` library, run the following command in your project directory:

```bash

npm install evzone-africa

```

Or, if you're using Yarn:

```bash
yarn add evzone-africa
```

Additionally, import the CSS file for styling the payment form:

```js
import "evzone-africa13/dist/dist/WalletPaymentForm.css";
```

## Usage
### Frontend (React)
1. Import the `WalletPaymentForm` component into your React application.
2. Use it within a conditional render to show the payment form when needed.
3. Pass the required props, such as `customerId`, `amount`, `onClose`, and `onSuccess`.

Here’s an example usage in a shopping cart component:

```js


import React, { useState } from "react";
import WalletPaymentForm from "evzone-africa13/dist/WalletPaymentForm.esm";
import "evzone-africa13/dist/dist/WalletPaymentForm.css";

const Cart = () => {
  const [showPopup, setShowPopup] = useState(false);
  const cartTotalAmount = 99.99; // Example amount (replace with your cart logic)
  const customerId = "customer123"; // Example customer ID (replace with your auth logic)

  const handlePaymentSuccess = () => {
    console.log("Payment successful");
    // Add logic to clear cart or update order status
    setShowPopup(false);
  };

  return (
    <div className="cart-container">
      <h2>Shopping Cart</h2>
      <button onClick={() => setShowPopup(true)}>Make Payments</button>
      {showPopup && (
        <WalletPaymentForm
          customerId={customerId}
          amount={cartTotalAmount}
          onClose={() => setShowPopup(false)}
          onSuccess={handlePaymentSuccess}
        />
      )}
    </div>
  );
};

export default Cart;


```

### Notes

- The `WalletPaymentForm` component is rendered conditionally (e.g., in a popup or modal).
- The `customerId` should be obtained from your authentication system or merchant account.
- The `amount` should reflect the total amount to be paid (e.g., from your cart).

## API Reference

### Frontend (React)

- **`WalletPaymentForm` Component**
    - `customerId` (string, required): The unique identifier for the customer making the payment.
    - `amount` (number, required): The payment amount in the default currency.
    - `onClose` (function, required): Callback triggered when the payment form is closed.
    - `onSuccess` (function, required): Callback triggered on successful payment.

## Examples

### Complete Checkout Flow

1. User adds items to the cart.
2. User clicks "Make Payments" to trigger the payment form.
3. The `WalletPaymentForm` component is displayed.
4. Upon successful payment, the `onSuccess` callback is triggered, and the form is closed via `onClose`.

See the [examples folder](examples/) for more detailed sample code.

## Configuration

The `WalletPaymentForm` component currently supports the props listed in the API Reference. Additional customization (e.g., theming, currency) may be added in future updates. Check the latest documentation or release notes for updates.

## Troubleshooting

- **Payment Form Not Displaying**: Ensure the CSS file is correctly imported (`evzone-africa/dist/dist/WalletPaymentForm.css`) and that the `showPopup` state (or equivalent) is toggled correctly.
- **"Invalid Customer ID" Error**: Verify that the `customerId` is valid and matches your Evzone Africa merchant account records.
- **Payment Not Processing**: Ensure your network connection is stable and that the Evzone Africa servers are reachable.

## Contributing

We welcome contributions to improve `evzone-africa`! To contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit them (`git commit -m "Add your feature"`).
4. Push to your branch (`git push origin feature/your-feature`).
5. Open a Pull Request.

Please read our [Contributing Guidelines](CONTRIBUTING.md) for more details.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Support

For questions, issues, or feature requests, please:
- Open an issue on our [GitHub Issues page](https://github.com/yourusername/evzone-africa/issues).
- Contact our support team at support@evzoneafrica.com.




