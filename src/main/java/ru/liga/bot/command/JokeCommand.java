package ru.liga.bot.command;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class JokeCommand extends ServiceCommand{
     public JokeCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, "Крутой джазовый бар проводит конкурс на лучшего исполнителя, которого возьмут на работу. Собирается толпа музыкантов и среди них старый негр.\n" +
                "Выходит и говорит:\n" +
                "— Эта песня называется \"Я буду дрочить на тебя всю ночь!\"\n" +
                "Играет восхитительный блюз, после которого половина музыкантов сразу собираются и уходят, другие просто плачут от восторга!\n" +
                "Старый негр говорит:\n" +
                "— А следующая моя песня называется \"Соси у меня, сука, пока я не кончу!\"\n" +
                "И играет блюз еще круче от которого оставшиеся музыканты единогласно отказываются от участия в конкурсе.\n" +
                "Владелец заведения говорит\n" +
                "— Мы вас берем, только не могли бы вы не называть своих песен, а они немного… нуу... шокируют.\n" +
                "Негр соглашается.\n" +
                "Идет очередной концерт, негр забыл заправиться, и у него елда торчит из штанов. Он садится за рояль, играет. Хозяин заведения замечает эту оплошность, подходит к нему, говорит:\n" +
                "— У тебя хуй видно, знаешь?\n" +
                "Негр:\n" +
                "— Что? Знаю ли я её?! Да я её написал!");
    }
}
