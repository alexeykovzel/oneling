package bot.handlers;

import bot.config.BotConfig;
import bot.utils.MessageSender;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.CommandRegistry;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class DefaultBotHandler extends TelegramLongPollingBot implements BotHandler {
    protected final MessageSender sender = new MessageSender(this);
    protected final CommandRegistry commandRegistry;

    public DefaultBotHandler() {
        commandRegistry = new CommandRegistry(false, this::getBotUsername);
        initCommandRegistry();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isCommand() && !commandRegistry.executeCommand(this, message)) {
                handleInvalidCommandUpdate(update);
            } else if (!message.hasText()) {
                handleNonTextMessage(message);
            } else {
                handleTextMessage(message);
            }
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update);
        }
    }

    abstract void initCommandRegistry();

    @Override
    public String getBotUsername() {
        return BotConfig.getInstance().getBotUsername();
    }

    @Override
    public String getBotToken() {
        return BotConfig.getInstance().getBotToken();
    }
}