package com.alexeykovzel;

import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.*;

@Getter
@Setter
public class BotCredentials {
    private static BotCredentials instance;

    private String botUsername;
    private String botToken;
    private String rapidAPIKey;

    public static BotCredentials getInstance() {
        if (instance == null) {
            try {
                Yaml yaml = new Yaml(new Constructor(BotCredentials.class));
                FileInputStream in = new FileInputStream("src/main/resources/credentials.yaml");
                instance = yaml.load(in);
            } catch (FileNotFoundException e) {
                System.out.println("[ERROR] Bot config could not be initialized");
            }
        }
        return instance;
    }
}