package ru.liga.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.CountDays;
import ru.liga.bot.Bot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class InputParser {
    private final String input;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public InputParser(String input) {
        this.input = input;
    }


    private boolean isHavingAlgotithm() throws Exception {
        logger.debug("isHavingAlgotithm was called.Определяется был ли выбран алгоритм");
        String[] strings = input.split(" ");
        boolean ok = false;
        if (strings.length < 6) {
            logger.debug("Пользователь подал невалидную команду: Мало аргументов");
            throw new Exception("Too few arguments");
        }

        for (int i = 0; i < strings.length; i++){
            if (strings[i].equals("-alg")){
                if (strings[i + 1].isEmpty())
                    throw new Exception("Please, choose algorithm");
                switch (strings[i + 1].toUpperCase(Locale.ROOT)){
                    case("LINEAR") :
                    case("MYSTICAL") :
                    case("ACTUAL") :
                        ok = true; break;
                    default: {
                        logger.debug("Пользователь выбрал невалидный алгоритм");
                        throw new Exception("Please, choose algorithm");
                    }
                }
            }
        }
        logger.debug("Алгоритм выбран верно");
        return ok;
    }


    private boolean isHavingDate() throws Exception {
        String[] strings = input.split(" ");
        boolean ok = false;
        logger.debug("isHavingDate was called. Оперделяется валидность даты/периода прогноза");
        ok = isHavingAlgotithm();
        for (int i = 0; i < strings.length; i++){
            if (strings[i].equals("-period")){
                if (strings[i + 1].isEmpty()) {
                    logger.debug("Невалидный период прогноза");
                    throw new Exception("Please, choose period or date");
                }
                switch (strings[i + 1].toLowerCase()){
                    case("week") :
                    case("month") :
                        ok = true; break;
                    default: {
                        logger.debug("Невалидная дата прогноза");
                        throw new Exception("Please, choose period or date");
                    }
                }
            }else if (strings[i].equals("-date")){
                if (strings[i + 1].isEmpty() || strings[i + 1].split("\\.").length != 3){
                    logger.debug("Невалидная дата прогноза");
                    throw new Exception("Incorrect date. Please, write date in format \'dd.MM.yyyy\'");
                }
            }
        }
        logger.debug("Дата прогноза успешно определена");
        return ok;
    }

    public boolean isAllOkay() throws Exception {
        logger.debug("isAllOkay was called. Начинается проверка на валидность данных");
        if (isHavingAlgotithm() && isHavingDate()) {
            logger.debug("Проверка на валидность данных успешно пройдена");
            return true;
        }
        logger.debug("Проверка на валидность данных не пройдена");
        return false;
    }

    public String[] getCurrency() {
        logger.debug("getCurrency was called. Начинается определение заданных валют");
        String[] result = input.split(" ");
        List<String> currencies = new ArrayList<>();
        int j = 0;
        for (int i = 1; !(result[i].equals("-date") || result[i].equals("-period")); i++){
            currencies.add(result[i]);
        }
        result = new String[currencies.size()];
        for (int i = 0; i < currencies.size(); i++){
            result[i] = currencies.get(i).split(",")[0];
        }
        logger.debug("Заданные валюты успешно определены: " + currencies.toString());
        return result;
    }

    public String getAlgorithm(){
        logger.debug("getAlgorithm was called. Получение выбранного алгоритма...");
        String[] args = input.split(" ");
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-alg")) {
                logger.debug("Алгоритм получен: " + args[i + 1].toUpperCase());
                return args[i + 1].toUpperCase();
            }
        }
        logger.debug("Алгоритм не был выбран");
        return null;
    }

    public List<LocalDate> getDates(){
        logger.debug("getDates was called. Получение периода прогноза");
        String[] args = input.split(" ");
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-date")){
                switch (args[i + 1]) {
                    case("tomorrow") : {
                        dates.add(LocalDate.now().plusDays(1));
                        break;
                    } default : {
                        LocalDate date = LocalDate.of(
                                Integer.parseInt(args[i + 1].split("\\.")[2]),
                                Integer.parseInt(args[i + 1].split("\\.")[1]),
                                Integer.parseInt(args[i + 1].split("\\.")[0]));
                        dates.add(date);
                        break;
                    }
                }
            }else if (args[i].equals("-period")){
                switch (args[i + 1]) {
                    case("month") : {
                        for (int amount_days = 1; amount_days <= CountDays.DAYS_OF_MONTH; amount_days++) {
                            dates.add(LocalDate.now().plusDays(amount_days));
                        }
                        break;
                    }
                    case("week") : {
                        for (int amount_days = 1; amount_days <= CountDays.DAYS_OF_WEEK; amount_days++) {
                            dates.add(LocalDate.now().plusDays(amount_days));
                        }
                        break;
                    }
                }
            }
        }
        logger.debug("Период прогноза успешно получен");
        return dates;
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

    public String getPeriod(){
        logger.debug("getPeriod was called. Получение периода прогноза");
        String[] args = input.split(" ");
        for (int i = 0; i < args.length; i++){
            if (args[i].equals("-period") || args[i].equals("-date")) {
                logger.debug("Период получен " + args[i + i]);
                return args[i + 1];
            }
        }
        logger.debug("Период не получен");
        return null;
    }


}
