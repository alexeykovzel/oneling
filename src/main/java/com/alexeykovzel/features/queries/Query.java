package com.alexeykovzel.features.queries;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class Query {
    private static final String DELIMITER = "&";

    private final QueryType type;
    private final String[] args;

    public Query(QueryType type, String... args) {
        this.type = type;
        this.args = args;
    }

    public static String encode(QueryType type, String... args) {
        String[] items = new String[args.length + 1];
        items[0] = type.getId();
        System.arraycopy(args, 0, items, 1, args.length);
        return String.join(DELIMITER, items);
    }

    public static Query decode(String data) {
        String[] args = data.split(DELIMITER);
        String[] queryArgs = Arrays.copyOfRange(args, 1, args.length);
        return new Query(QueryType.byId(args[0]), queryArgs);
    }
}