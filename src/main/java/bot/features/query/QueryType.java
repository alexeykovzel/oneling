package bot.features.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueryType {
    SET_LANGUAGE(1),
    GET_EXAMPLES(2),
    GET_DEFINITIONS(3),
    GET_TRANSLATIONS(4);

    private final int id;

    public static QueryType byId(int id) {
        for (QueryType queryType : values()) {
            if (queryType.id == id) return queryType;
        }
        return null;
    }
}
