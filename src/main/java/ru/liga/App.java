package ru.liga;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.bot.Bot;

/**
 * Hello world!
 *
 */


public class App {
    public static void main( String[] args ){
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot("thardon_bot", "5141426112:AAEN6eQRTBN2VbzfpOtFy8oRmxAFh1ZGhCo");
            botsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
