package org.example;

public class EazzyApiResponse {
    private boolean success;
    private String statusCode;
    private String message;
    private String data;

    public EazzyApiResponse(boolean success, String statusCode, String message) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
    }

    public EazzyApiResponse(boolean success, String statusCode, String message, String data) {
        this.success = success;
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "EazzyApiResponse{" +
                "success=" + success +
                ", statusCode='" + statusCode + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
