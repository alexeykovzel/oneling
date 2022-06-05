package bot.features.query;

import bot.features.query.handler.QueryHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryRegistry {
    private final Map<QueryType, QueryHandler> registryMap = new HashMap<>();

    public void register(QueryHandler handler) {
        registryMap.put(handler.getType(), handler);
    }

    public void registerAll(QueryHandler... handlers) {
        Arrays.stream(handlers).forEach(this::register);
    }
}
