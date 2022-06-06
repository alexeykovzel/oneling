package com.alexeykovzel.bot.feature.command;

import com.alexeykovzel.bot.feature.query.Query;
import com.alexeykovzel.bot.util.MessageSender;
import com.alexeykovzel.bot.feature.query.QueryType;
import com.alexeykovzel.dictionary.Language;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import com.alexeykovzel.bot.util.Keyboard;

public class SetLanguageCommand extends BotCommand {
    public SetLanguageCommand() {
        super("setlang", "changes dictionary language");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String chatId = chat.getId().toString();
        MessageSender sender = new MessageSender(absSender);

        InlineKeyboardMarkup keyboard = new Keyboard().addRow()
                .addButton("English <-> Russian", buildCallback(Language.ENGLISH, Language.DUTCH))
                .addButton("English --> Dutch", buildCallback(Language.ENGLISH, Language.RUSSIAN)).build();

        sender.sendDefaultMessage("Please choose a language:", chatId, keyboard);
    }

    private String buildCallback(Language l1, Language l2) {
        return Query.encode(QueryType.SET_LANGUAGE, l1.name(), l2.name());
    }
}
