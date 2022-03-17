package ru.liga.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.liga.Forecasting;
import ru.liga.bot.command.JokeCommand;
import ru.liga.bot.command.StartCommand;
import ru.liga.parser.InputParser;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private Logger logger = LoggerFactory.getLogger(Bot.class);

    public Bot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        register(new StartCommand("start", "Старт"));
        register(new JokeCommand("joke", "Анекдот"));
        logger.info("Бот создан");
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        List<Map<LocalDate, Double>> result;
        ContentType contentType;

        Map<LocalDate, Double> date = new LinkedHashMap<>();
        try{
            Forecasting forecast = new Forecasting(new InputParser(msg.getText()));
            contentType = new ContentType(forecast.isGraph());
            result = forecast.forecasting();
            contentType.setContent(result, chatId.toString());
            setAnswer(chatId, userName, contentType);
        }catch (DateTimeException e){
            setAnswer(chatId,"Incorrect date. Please, write date in format \'dd.MM.yyyy\'");
        } catch (Exception e){
            setAnswer(chatId, e.getMessage());
        }
    }

    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     */
    private void setAnswer(Long chatId, String userName, ContentType content) {

        try {
            if (content.isGraph())
                execute(content.getPhoto());
            else
                execute(content.getMessageText());
        } catch (TelegramApiException e) {
        }
    }

    private void setAnswer(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }
}
