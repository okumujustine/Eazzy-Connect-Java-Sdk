package org.example.requestBody;

public class EazzySmsRequestBody {
    private String phone_number;
    private String message;

    private EazzySmsRequestBody() {}

    public EazzySmsRequestBody(String phone_number, String message) {
        this.phone_number = phone_number;
        this.message = message;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
