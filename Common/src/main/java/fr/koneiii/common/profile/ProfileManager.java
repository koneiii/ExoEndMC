package fr.koneiii.common.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.koneiii.common.models.ProfilePostModel;
import fr.koneiii.common.models.TempProfileModel;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ProfileManager {

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static String BASE_URL = "/api/profile";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String token;

    public ProfileManager(String token, String baseUrl) {
        this.token = token;
        BASE_URL = baseUrl + BASE_URL;
    }

    public TempProfileModel createProfile(UUID uniqueId, String name) throws Exception {
        ProfilePostModel request = new ProfilePostModel(uniqueId, name);
        String jsonRequest = objectMapper.writeValueAsString(request);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/create"))
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return objectMapper.readValue(response.body(), TempProfileModel.class);
        } else {
            throw new IOException("Failed request: " + response.statusCode());
        }


    }

    public TempProfileModel getProfile(UUID uniqueId) throws IOException {
        String endpoint = BASE_URL + "/get?uniqueId=" + uniqueId;
        return sendGetRequest(endpoint);
    }

    public TempProfileModel updateCoins(UUID uniqueId, int coins) throws IOException {
        String endpoint = BASE_URL + "/setcoins?uniqueId=" + uniqueId + "&coins=" + coins;
        return sendPostRequest(endpoint);
    }

    public TempProfileModel updateOnline(UUID uniqueId, boolean isOnline) throws IOException {
        String endpoint = BASE_URL + "/setonline?uniqueId=" + uniqueId + "&isOnline=" + isOnline;
        return sendPostRequest(endpoint);
    }

    public TempProfileModel disconnect(UUID uniqueId) throws IOException {
        String endpoint = BASE_URL + "/disconnect?uniqueId=" + uniqueId;
        return sendPostRequest(endpoint);
    }

    public TempProfileModel connect(UUID uniqueId) throws IOException {
        String endpoint = BASE_URL + "/connect?uniqueId=" + uniqueId;
        return sendPostRequest(endpoint);
    }

    public TempProfileModel saveProfile(UUID uniqueId) throws IOException {
        String endpoint = BASE_URL + "/save?uniqueId=" + uniqueId;
        return sendPostRequest(endpoint);
    }

    private TempProfileModel sendGetRequest(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", token);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            return objectMapper.readValue(connection.getInputStream(), TempProfileModel.class);
        } else {
            System.err.println("Authorization failed or invalid token. Response code: " + responseCode);
            throw new IOException("Failed GET request: " + responseCode);
        }
    }

    private TempProfileModel sendPostRequest(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", token);
        connection.setDoOutput(true);

        byte[] input = "".getBytes(StandardCharsets.UTF_8);
        connection.getOutputStream().write(input);

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            return objectMapper.readValue(connection.getInputStream(), TempProfileModel.class);
        } else {
            System.err.println("Authorization failed or invalid token. Response code: " + responseCode);
            throw new IOException("Failed POST request: " + responseCode);
        }
    }
}
