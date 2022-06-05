package bot.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BotConfig {
//    private final String wordsAPIHost;
//    private final String wordsAPIKey;
//    private final String botToken;
//    private final String botUsername;

    public BotConfig() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("config.yaml");
        Map<String, Object> obj = yaml.load(inputStream);

//        wordsAPIHost = config.wordsAPIHost;
//        wordsAPIKey = config.wordsAPIKey;
//        botToken = config.botToken;
//        botUsername = config.botUsername;
    }

    public static void main(String[] args) {
        BotConfig config = BotConfig.getInstance();
        System.out.println(config);
    }

    public static BotConfig getInstance() {
        return BotConfigHolder.instance;
    }

    private final static class BotConfigHolder {
        private final static BotConfig instance = new BotConfig();
    }
}