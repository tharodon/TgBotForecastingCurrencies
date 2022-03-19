package ru.liga.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends ServiceCommand{

    private Logger logger = LoggerFactory.getLogger(HelpCommand.class);
    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        logger.info("/help was executed");
        logger.debug("/help was executed");
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Запрос состоит из: \n1) Ключевое слово 'rate'\n" +
                        "2) Желаемые курсы валют через запятую (EUR, TRY, USD, ...)\n" +
                        "3) -date Дата в формате: [day.month.year]/-period [month/week]\n" +
                        "4) -alg [actual, linear, mystical]\n" +
                        "5) Если хочешь с выводом на график, то дабавь флаг '-output graph'");
    }
}
