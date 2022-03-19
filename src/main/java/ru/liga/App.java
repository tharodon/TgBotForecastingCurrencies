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
            Bot bot = new Bot("CurrencyForecast_bot", "5161535458:AAHf1enaoKlaZgUj1568ig2nDvaneuIiLrI");
            botsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
