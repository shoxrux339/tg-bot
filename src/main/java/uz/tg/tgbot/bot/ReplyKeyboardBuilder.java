package uz.tg.tgbot.bot;


import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardBuilder {

    private Long chatId;
    private String text;

    private List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
    private KeyboardRow row = null;

    private ReplyKeyboardBuilder() {}

    public static ReplyKeyboardBuilder create() {
        return new ReplyKeyboardBuilder();
    }

    public static ReplyKeyboardBuilder create(Long chatId) {
        ReplyKeyboardBuilder builder = new ReplyKeyboardBuilder();
        builder.setChatId(chatId);
        return builder;
    }

    public ReplyKeyboardBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public ReplyKeyboardBuilder setChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public ReplyKeyboardBuilder row() {
        this.row = new KeyboardRow();
        return this;
    }

    public ReplyKeyboardBuilder button(String text) {
        row.add(text);
        return this;
    }

    public ReplyKeyboardBuilder endRow() {
        this.keyboard.add(this.row);
        this.row = null;
        return this;
    }


    public SendMessage build() {
        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText(text);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

    public SendDocument buildDocument() {
        SendDocument message = new SendDocument();

        message.setChatId(chatId);
        message.setCaption(text);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }


    public static SendMessage mainMenu(Long chat_id, String lang) {
        return create(chat_id)
                .build()
                .setText("salom");

    }
}
