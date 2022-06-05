package bot.features.query;

import lombok.Getter;

@Getter
public class Query {
    private final QueryType action;
    private final String[] args;

    public Query(QueryType action, String... args) {
        this.action = action;
        this.args = args;
    }
}