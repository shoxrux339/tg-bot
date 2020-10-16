package uz.tg.tgbot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class BotHandle extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(BotHandle.class);
    private final Environment environment;

    public BotHandle(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void initBot() {
        try {
            ApiContextInitializer.init();

            TelegramBotsApi botsApi = new TelegramBotsApi();
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.info("Telegram Bot not started: {}", e.toString());
        }
    }

    public void onUpdateReceived(Update update) {
        //555460603
        if (update.hasCallbackQuery()) {
            log.info("update callback {}", update.getCallbackQuery());
        } else if (update.hasMessage()) {
            log.info("update message {}", update.getMessage());
        }
    }


    private void sendMessageOnly(Message message, String msg) {
        SendMessage sendMessage = new SendMessage().setText(msg).setChatId(message.getChatId());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Telegram send message: ", e);
        }
    }

    public void sendMessageOnly(SendMessage msg) {
        if (msg != null) {
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                log.error("Telegram send message: ", e);
            }
        }
    }

    public boolean sendMessageResult(SendMessage msg) {
        if (msg != null) {
            try {
                execute(msg);
                return true;
            } catch (TelegramApiException e) {
                log.error("Telegram send message: ", e);
            }
        }

        return false;
    }

    public void sendDocumentOnly(SendDocument msg) {
        if (msg != null) {
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                log.error("Telegram send message: ", e);
            }
        }
    }

    private void sendMessageOnly(EditMessageText msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Telegram send message: ", e);
        }
    }

    public String getBotUsername() {
        return environment.getProperty("bot_username");
    }

    public String getBotToken() {
        return environment.getProperty("bot_token");
    }

}
