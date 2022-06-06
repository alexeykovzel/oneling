package com.alexeykovzel.bot.feature.command;

import com.alexeykovzel.bot.util.MessageSender;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends BotCommand {
    private final HelpCommand helpCommand;

    public StartCommand(HelpCommand helpCommand) {
        super("start", "this command starts the bot");
        this.helpCommand = helpCommand;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String chatId = chat.getId().toString();
        MessageSender sender = new MessageSender(absSender);
        helpCommand.execute(absSender, user, chat, new String[]{});
        sender.sendDefaultMessage( "Please choose a dictionary (Default: English)", chatId);
    }
}
