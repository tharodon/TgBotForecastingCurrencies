package ru.liga.bot.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends ServiceCommand {
    private Logger logger = LoggerFactory.getLogger(StartCommand.class);
    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
            String userName = (user.getUserName() != null) ? user.getUserName() :
                    String.format("%s %s", user.getLastName(), user.getFirstName());
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                    "Привет)\n" + "Рад тебя видеть. Воспользуйся командой '/help' если где-то запутаешься)");
    }
}
