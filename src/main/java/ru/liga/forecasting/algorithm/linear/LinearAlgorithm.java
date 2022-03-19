package ru.liga.forecasting.algorithm.linear;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.forecasting.algorithm.ForecastAlgorithm;
import ru.liga.parser.DateAndCurrencies;
import ru.liga.utils.CountDays;

import java.time.LocalDate;
import java.util.List;

public class LinearAlgorithm implements ForecastAlgorithm {
    private DateAndCurrencies dateAndCurrencies;
    private int[] predictors;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public LinearAlgorithm(DateAndCurrencies dateAndCurrencies, List<LocalDate> dates) {
        logger.debug("Начало вычислений по алгоритму \"Линейная регрессия\"");
        predictors = new int[dates.size()];
        for (int i = 0; i < dates.size(); i++) {
            predictors[i] = (int) dateAndCurrencies.differenceDates(dates.get(i), dateAndCurrencies.getDates().get(0)) + CountDays.DAYS_OF_MONTH;
        }
        this.dateAndCurrencies = dateAndCurrencies;
    }

    public double[] forecast(){
        logger.debug("forecastLinearregression was called");
        Double[] x = new Double[30];
        Double[] y = new Double[30];
        List<Double> curr = dateAndCurrencies.getCurrencySortedToData();
        for (int i = 0; i < 30; i++){
            y[i] = (double) i;
        }
        int j = 0;
        for(int i = curr.size() - 30; i < curr.size(); i++){
            x[j] = curr.get(i);
            j++;
        }
        LinearRegression linearRegression = new LinearRegression(y, x);
        double[] currenciesForecast = new double[predictors.length];
        for (int i = 0; i < predictors.length; i++){
            currenciesForecast[i] = linearRegression.predict(predictors[i]);
        }
        return currenciesForecast;
    }
}
