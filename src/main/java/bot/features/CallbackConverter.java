package bot.features;

import bot.features.query.Query;
import bot.features.query.QueryType;

import java.util.Arrays;

public class CallbackConverter {
    private static final String DELIMITER = "&";

    public String encode(QueryType action, String... args) {
        switch (action) {
            case GET_TRANSLATIONS:
            case GET_EXAMPLES:
            case GET_DEFINITIONS:
                return action.getId() + DELIMITER + args[0];
        }
        return null;
    }

    public Query decode(String data) {
        String[] args = data.split(DELIMITER);
        int actionId = Integer.parseInt(args[0]);
        String[] queryArgs = Arrays.copyOfRange(args, 1, args.length);
        return new Query(QueryType.byId(actionId), queryArgs);
    }
}
