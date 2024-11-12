package com.eazzyconnect.wallet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eazzyconnect.Config;
import com.eazzyconnect.EazzyApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EazzyWallet {
    private EazzyWallet() {}

    private static final String BASE_API_URL = "https://eazzyconnect.com/api/v1/wallet";
    private static final String API_KEY = Config.getEnv("API_KEY");

    public static EazzyApiResponse getBalance() {
        String url = BASE_API_URL + "/account-balance";

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            HttpRequest sendSmsRequest = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/vnd.eazzyconnect.v1")
                    .header("apiKey", API_KEY).GET().build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> sendSmsResponse = client.send(sendSmsRequest, HttpResponse.BodyHandlers.ofString());

            JsonNode responseJson = objectMapper.readTree(sendSmsResponse.body());
            String statusCode = responseJson.get("statusCode").asText();
            String message = responseJson.get("message").asText();
            String accountBalance = responseJson.has("data") && !responseJson.get("data").isNull()
                    ? responseJson.get("data").get("balance").asText()
                    : null;
            return new EazzyApiResponse(statusCode.equals("200"), statusCode, message, accountBalance);
        } catch (Exception e) {
            return new EazzyApiResponse(false, "400", e.getMessage());
        }
    }

}
