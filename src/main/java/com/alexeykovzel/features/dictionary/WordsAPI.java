package com.alexeykovzel.features.dictionary;

import com.alexeykovzel.db.models.Definition;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordsAPI {
    public static final String API_URL = "https://wordsapiv1.p.rapidapi.com/words/";
    public static final String API_HOST = "wordsapiv1.p.rapidapi.com";

    public List<Definition> getDefinitions(String word) {
        JSONArray results = getResults(word);
        if (results == null) return null;
        List<Definition> definitions = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String definition = result.getString("definition");
            String partOfSpeech = result.getString("partOfSpeech");
            definitions.add(new Definition(definition, partOfSpeech));
        }
        return definitions;
    }

    public List<String> getExamples(String word) {
        JSONArray results = getResults(word);
        if (results == null) return null;
        List<String> examples = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            if (result.has("examples")) {
                for (Object example : result.getJSONArray("examples")) {
                    examples.add(example.toString());
                }
            }
        }
        return examples;
    }

    private JSONArray getResults(String word) {
        try (Response response = RapidAPIClient.getInstance().sendRequest(API_URL + word, API_HOST)) {
            ResponseBody body = response.body();
            if (body != null) {
                JSONObject data = new JSONObject(body.string());
                if (data.has("results")) {
                    return data.getJSONArray("results");
                }
            }
        } catch (IOException e) {
            System.out.println("WordsAPI is not responding...");
        }
        return null;
    }
}