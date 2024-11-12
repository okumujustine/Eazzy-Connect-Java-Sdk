package com.eazzyconnect.sms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.eazzyconnect.Config;
import com.eazzyconnect.EazzyApiResponse;
import com.eazzyconnect.requestBody.EazzyBulkSmsRequestBody;
import com.eazzyconnect.requestBody.EazzySmsRequestBody;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

/**
 * A utility class for sending SMS messages through the EazzyConnect API.
 * This class provides methods for sending both single and bulk SMS messages.
 *
 * <p>The class uses the EazzyConnect API v1 and requires an API key to be set
 * in the environment variables.</p>
 *
 * @since 0.1.0
 */
public class EazzySms {
    /** The base URL for the EazzyConnect SMS API */
    private static final String API_URL = "https://eazzyconnect.com/api/v1/sms";

    /** The API key retrieved from environment variables */
    private static final String API_KEY = Config.getEnv("API_KEY");

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private EazzySms() {}

    /**
     * Sends a single SMS message to a specified contact.
     *
     * @param contact must be a Uganda phone number in the format (e.g., +2547XXXXXXXX)
     * @param message The text message to be sent
     * @return An {@link EazzyApiResponse} object containing the status and response from the API
     * @throws IllegalArgumentException if contact or message is null or empty
     */
    public static EazzyApiResponse sendSms(String contact, String message) {
        return senSmsUtil(new EazzySmsRequestBody(contact, message), "/send");
    }

    /**
     * Sends the same SMS message to multiple contacts simultaneously.
     *
     * @param bulkContacts An array of phone numbers
     * @param message The text message to be sent to all contacts
     * @return An {@link EazzyApiResponse} object containing the status and response from the API
     * @throws IllegalArgumentException if bulkContacts is null or empty, or message is null or empty
     */
    public static EazzyApiResponse sendBulkSms(String[] bulkContacts, String message) {
        return senSmsUtil(new EazzyBulkSmsRequestBody(String.join(",", bulkContacts), message), "/bulk-send");
    }

    /**
     * Internal utility method to handle both single and bulk SMS sending operations.
     *
     * <p>This method handles the HTTP request to the EazzyConnect API and processes
     * the response. It includes error handling for network and API-related issues.</p>
     *
     * @param sendSmsRequestBody The request body object (either {@link EazzySmsRequestBody} or {@link EazzyBulkSmsRequestBody})
     * @param endpoint The API endpoint to be used ("/send" or "/bulk-send")
     * @return An {@link EazzyApiResponse} object containing the status and response from the API
     * @throws IllegalArgumentException if the request body type is invalid
     */
    private static EazzyApiResponse senSmsUtil(Object sendSmsRequestBody, String endpoint) {

        if (!(sendSmsRequestBody instanceof EazzyBulkSmsRequestBody || sendSmsRequestBody instanceof EazzySmsRequestBody)) {
            return new EazzyApiResponse(false, "400", "Invalid request body type");
        }

        String url = API_URL + endpoint;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(sendSmsRequestBody);

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
