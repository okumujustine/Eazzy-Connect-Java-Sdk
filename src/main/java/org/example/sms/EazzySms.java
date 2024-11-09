package org.example.sms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Config;
import org.example.EazzyApiResponse;
import org.example.requestBody.EazzyBulkSmsRequestBody;
import org.example.requestBody.EazzySmsRequestBody;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class EazzySms {
    private static final String API_URL = "https://eazzyconnect.com/api/v1/sms";
    private static final String API_KEY = Config.getEnv("API_KEY");

    private EazzySms() {}

    public static EazzyApiResponse sendSms(String contact, String message) {
        return senSmsUtil(new EazzySmsRequestBody(contact, message), "/send");
    }

    public static EazzyApiResponse sendBulkSms(String[] bulkContacts, String message) {
        return senSmsUtil(new EazzyBulkSmsRequestBody(String.join(",", bulkContacts), message), "/bulk-send");
    }

    private static EazzyApiResponse senSmsUtil(Object sendSmsRequestBody, String endpoint) {

        if (!(sendSmsRequestBody instanceof EazzyBulkSmsRequestBody || sendSmsRequestBody instanceof EazzySmsRequestBody)) {
            return new EazzyApiResponse(false, "400", "Invalid request body type");
        }

        String url = API_URL + endpoint;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(sendSmsRequestBody);
            System.out.println(json);

            HttpRequest sendSmsRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/vnd.eazzyconnect.v1")
                    .header("apiKey", API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> sendSmsResponse = client.send(sendSmsRequest, BodyHandlers.ofString());

            JsonNode responseJson = objectMapper.readTree(sendSmsResponse.body());
            String statusCode = responseJson.get("statusCode").asText();
            String message = responseJson.get("message").asText();

            return new EazzyApiResponse(statusCode.equals("200"), statusCode, message);
        } catch (Exception e) {
            return new EazzyApiResponse(false, "400", e.getMessage());
        }
    }

}
