package ru.liga.forecasting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.forecasting.algorithm.actual.ActualAlgorithm;
import ru.liga.forecasting.algorithm.linear.LinearAlgorithm;
import ru.liga.forecasting.algorithm.mystical.MysticalAlgorithm;
import ru.liga.graph.GraphDesigner;
import ru.liga.database.DAOCurrency;
import ru.liga.parser.DateAndCurrencies;
import ru.liga.parser.InputParser;
import ru.liga.parser.Validator;

import java.time.LocalDate;
import java.util.*;

public class Forecasting {
    private InputParser input;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Forecasting(InputParser input) {
        this.input = input;
    }

    public boolean isGraph(){
        return new Validator(input.getInput()).isGraph();
    }

    public List<Map<LocalDate, Double>> forecasting() throws Exception {
        logger.debug("forecasting was called. Начало вычислений");
        logger.info("Начало вычислений");
        DAOCurrency daoCurrency = new DAOCurrency();
        new Validator(input.getInput()).isAllOkay();
        String[] currencies = input.getCurrency();
        List<Map<LocalDate, Double>> result = new ArrayList<>();

        for (int circ = 0; circ < currencies.length; circ++) {
            Map<LocalDate, Double> curr = new LinkedHashMap<>();
            DateAndCurrencies dateAndCurrencies = daoCurrency.getInfo(currencies[circ]);
            List<LocalDate> dates = input.getDates();
            double[] answer = new double[0];
            switch (input.getAlgorithm()) {
                case ("ACTUAL"): {
                    logger.debug("Начало вычислений по алгоритму \"Актуальный\"");
                    answer = new ActualAlgorithm(dateAndCurrencies.getInformation(), input.getDates()).forecast();
                    break;
                }
                case ("LINEAR"): {
                    logger.debug("Начало вычислений по алгоритму \"Линейная регрессия\"");
                    answer = new LinearAlgorithm(dateAndCurrencies, dates).forecast();
                    break;
                }
                case ("MYSTICAL"): {
                    logger.debug("Начало вычислений по алгоритму \"Мистичесикй\"");
                    answer = new MysticalAlgorithm(dateAndCurrencies, input.getDates()).forecast();
                    break;
                }
                default: break;
            }
            int j = 0;
            for (double o : answer){
                curr.put(dates.get(j++), o);
            }
            result.add(curr);
        }
        if (isGraph())
            new GraphDesigner().draw(result);
        return result;
    }
}
