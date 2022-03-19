package ru.liga.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.utils.CountDays;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InputParser {
    private final String input;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public InputParser(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
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
}
