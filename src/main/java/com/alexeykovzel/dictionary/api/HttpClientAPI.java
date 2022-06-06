package com.alexeykovzel.dictionary.api;

public class HttpClientAPI {
    private final String name;
    protected final String host;
    protected final String key;

    public String getName() {
        return name;
    }

    public HttpClientAPI(String name, String host, String key) {
        this.name = name;
        this.host = host;
        this.key = key;
    }
}
