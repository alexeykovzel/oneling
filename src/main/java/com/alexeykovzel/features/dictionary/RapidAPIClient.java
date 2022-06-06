package com.alexeykovzel.features.dictionary;

import com.alexeykovzel.BotCredentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class RapidAPIClient {
    private static final String API_HOST_HEADER = "x-rapidapi-host";
    private static final String API_KEY_HEADER = "x-rapidapi-key";
    private static final String API_KEY = BotCredentials.getInstance().getRapidAPIKey();

    public Response sendRequest(String url, String host) throws IOException {
        return new OkHttpClient().newCall(new Request.Builder()
                .url(url).get()
                .addHeader(API_HOST_HEADER, host)
                .addHeader(API_KEY_HEADER, API_KEY)
                .build()).execute();
    }

    public Response sendRequest(String url, String host, RequestBody body) throws IOException {
        return new OkHttpClient().newCall(new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("Accept-Encoding", "application/gzip")
                .addHeader(API_HOST_HEADER, host)
                .addHeader(API_KEY_HEADER, API_KEY)
                .build()).execute();
    }

    public static RapidAPIClient getInstance() {
        return RapidAPIClientHolder.instance;
    }

    private static final class RapidAPIClientHolder {
        public static final RapidAPIClient instance = new RapidAPIClient();
    }
}