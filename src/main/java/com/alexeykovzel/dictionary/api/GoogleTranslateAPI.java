package com.alexeykovzel.dictionary.api;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleTranslateAPI {
    public static final String API_URL = "https://google-translate1.p.rapidapi.com/language/translate/v2";
    public static final String API_HOST = "google-translate1.p.rapidapi.com";

    /**
     * Sends a POST request to the GoogleTranslateAPI for the translation of the phrase. The source language
     * is detected automatically by the API.
     *
     * @param phrase   phrase that should be translated
     * @param language target language
     * @return translation of the phrase
     */
    public List<String> translate(String phrase, String language) {
        RequestBody requestBody = new FormBody.Builder()
                .add("q", phrase)
                .add("target", language)
                .add("format", "text").build();

        try (Response response = RapidAPIClient.getInstance().sendRequest(API_URL, API_HOST, requestBody)) {
            ResponseBody body = response.body();
            if (body != null) {
                JSONObject data = new JSONObject(body.string());

                // Check that a body contains "data" field
                if (!data.has("data")) return null;
                JSONObject jsonData = data.getJSONObject("data");

                // Check that the retrieved data contains "translations" field
                if (!jsonData.has("translations")) return null;
                JSONArray jsonTranslations = jsonData.getJSONArray("translations");

                List<String> translations = new ArrayList<>();
                for (int i = 0; i < jsonTranslations.length(); i++) {
                    JSONObject jsonTranslation = jsonTranslations.getJSONObject(i);
                    translations.add(jsonTranslation.getString("translatedText"));
                }
                return translations;
            }
        } catch (IOException e) {
            // Print error message if there was no response from the API
            System.out.println("GoogleTranslateAPI is not responding...");
        }
        return null;
    }
}