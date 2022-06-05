package bot.features.command;

import bot.features.CallbackConverter;
import bot.utils.MessageSender;
import bot.features.query.QueryType;
import services.Language;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import bot.utils.KeyboardBuilder;

public class SetLanguageCommand extends BotCommand {
    private final CallbackConverter encoder = new CallbackConverter();

    public SetLanguageCommand() {
        super("setlang", "changes dictionary language");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String chatId = chat.getId().toString();
        MessageSender sender = new MessageSender(absSender);

        InlineKeyboardMarkup keyboard = new KeyboardBuilder().row()
                .button("English <-> Russian", buildCallback(Language.ENGLISH, Language.DUTCH))
                .button("English --> Dutch", buildCallback(Language.ENGLISH, Language.RUSSIAN)).build();

        sender.sendDefaultMessage("Please choose a language:", chatId, keyboard);
    }

    private String buildCallback(Language l1, Language l2) {
        return new CallbackConverter().encode(QueryType.SET_LANGUAGE, l1.name(), l2.name());
    }
}
