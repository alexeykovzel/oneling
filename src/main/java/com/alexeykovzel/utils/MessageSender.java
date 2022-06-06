package com.alexeykovzel.utils;

import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

public class MessageSender {
    private final AbsSender sender;

    public MessageSender(AbsSender sender) {
        this.sender = sender;
    }

    public void sendDefaultMessage(String text, String chatId) {
        sendDefaultMessage(text, chatId, null);
    }

    public void sendDefaultMessage(String text, String chatId, ReplyKeyboard replyKeyboard) {
        execute(SendMessage.builder()
                .parseMode(ParseMode.MARKDOWN)
                .disableWebPagePreview(true)
                .replyMarkup(replyKeyboard)
                .chatId(chatId)
                .text(text)
                .build());
    }

    public void answerCallback(String queryId) {
        answerCallback(null, queryId);
    }

    public void answerCallback(String text, String queryId) {
        AnswerCallbackQuery action = new AnswerCallbackQuery();
        action.setCallbackQueryId(queryId);
        action.setText(text);
        execute(action);
    }
    
    public void sendTypingAction(String chatId) {
        SendChatAction action = new SendChatAction();
        action.setAction(ActionType.TYPING);
        action.setChatId(chatId);
        execute(action);
    }

    protected <T extends Serializable, Method extends BotApiMethod<T>> void execute(Method method) {
        try {
            sender.execute(method);
        } catch (TelegramApiException e) {
            e.getStackTrace();
        }
    }
}
