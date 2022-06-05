package bot.features.query.handler;

import bot.features.query.QueryType;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface QueryHandler {
    void execute(CallbackQuery query);

    QueryType getType();
}
