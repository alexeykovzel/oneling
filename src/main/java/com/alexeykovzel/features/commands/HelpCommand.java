package com.alexeykovzel.features.commands;

import com.alexeykovzel.utils.MessageSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends BotCommand {
    public HelpCommand() {
        super("help", "shows all commands");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String chatId = chat.getId().toString();
        MessageSender sender = new MessageSender(absSender);
        sender.sendDefaultMessage(getHelpMessage(), chatId);
    }

    private String getHelpMessage() {
        return "You can control me by using the following commands:\n\n"
                + "/help - shows all commands";
    }
}