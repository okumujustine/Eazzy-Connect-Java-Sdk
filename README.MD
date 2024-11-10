# EazzyConnect SDK

EazzyConnect SDK provides access to the EazzyConnect API. 
Currently, it supports SMS messaging to Ugandan contacts only specifically to MTN and Airtel.

## Important to Note

- Currently supports Ugandan phone numbers only (MTN and Airtel)
- API key must be added in the `.env` file
- All phone numbers must include the country code (+256)

## Features

- Send SMS to single contact
- Send bulk SMS to multiple contacts
- Check wallet balance

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.eazzyconnect</groupId>
    <artifactId>eazzyconnectsdk</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Configuration

1. Create a `.env` file in your project's root directory
2. Add your API key generated from the [EazzyConnect](https://eazzyconnect.com/) website:
   ```
   API_KEY=your_api_key_here
   ```
3. A sample configuration file is provided in `.env.example`. You can copy it to your `.env` and edit with your personal detail.

## Usage

### Initialize Imports

```java
import com.eazzyconnect.sms.EazzySms;
import com.eazzyconnect.wallet.EazzyWallet;
```

### SMS Operations
EazzyConnect uses a wallet-based system for SMS services. Before sending SMS messages, you need to top up your wallet using either MTN Mobile Money or Airtel Money.
### Supported Payment Providers
- MTN Mobile Money (Uganda)
- Airtel Money (Uganda)


#### Send SMS to Single Contact

```java
String phoneNumber = "+256781459239";  // Ugandan phone number only
String message = "Hello from EazzyConnect!";

EazzyApiResponse response = EazzySms.sendSms(phoneNumber, message);
System.out.println(response);
```

#### Send Bulk SMS

```java
String[] phoneNumbers = {"+256781459239", "+256787584128"};  // Array of Ugandan phone numbers only
String message = "Bulk message from EazzyConnect!";

EazzyApiResponse response = EazzySms.sendBulkSms(phoneNumbers, message);
System.out.println(response);
```

### Wallet Operations
SMS sending is facilitated 
#### Check Wallet Balance

```java
String walletNumber = "WL-0000x";

EazzyApiResponse response = EazzyWallet.getBalance(walletNumber);
System.out.println(response);
```

## API Reference

### EazzySms Class

#### `sendSms(String phoneNumber, String message)`

Sends an SMS to a single contact.

**Parameters:**
- `phone_number` (String): Recipient's phone number (must be a valid Ugandan number)
- `message` (String): The message content to be sent

**Returns:**
- `EazzyApiResponse`: Response object containing the API response

#### `sendBulkSms(String[] phoneNumbers, String message)`

Sends the same SMS message to multiple contacts.

**Parameters:**
- `phone_numbers` (String[]): Array of recipient phone numbers (must be valid Ugandan numbers)
- `message` (String): The message content to be sent to all recipients

**Returns:**
- `EazzyApiResponse`: Response object containing the API response

### EazzyWallet Class

#### `getBalance(String walletNumber)`

Retrieves the balance for a specified wallet.

**Parameters:**
- `walletNumber` (String): The wallet number to check

**Returns:**
- `EazzyApiResponse`: Response object containing the wallet balance information

## Complete Example

```java
package com.eazzyconnect;

import com.eazzyconnect.sms.EazzySms;
import com.eazzyconnect.wallet.EazzyWallet;

public class Main {
    public static void main(String[] args) {
        // SMS actions
        // Single SMS
        String singleContact = "+256781459239";
        EazzyApiResponse singleSms = EazzySms.sendSms(singleContact, "Test single SMS");
        System.out.println(singleSms);

        // Bulk SMS
        String[] bulkContacts = {"+256781459239", "+256787584128"};
        EazzyApiResponse bulkSms = EazzySms.sendBulkSms(bulkContacts, "Test bulk SMS");
        System.out.println(bulkSms);

        // Wallet actions
        EazzyApiResponse balance = EazzyWallet.getBalance("WL-00004");
        System.out.println(balance);
    }
}
```

## Support

For additional information and support, please visit [EazzyConnect website](https://eazzyconnect.com/).

## License

[Include your license information here]