package ru.liga.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class Validator {
    private final String input;
    private final String[] strings;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Validator(String input) {
        this.input = input;
        strings = input.split(" ");
    }

    private boolean isHavingAlgotithm() {
        logger.debug("isHavingAlgotithm was called.Определяется был ли выбран алгоритм");
        boolean ok = false;
        if (strings.length < 6) {
            logger.debug("Пользователь подал невалидную команду: Мало аргументов");
            throw new IllegalArgumentException("Too few arguments");
        }

        for (int i = 0; i < strings.length; i++){
            if (strings[i].equals("-alg")){
                if (strings[i + 1].isEmpty())
                    throw new IllegalArgumentException("Please, choose algorithm");
                switch (strings[i + 1].toUpperCase(Locale.ROOT)){
                    case("LINEAR") :
                    case("MYSTICAL") :
                    case("ACTUAL") :
                        ok = true; break;
                    default: {
                        logger.debug("Пользователь выбрал невалидный алгоритм");
                        throw new IllegalArgumentException("Please, choose algorithm");
                    }
                }
            }
        }
        logger.debug("Алгоритм выбран верно");
        return ok;
    }

    private boolean isHavingDate() {
        boolean ok = false;
        logger.debug("isHavingDate was called. Оперделяется валидность даты/периода прогноза");
        ok = isHavingAlgotithm();
        for (int i = 0; i < strings.length; i++){
            if (strings[i].equals("-period")){
                if (strings[i + 1].isEmpty()) {
                    logger.debug("Невалидный период прогноза");
                    throw new IllegalArgumentException("Please, choose period or date");
                }
                switch (strings[i + 1].toLowerCase()){
                    case("week") :
                    case("month") :
                        ok = true; break;
                    default: {
                        logger.debug("Невалидная дата прогноза");
                        throw new IllegalArgumentException("Please, choose period or date");
                    }
                }
            }else if (strings[i].equals("-date")){
                if ((strings[i + 1].isEmpty() || strings[i + 1].split("\\.").length != 3) && !strings[i + 1].equals("tomorrow")){
                    logger.debug("Невалидная дата прогноза");
                    throw new IllegalArgumentException("Incorrect date. Please, write date in format \'dd.MM.yyyy\'");
                }
            }
        }
        logger.debug("Дата прогноза успешно определена");
        return ok;
    }

    public boolean isAllOkay() throws IllegalArgumentException {
        logger.debug("isAllOkay was called. Начинается проверка на валидность данных");
        if (isHavingAlgotithm() && isHavingDate()) {
            logger.debug("Проверка на валидность данных успешно пройдена");
            return true;
        }
        logger.debug("Проверка на валидность данных не пройдена");
        return false;
    }

    public boolean isGraph(){
        logger.debug("isGraph was called. Начинается определение необходимости вывода графика");
        String[] args = input.split(" ");
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-output")){
                if (args[i + 1] != null && args[i + 1].equals("graph")) {
                    logger.debug("График нужен");
                    return true;
                }
                logger.debug("График не нужен");
                return false;
            }
        }
        logger.debug("График не нужен");
        return false;
    }
}
