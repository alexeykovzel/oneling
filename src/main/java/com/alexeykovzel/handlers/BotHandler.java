package com.alexeykovzel.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotHandler {
    /**
     * Handles commands that could not be found in the command registry of the bot.
     *
     * @param update update of the corresponding command
     */
    void handleInvalidCommandUpdate(Update update);

    /**
     * Handles query updates (e.g. when a user presses a button on the inline keyboard)
     *
     * @param update information about the query update
     */
    void handleCallbackQuery(Update update);

    /**
     * Handles messages from the user that do not contain any text.
     *
     * @param message message that is being sent by the user
     */
    void handleNonTextMessage(Message message);

    /**
     * Handles message from the user than contain only text.
     *
     * @param message message that is being sent by the user
     */
    void handleTextMessage(Message message);
}
