package com.eazzyconnect;

import com.eazzyconnect.wallet.EazzyWallet;

public class Main {
    public static void main(String[] args) {

        // SMS actions
//        String singleContact = "+256781459239";
//        EazzyApiResponse singleSms = EazzySms.sendSms(singleContact, "fix JAVA SDK single sms send");
//        System.out.println(singleSms);

//        String[] bulkContacts = {"+256781459239", "+256787584128"};
//        EazzyApiResponse bulkSms = EazzySms.sendBulkSms(bulkContacts, "fix JAVA SDK bulk sms send");
//        System.out.println(bulkSms);

//         Wallet actions
        EazzyApiResponse balance = EazzyWallet.getBalance("WL-00004");
        System.out.println(balance);
    }
}
