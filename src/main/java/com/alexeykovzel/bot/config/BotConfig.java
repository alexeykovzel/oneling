package com.alexeykovzel.bot.config;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

@Getter
@Setter
public class BotConfig {
    private static BotConfig instance;

    private String botUsername;
    private String botToken;
    private String rapidAPIKey;

    public static BotConfig getInstance() {
        if (instance == null) {
            try {
                Yaml yaml = new Yaml(new Constructor(BotConfig.class));
                FileInputStream in = new FileInputStream("src/main/resources/config.yaml");
                instance = yaml.load(in);
            } catch (FileNotFoundException e) {
                System.out.println("[ERROR] Bot config could not be initialized");
            }
        }
        return instance;
    }
}