package services.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RapidAPIClient {
    private static final String API_HOST_HEADER = "x-rapidapi-host";
    private static final String API_KEY_HEADER = "x-rapidapi-key";

    private RapidAPIClient() {
    }

    public Response sendRequest(String url, String host, String key) throws IOException {
        return new OkHttpClient().newCall(new Request.Builder()
                .url(url).get()
                .addHeader(API_HOST_HEADER, host)
                .addHeader(API_KEY_HEADER, key)
                .build()).execute();
    }

    public static RapidAPIClient getInstance() {
        return RapidAPIClientHolder.instance;
    }

    private static final class RapidAPIClientHolder {
        public static final RapidAPIClient instance = new RapidAPIClient();
    }
}