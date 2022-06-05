package bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardBuilder {
    private final List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
    private List<InlineKeyboardButton> currentRow;

    public KeyboardBuilder button(String text, String callback) {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .callbackData(callback)
                .text(text).build();
        currentRow.add(button);
        return this;
    }

    public KeyboardBuilder row() {
        currentRow = new ArrayList<>();
        keyboard.add(currentRow);
        return this;
    }

    public InlineKeyboardMarkup build() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(keyboard);
        return markup;
    }
}