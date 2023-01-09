package AniNight.WathAnimeBot.service;

import AniNight.WathAnimeBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getToken();
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();
            if (messageText.equals("/start")) {
                startCommandRecived(chatId, update.getMessage().getChat().getFirstName());
            } else {
                throw new IllegalStateException("Unexpected value: " + messageText);
            }
        }

    }

    private void startCommandRecived(long chatId, String name) {
        String answer = "This is Anime bot, Для просмотра аниме, "  + name + "Оставьте отзыв @Neon_60";

        sendMessage(chatId, answer);
    }
    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }catch (TelegramApiException e){
            throw new RuntimeException();
        }
    }
}

