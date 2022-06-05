package services.api;

import db.entity.Definition;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WordsAPI extends HttpClientAPI {
    public WordsAPI() {
//        super("WordsAPI", BotConfig.WORDS_API_HOST, BotConfig.WORDS_API_KEY);
        super("123", "123", "123");
    }

    public Set<Definition> getDefinitions(String word) {
        JSONArray results = getResults(word);
        if (results == null) return null;
        Set<Definition> definitions = new HashSet<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            String definition = result.getString("definition");
            String partOfSpeech = result.getString("partOfSpeech");
            definitions.add(new Definition(definition, partOfSpeech));
        }
        return definitions;
    }

    public Set<String> getExamples(String word) {
        JSONArray results = getResults(word);
        if (results == null) return null;
        Set<String> examples = new HashSet<>();
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
        String url = String.format("https://wordsapiv1.p.rapidapi.com/words/%s", word);
        try (Response response = RapidAPIClient.getInstance().sendRequest(url, host, key)) {
            ResponseBody body = response.body();
            if (body != null) {
                JSONObject data = new JSONObject(body.string());
                if (data.has("results")) {
                    return data.getJSONArray("results");
                }
            }
        } catch (IOException e) {
            System.out.println(getName() + " is not responding...");
        }
        return null;
    }
}