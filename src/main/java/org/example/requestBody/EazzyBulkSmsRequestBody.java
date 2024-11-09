package org.example.requestBody;

public class EazzyBulkSmsRequestBody {
    private String phone_numbers;
    private String message;

    private EazzyBulkSmsRequestBody() {}

    public EazzyBulkSmsRequestBody(String phone_number, String message) {
        this.phone_numbers = phone_number;
        this.message = message;
    }

    public String getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(String phone_number) {
        this.phone_numbers = phone_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
